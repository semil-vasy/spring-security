import axios from "axios";

export const BASE_URL = "http://localhost:8080";

const apiService = axios.create({
  baseURL: BASE_URL,
});

export const login = async (user) => {
  return await apiService.post("/api/auth/login", user);
};

export const signup = async (user) => {
  try {
    const response = await apiService.post("/api/auth/register", user);
    return response.data;
  } catch (error) {
    return error;
  }
};
