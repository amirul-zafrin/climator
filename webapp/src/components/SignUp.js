import React from "react";
import { Container, TextInput, PasswordInput } from "@mantine/core";
import { ButtonContainer, LgButton } from "./styles/Login.styled";
import { useForm } from "@mantine/form";
import { useDisclosure } from "@mantine/hooks";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import ErrorHandling from "./ErrorHandling";

const SignUp = () => {
  const form = useForm({
    initialValues: {
      username: "",
      email: "",
      password: "",
      confirmPassword: "",
    },

    validate: {
      email: (value) => (/^\S+@\S+$/.test(value) ? null : "Invalid email"),
      confirmPassword: (value, values) =>
        value !== values.password ? "Passwords did not match" : null,
    },
  });

  let navigate = useNavigate();
  const routeChange = () => {
    let path = "/activation";
    navigate(path);
  };

  const [sent, handlers] = useDisclosure(false, {
    onOpen: () => routeChange(),
  });

  const [error, setError] = React.useState(null);

  //TODO: async change page with activation link
  const handleSubmit = form.onSubmit((values) => {
    axios
      .post(`http://localhost:8081/register/create`, values)
      .then((response) => {
        axios
          .get(`http://localhost:8081/mail/activation/` + response.data)
          .then(() => {
            handlers.open();
            console.log(sent);
            setError(null);
          })
          .catch((error) => console.log(error.response.data));
      })
      .then((response) => {
        console.log(response);
      })
      .catch(function (error) {
        setError(error.response.data);
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
        <TextInput
          placeholder="user@email.com"
          label="Email"
          required
          {...form.getInputProps("email")}
        />
        <PasswordInput
          placeholder="Password"
          label="Password"
          required
          {...form.getInputProps("password")}
        />
        <PasswordInput
          placeholder="Password"
          label="Confirm Password"
          required
          {...form.getInputProps("confirmPassword")}
        />
        <ButtonContainer>
          <LgButton type="submit">Submit</LgButton>
        </ButtonContainer>
      </form>
      {error !== null ? <ErrorHandling text={error} /> : null}
    </Container>
  );
};

export default SignUp;
