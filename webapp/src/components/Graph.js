import React from "react";
import { LineChart, Line } from "recharts";

const rows = (data) => {
  return data.map((i) => {
    return {
      id: i[0],
      dateTime: i[2],
      temperature: i[3],
    };
  });
};
const Graph = ({ data }) => {
  return (
    <LineChart width={400} height={400} data={rows(data)}>
      <Line type="monotone" dataKey="temperature" stroke="#8884d8" />
    </LineChart>
  );
};

export default Graph;
