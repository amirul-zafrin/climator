import React, { useState } from "react";
import { Modal, Accordion } from "@mantine/core";
import { ButtonContainer } from "./styles/FlexContainer.styled";
import { Button } from "./styles/Button";
import FileList from "./FileList";
import UploadData from "../components/UploadData";

const DrawerComp = ({ setData }) => {
  const [openedUpload, setOpenedUpload] = useState(false);
  return (
    <>
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
      <Accordion>
        <Accordion.Item label="Uploaded Data">
          <FileList setData={setData} />
        </Accordion.Item>
      </Accordion>
    </>
  );
};

export default DrawerComp;
