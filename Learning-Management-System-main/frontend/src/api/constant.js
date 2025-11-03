export const API_BASE_URL =
  process.env.REACT_APP_API_BASE_URL ||
  (typeof window !== "undefined" ? window.__API_BASE_URL__ : undefined) ||
  "http://localhost:8080";