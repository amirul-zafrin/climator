import axios from "axios";

// TODO: Clean up code
export default axios.create({
  baseURL: "http://localhost:8081",
  headers: {
    Authorization: localStorage.getItem("Authorization"),
    "Content-type": "application/json",
  },
});
