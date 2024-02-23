import axios from "axios";

export const BASE_URL = "http://localhost:8080";

const apiService = axios.create({
  baseURL: BASE_URL,
  headers: {
    Authorization: `Bearer ${localStorage.getItem("data")}`,
    "Content-Type": "application/json",
  },
});

export const getUser = async () => {
  return await apiService.get("/api/user");
};

export const getAllUser = async () => {
  return await apiService.get("/api/admin");
};
