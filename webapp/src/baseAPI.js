import axios from "axios";

// TODO: Clean up code
export default axios.create({
  baseURL: "http://localhost:8081",
  headers: {
    "Content-type": "application/json",
  },
});
