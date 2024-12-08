import axios from "axios";

export const BASE_URL = "http://localhost:8080";

const apiService = axios.create({
  baseURL: BASE_URL,
});

export const getUser = async () => {
  console.log(localStorage.getItem("data"));
  return await apiService.get("/api/user", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("data")}`,
      "Content-Type": "application/json",
    },
  });
};

export const getAllUser = async () => {
  console.log(localStorage.getItem("data"));
  return await apiService.get("/api/admin", {
    headers: {
      Authorization: `Bearer ${localStorage.getItem("data")}`,
      "Content-Type": "application/json",
    },
  });
};
