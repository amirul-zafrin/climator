import axios from "axios";
import React, { useState, useEffect } from "react";
import { TopContainer } from "./styles/TopBar.styled";
import { Burger, Drawer } from "@mantine/core";
import DrawerComp from "./DrawerComp";

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
        size="xl"
      >
        <DrawerComp setData={setData} />
      </Drawer>
    </>
  );
};

export default TopBar;
