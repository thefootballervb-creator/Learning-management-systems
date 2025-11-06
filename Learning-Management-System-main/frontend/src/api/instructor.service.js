import axios from "axios";
import { API_BASE_URL } from "./constant";

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem("token");
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => Promise.reject(error)
);

// Get all courses created by the logged-in instructor
async function getMyCourses() {
  try {
    const { data } = await api.get("/api/instructor/courses");
    return { success: true, data: data.data || data };
  } catch (error) {
    console.error("Error fetching instructor courses:", error);
    return { 
      success: false, 
      error: error.response?.data?.message || "Could not fetch courses" 
    };
  }
}

// Create a new course
async function createCourse(courseData) {
  try {
    const { data } = await api.post("/api/instructor/courses", courseData);
    return { success: true, data: data.data || data };
  } catch (error) {
    console.error("Error creating course:", error);
    return { 
      success: false, 
      error: error.response?.data?.message || "Could not create course" 
    };
  }
}

// Update a course
async function updateCourse(courseId, courseData) {
  try {
    const { data } = await api.put(`/api/instructor/courses/${courseId}`, courseData);
    return { success: true, data: data.data || data };
  } catch (error) {
    console.error("Error updating course:", error);
    return { 
      success: false, 
      error: error.response?.data?.message || "Could not update course" 
    };
  }
}

// Delete a course
async function deleteCourse(courseId) {
  try {
    const { data } = await api.delete(`/api/instructor/courses/${courseId}`);
    return { success: true, data: data.data || data };
  } catch (error) {
    console.error("Error deleting course:", error);
    return { 
      success: false, 
      error: error.response?.data?.message || "Could not delete course" 
    };
  }
}

// Get all students enrolled in instructor's courses
async function getMyStudents() {
  try {
    const { data } = await api.get("/api/instructor/students");
    return { success: true, data: data.data || data };
  } catch (error) {
    console.error("Error fetching students:", error);
    return { 
      success: false, 
      error: error.response?.data?.message || "Could not fetch students" 
    };
  }
}

// Get students enrolled in a specific course
async function getCourseStudents(courseId) {
  try {
    const { data } = await api.get(`/api/instructor/courses/${courseId}/students`);
    return { success: true, data: data.data || data };
  } catch (error) {
    console.error("Error fetching course students:", error);
    return { 
      success: false, 
      error: error.response?.data?.message || "Could not fetch course students" 
    };
  }
}

// Get enrollment details for a specific course
async function getCourseEnrollments(courseId) {
  try {
    const { data } = await api.get(`/api/instructor/courses/${courseId}/enrollments`);
    return { success: true, data: data.data || data };
  } catch (error) {
    console.error("Error fetching course enrollments:", error);
    return { 
      success: false, 
      error: error.response?.data?.message || "Could not fetch enrollments" 
    };
  }
}

export const instructorService = {
  getMyCourses,
  createCourse,
  updateCourse,
  deleteCourse,
  getMyStudents,
  getCourseStudents,
  getCourseEnrollments,
};

