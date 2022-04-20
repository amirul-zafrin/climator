import React from "react";
import { useNavigate } from "react-router-dom";
import { Container, TextInput, PasswordInput, Text } from "@mantine/core";
import { ButtonContainer, LgButton } from "./styles/Login.styled";
import { useForm } from "@mantine/form";
import axios from "axios";

const Login = () => {
  let navigate = useNavigate();

  const form = useForm({
    initialValues: {
      username: "",
      password: "",
    },
  });

  const handleSubmit = form.onSubmit((values) => {
    axios
      .post(`http://localhost:8081/login`, values)
      .then(function (response) {
        localStorage.removeItem("Authorization");
        localStorage.setItem("Authorization", response.data.jwt);
        navigate("/dashboard/" + response.data.username);
      })
      .catch(function (error) {
        console.log(error.response.data);
      });
  });

  return (
    <Container>
      <form onSubmit={handleSubmit}>
        <TextInput
          placeholder="username"
          label="Username"
          required
          {...form.getInputProps("username")}
        />
        <PasswordInput
          placeholder="Password"
          label="Password"
          required
          {...form.getInputProps("password")}
        />
        <ButtonContainer>
          <LgButton>Log In</LgButton>
        </ButtonContainer>
      </form>
      <Text
        variant="link"
        component="a"
        href="https://mantine.dev"
        align="center"
        size="sm"
      >
        Forgot Password?
      </Text>
    </Container>
  );
};

export default Login;
