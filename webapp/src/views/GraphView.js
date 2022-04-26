import React from "react";
import { FlexContainer } from "../components/styles/FlexContainer.styled";
import TopBar from "../components/TopBar";
import { Button } from "../components/styles/Button";
import { useNavigate, useLocation } from "react-router-dom";
import Graph from "../components/Graph";

const GraphView = () => {
  let navigate = useNavigate();
  let location = useLocation();

  return (
    <FlexContainer>
      <TopBar />
      <Button onClick={() => navigate(-1)}>To Table</Button>
      {location.state.data.length > 0 ? (
        <Graph data={location.state.data} />
      ) : null}
    </FlexContainer>
  );
};

export default GraphView;
