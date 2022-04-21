import React, { useState } from "react";
import TopBar from "../components/TopBar";
import { FlexContainer } from "../components/styles/FlexContainer.styled";
import { Modal } from "@mantine/core";
import { Button } from "../components/styles/Button";
import UploadData from "../components/UploadData";
import DataGridComp from "../components/DataGridComp";
import Graph from "../components/Graph";

const Dashboard = () => {
  const [openedUpload, setOpenedUpload] = useState(false);
  const [data, setData] = useState([]);
  const addData = ({ list }) => {
    setData(list);
  };
  // TODO: Add graph with filtered data
  //TODO: Filtering Data
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
      <DataGridComp data={data} />
    </FlexContainer>
  );
};

export default Dashboard;
