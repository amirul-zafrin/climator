import React from "react";
import { useState } from "react";
import {
  ButtonContainer,
  LandingCard,
  LButton,
  LTitle,
  TextContainer,
} from "../components/styles/Landing.styled";
import { FlexContainer } from "../components/styles/FlexContainer.styled";
import Login from "../components/Login";
import { Modal } from "@mantine/core";
import SignUp from "../components/SignUp";

const Landing = () => {
  const [openedLogin, setOpenedLogin] = useState(false);
  const [openedSignup, setOpenedSignup] = useState(false);

  return (
    <FlexContainer>
      <LandingCard>
        <TextContainer>
          <LTitle>Climator</LTitle>
          <p>
            Time-series analysis of temperature data may be useful in helping to
            determine whether temperature affects the productivity of business
            enterprises.
          </p>
        </TextContainer>
        <ButtonContainer>
          <LButton onClick={() => setOpenedLogin(true)}>Log In</LButton>
          <Modal
            centered
            title="Login"
            onClose={() => setOpenedLogin(false)}
            opened={openedLogin}
          >
            <Login />
          </Modal>
          <LButton onClick={() => setOpenedSignup(true)}>Sign up</LButton>
          <Modal
            centered
            title="Sign Up"
            onClose={() => setOpenedSignup(false)}
            opened={openedSignup}
          >
            <SignUp />
          </Modal>
        </ButtonContainer>
      </LandingCard>
    </FlexContainer>
  );
};

export default Landing;
