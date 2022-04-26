import React, { useState, useEffect } from "react";
import { FlexContainer } from "../components/styles/FlexContainer.styled";
import TopBar from "../components/TopBar";
import { Button } from "../components/styles/Button";
import { useNavigate, useLocation } from "react-router-dom";
import Graph from "../components/Graph";

const GraphView = () => {
  let navigate = useNavigate();
  let location = useLocation();
  const [arr, setArr] = useState();

  const row = (data) => {
    const csvHeader = data.slice(0, data.indexOf("\n")).split(",");
    const csvRows = data.slice(data.indexOf("\n") + 1).split("\n");

    const array = csvRows.map((i) => {
      const values = i.split(",");
      // const obj = csvHeader.reduce((object, header, index) => {
      //   object[header] = values[index];
      //   return object;
      // }, {});
      // return obj;
      return {
        id: values[0],
        date: values[1],
        time: values[2],
        temperature: parseFloat(values[3]).toFixed(2),
      };
    });
    return array;
  };

  useEffect(() => {
    let isMounted = true;
    if (isMounted) setArr(row(location.state.data));
    return () => {
      isMounted = false;
    };
  }, [location.state.data]);

  return (
    <FlexContainer>
      <TopBar />
      <Button onClick={() => navigate(-1)}>To Table</Button>
      {location.state.data.length > 0 ? <Graph data={arr} /> : null}
    </FlexContainer>
  );
};

export default GraphView;
