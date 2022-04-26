import React, { useState } from "react";
import { Modal, Accordion } from "@mantine/core";
import { ButtonContainer } from "./styles/FlexContainer.styled";
import { Button } from "./styles/Button";
import FileList from "./FileList";
import DropZone from "./DropZone";

const DrawerComp = ({ setData }) => {
  const [openedUpload, setOpenedUpload] = useState(false);
  const [user, setUser] = useState([]);
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
        <DropZone user={user} />
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
