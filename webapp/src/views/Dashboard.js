import React, { useState } from "react";
import TopBar from "../components/TopBar";
import { FlexContainer } from "../components/styles/FlexContainer.styled";
import DataGridComp from "../components/DataGridComp";

const Dashboard = () => {
  const [data, setData] = useState([]);

  // TODO: Add graph with filtered data
  return (
    <FlexContainer>
      <TopBar setData={setData} />
      <DataGridComp data={data} />
    </FlexContainer>
  );
};

export default Dashboard;
