import React from "react";
import { LineChart, Line, CartesianGrid, XAxis, YAxis } from "recharts";

const Graph = ({ data }) => {
  return (
    <LineChart width={1300} height={600} data={data}>
      <Line type="monotone" dataKey="temperature" stroke="#000" dot={false} />
      <CartesianGrid stroke="#ccc" />
      <XAxis dataKey="time" />
      <YAxis type="number" domain={["dataMin" - 5, "dataMax" + 5]} />
      {console.log(data)}
    </LineChart>
  );
};

export default Graph;
