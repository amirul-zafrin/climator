import axios from "axios";
import React, { useState, useEffect } from "react";
import { TopContainer } from "./styles/TopBar.styled";
import { Burger, Drawer } from "@mantine/core";
import FileList from "./FileList";

const TopBar = ({ setData }) => {
  const [user, setUser] = useState([]);
  const [opened, setOpened] = useState(false);

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

  const onClick = () => {
    setOpened(!opened);
  };

  return (
    <>
      <TopContainer>
        <Burger opened={opened} onClick={onClick} />
        <h1>Welcome to Climator, {user}!</h1>
      </TopContainer>
      <Drawer
        opened={opened}
        onClose={() => setOpened(false)}
        title="File List"
        padding="xl"
        size="md"
      >
        <FileList setData={setData} />
      </Drawer>
    </>
  );
};

export default TopBar;
