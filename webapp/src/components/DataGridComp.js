import React, { useMemo, useRef, useCallback, useState } from "react";
import { AgGridReact } from "ag-grid-react";
import "ag-grid-community/dist/styles/ag-grid.css";
import "ag-grid-community/dist/styles/ag-theme-alpine.css";
import { Button } from "./styles/Button";
import { Tooltip } from "@mantine/core";
import { useNavigate } from "react-router-dom";

// TODO: Config in new file

const defaultColDef = {
  sortable: true,
  resizable: true,
};

const dateFilter = {
  comparator: function (filterLocalDateAtMidnight, cellValue) {
    var dateAsString = cellValue;
    if (dateAsString == null) return -1;
    var dateParts = dateAsString.split("/");
    var cellDate = new Date(
      Number(dateParts[2]),
      Number(dateParts[1]) - 1,
      Number(dateParts[0])
    );
    if (filterLocalDateAtMidnight.getTime() === cellDate.getTime()) {
      return 0;
    }
    if (cellDate < filterLocalDateAtMidnight) {
      return -1;
    }
    if (cellDate > filterLocalDateAtMidnight) {
      return 1;
    }
  },
  browserDatePicker: true,
  minValidYear: 2000,
  maxValidYear: 2030,
};

const timeFilter = {
  allowedCharPattern: "\\d\\-\\,",
  numberParser: (text) => {
    return text == null ? null : parseFloat(text.replace(":", ""));
  },
};

const columns = [
  { field: "id", checkboxSelection: true, headerCheckboxSelection: true },
  {
    field: "date",
    filter: "agDateColumnFilter",
    filterParams: { ...dateFilter, buttons: ["reset", "apply"] },
  },
  {
    field: "time",
    filter: "agNumberColumnFilter",
    filterParams: { ...timeFilter, buttons: ["reset", "apply"] },
  },
  {
    field: "temperature",
    filter: "agNumberColumnFilter",
    filterParams: { buttons: ["reset", "apply"] },
  },
];

const rows = (data) => {
  return data.map((i) => {
    var d = i.split(",");
    var date = new Date(d[2] * 1);
    var date_string = date.toLocaleString("en-GB").split(",");
    if (d[0] !== "") {
      return {
        id: d[0],
        date: date_string[0],
        time: date_string[1],
        temperature: parseFloat(d[6]).toFixed(2),
      };
    }
  });
};

const saveCSVParam = () => {
  return {
    suppressQuotes: true,
    onlySelected: true,
  };
};

const DataGridComp = ({ data }) => {
  const gridRef = useRef();
  const gridStyle = useMemo(() => ({ height: 500, width: "65% " }), []);
  const [filteredData, setFilteredData] = useState();

  const clearFilters = useCallback(() => {
    gridRef.current.api.setFilterModel(null);
  }, []);

  const onBtnExport = useCallback(() => {
    const params = saveCSVParam();
    gridRef.current.api.exportDataAsCsv(params);
  }, []);

  let navigate = useNavigate();

  const toGraph = (e) => {
    e.preventDefault();
    navigate("/graph");
  };

  const onBtnUpdate = useCallback(() => {
    const value = gridRef.current.api.getDataAsCsv(saveCSVParam());
    setFilteredData(value);
  }, []);

  return (
    <div className="ag-theme-alpine" style={gridStyle}>
      <Button onClick={clearFilters}>Reset Filter</Button>
      <Tooltip
        wrapLines
        width={220}
        withArrow
        transition="fade"
        transitionDuration={200}
        label="Make sure you select all rows that you want to download"
      >
        <Button onClick={onBtnExport} disabled={data.length > 0 ? false : true}>
          Export CSV
        </Button>
      </Tooltip>
      <Tooltip
        wrapLines
        width={220}
        withArrow
        transition="fade"
        transitionDuration={200}
        label="Make sure you select all rows that you want to visualize"
      >
        <Button onClick={(e) => toGraph(e)}>To Graph</Button>
      </Tooltip>
      <AgGridReact
        ref={gridRef}
        rowData={rows(data)}
        columnDefs={columns}
        defaultColDef={defaultColDef}
        rowSelection={"multiple"}
      ></AgGridReact>
    </div>
  );
};

export default DataGridComp;
