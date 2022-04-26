import React, { useState, useEffect } from "react";
import {
  XYPlot,
  LineSeries,
  XAxis,
  YAxis,
  HorizontalGridLines,
  VerticalGridLines,
} from "react-vis";

const Graph = ({ data }) => {
  useEffect(() => {
    console.log(data);
  }, []);
  return <></>;
};

export default Graph;
