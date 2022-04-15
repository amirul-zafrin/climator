import React from "react";
import { Alert } from "@mantine/core";
import { AlertCircle } from "tabler-icons-react";

const ErrorHandling = ({ text }) => {
  return (
    <Alert icon={<AlertCircle size={16} />} title="Error!" color="red">
      {text}
    </Alert>
  );
};

export default ErrorHandling;
