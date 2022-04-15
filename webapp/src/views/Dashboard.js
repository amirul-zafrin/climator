import React, { useState } from "react";
import TopBar from "../components/TopBar";
import { FlexContainer } from "../components/styles/FlexContainer.styled";
import { Modal } from "@mantine/core";
import { Button } from "../components/styles/Button";
import UploadData from "../components/UploadData";

const Dashboard = () => {
  const [openedUpload, setOpenedUpload] = useState(false);
  const [file, setFile] = useState();
  const fileReader = new FileReader();

  const handleOnSubmit = (e) => {
    e.preventDefault();
    if (file) {
      fileReader.onload = function (event) {
        const csvOutput = event.target.result;
      };

      fileReader.readAsText(file);
    }
  };

  return (
    <FlexContainer>
      <TopBar />
      <Button onClick={() => setOpenedUpload(true)}>Upload File</Button>
      <Modal
        centered
        title="Upload File"
        onClose={() => setOpenedUpload(false)}
        opened={openedUpload}
      >
        <UploadData />
      </Modal>
    </FlexContainer>
  );
};

export default Dashboard;
