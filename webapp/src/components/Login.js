import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { Container, TextInput, PasswordInput, Text } from "@mantine/core";
import { ButtonContainer, LgButton } from "./styles/Login.styled";
import { useForm } from "@mantine/form";
import axios from "axios";
import ErrorHandling from "./ErrorHandling";

const Login = () => {
  let navigate = useNavigate();

  const form = useForm({
    initialValues: {
      username: "",
      password: "",
    },
  });

  const [value, setValue] = useState(false);

  const [error, setError] = React.useState(null);

  const handleSubmit = form.onSubmit((values) => {
    axios
      .post(`http://localhost:8081/login`, values)
      .then(function (response) {
        localStorage.removeItem("Authorization");
        localStorage.setItem("Authorization", response.data.jwt);
        navigate("/dashboard/" + response.data.username);
      })
      .catch(function (error) {
        setError(error.response.data);
      });
  });

  const resetPassRoute = (value) => {
    return value ? navigate("/resetPassword") : null;
  };
  useEffect(() => {
    resetPassRoute(value);
  }, [value]);

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
      {error !== null ? <ErrorHandling text={error} /> : null}
      <Text
        variant="link"
        component="a"
        align="center"
        size="sm"
        onClick={() => setValue(true)}
      >
        Forgot Password?
      </Text>
    </Container>
  );
};

export default Login;
