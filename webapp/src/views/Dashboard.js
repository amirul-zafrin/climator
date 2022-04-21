import React, { useState } from "react";
import TopBar from "../components/TopBar";
import {
  ButtonContainer,
  FlexContainer,
} from "../components/styles/FlexContainer.styled";
import { Modal } from "@mantine/core";
import { Button } from "../components/styles/Button";
import UploadData from "../components/UploadData";
import DataGridComp from "../components/DataGridComp";
const Dashboard = () => {
  const [openedUpload, setOpenedUpload] = useState(false);
  const [data, setData] = useState([]);

  // TODO: Add graph with filtered data
  return (
    <FlexContainer>
      <TopBar setData={setData} />
      <ButtonContainer>
        <Button onClick={() => setOpenedUpload(true)}>Upload File</Button>
      </ButtonContainer>
      <Modal
        centered
        title="Upload File"
        onClose={() => setOpenedUpload(false)}
        opened={openedUpload}
      >
        <UploadData />
      </Modal>
      <DataGridComp data={data} />
    </FlexContainer>
  );
};

export default Dashboard;
