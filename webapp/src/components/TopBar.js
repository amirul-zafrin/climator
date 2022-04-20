import axios from "axios";
import React, { useState, useEffect } from "react";
import { TopContainer } from "./styles/TopBar.styled";

const TopBar = () => {
  const [user, setUser] = useState([]);

  useEffect(() => {
    let isMounted = true;
    axios
      .get("http://localhost:8081/manage/username", {
        headers: {
          Authorization: localStorage.getItem("Authorization"),
        },
      })
      .then((res) => {
        if (isMounted) setUser(res.data);
      });
    return () => {
      isMounted = false;
    };
  }, [user]);

  return <TopContainer>Welcome to Climator, {user}!</TopContainer>;
};

export default TopBar;
