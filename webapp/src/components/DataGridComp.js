import React, { useMemo, useRef, useCallback } from "react";
import { AgGridReact } from "ag-grid-react";

import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-alpine.css";

const defaultColDef = {
  sortable: true,
  resizable: true,
};

// TODO: Fix the filtering
const columns = [
  { field: "id", checkboxSelection: true },
  { field: "date", filter: "agDateColumnFilter" },
  { field: "time" },
  { field: "temperature", filter: "agNumberColumnFilter" },
];

const rows = (data) => {
  return data.map((i) => {
    var date = new Date(i[2] * 1);
    var date_string = date.toLocaleString("en-GB").split(",");
    var dt_string = date_string[0].split("/");
    return {
      id: i[0],
      date: date,
      time: date_string[1],
      temperature: Number(i[3]).toFixed(2),
    };
  });
};

const DataGridComp = ({ data }) => {
  const gridStyle = useMemo(() => ({ height: 500, width: "65% " }), []);

  return (
    <div className="ag-theme-alpine" style={gridStyle}>
      <AgGridReact
        rowData={rows(data)}
        columnDefs={columns}
        defaultColDef={defaultColDef}
        rowSelection={"multiple"}
      ></AgGridReact>
    </div>
  );
};

export default DataGridComp;
