import React from "react";
import { FlexContainer } from "../components/styles/FlexContainer.styled";
import TopBar from "../components/TopBar";
import { Button } from "../components/styles/Button";
import { useNavigate } from "react-router-dom";

const GraphView = () => {
  let navigate = useNavigate();
  return (
    <FlexContainer>
      <TopBar />
      <Button onClick={() => navigate(-1)}>To Table</Button>
    </FlexContainer>
  );
};

export default GraphView;
