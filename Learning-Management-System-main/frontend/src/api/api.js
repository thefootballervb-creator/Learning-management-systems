import axios from "axios";
import { API_BASE_URL } from "./constant";
import { message } from "antd";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    // Public endpoints that don't need authentication
    const isPublicEndpoint = config.url?.includes('/api/courses') && config.method === 'get' ||
                            config.url?.includes('/api/auth/') ||
                            config.url?.includes('/actuator/health');
    
    // Only add auth header if not a public endpoint
    if (!isPublicEndpoint) {
      const token = localStorage.getItem("token");
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Check if this is an optional resource that shouldn't show error messages
    const isOptionalResource = error.config?.url?.includes('/profile-image') || 
                               error.config?.url?.includes('/certificates/');
    
    // Public endpoints that don't require authentication
    const isPublicEndpoint = error.config?.url?.includes('/api/courses') ||
                            error.config?.url?.includes('/api/auth/') ||
                            error.config?.url?.includes('/actuator/health');
    
    if (error.response?.status === 401) {
      // Don't redirect for public endpoints - they should work without auth
      if (isPublicEndpoint) {
        // For public endpoints, just log the error but don't redirect
        console.log("Public endpoint returned 401, but continuing anyway:", error.config?.url);
        return Promise.reject(error);
      }
      
      // Only redirect if user was previously logged in and it's not a public endpoint
      const token = localStorage.getItem("token");
      if (token) {
        message.destroy()
        message.error("Session expired. Please log in again.");
        localStorage.clear();
        setTimeout(() => {
          window.location.href = "/login";
        }, 1500);
      } else {
        // No token means user is not logged in, just show error
        message.error("Please log in to access this resource.");
      }
    } else if (error.response?.status === 403) {
      message.error("You don't have permission to perform this action.");
    } else if (error.response?.status === 404) {
      // Only show 404 error for non-optional resources
      if (!isOptionalResource) {
        message.error("Requested resource not found.");
      }
      // For optional resources, just log to console
      console.log("Optional resource not found:", error.config?.url);
    } else if (error.response?.status >= 500) {
      message.error("Server error. Please try again later.");
    }
    return Promise.reject(error);
  }
);

export default api;