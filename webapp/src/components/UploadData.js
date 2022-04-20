import axios from "axios";
import React, { useState } from "react";
import { useDropzone } from "react-dropzone";
import { TextContainer, UploadButton } from "./styles/Upload.styled";

// TODO: Improve loading time

const UploadData = ({ addData }) => {
  const { acceptedFiles, getRootProps, getInputProps } = useDropzone({
    accept: ".csv",
  });
  const [array, setArray] = useState([]);

  const csvFileToArray = (string) => {
    const csvHeader = ["id", "unknown", "epoch", "temperature"];
    const csvRows = string.slice(string.indexOf("\n") + 1).split("\n");
    const array = csvRows.map((i) => {
      var result = i.split(",");
      result.splice(3, 3);
      const obj = csvHeader.reduce((object, header, index) => {
        object[header] = result[index];
        return object;
      }, {});
      return obj;
    });
    setArray(array);
  };

  const uploadToDB = ({ file }) => {
    axios
      .post(
        //TODO: change id to user id
        "http://localhost:8081/data/upload?id=1",
        { file },
        {
          headers: {
            Authorization: localStorage.getItem("Authorization"),
            "Content-Type": "text/csv",
          },
        }
      )
      .then((res) => console.log(res.data))
      .catch((err) => console.log(err));
  };

  const handleOnSubmit = (e) => {
    e.preventDefault();
    if (acceptedFiles[0]) {
      const fileReader = new FileReader();
      fileReader.onload = function (event) {
        const csvOutput = event.target.result;
        csvFileToArray(csvOutput);
        // console.log(array);
        // addData({ list: array });
        if (array.length !== 0) {
          console.log(acceptedFiles[0]);
          uploadToDB(acceptedFiles[0]);
          console.log("uploaded");
        }
      };
      fileReader.readAsText(acceptedFiles[0]);
    }
  };

  const files = acceptedFiles.map((file) => (
    <li key={file.path}>
      <ul>{file.path} </ul>
      <ul>
        <UploadButton onClick={(e) => handleOnSubmit(e)}>Upload</UploadButton>
      </ul>
    </li>
  ));

  return (
    <section className="container">
      <div {...getRootProps({ className: "dropzone" })}>
        <input {...getInputProps()} />
        <TextContainer>
          <p>Drag 'n' drop some files here, or click to select files</p>
          <em>(Only *.csv file will be accepted)</em>
        </TextContainer>
      </div>
      <aside>
        <ul>{files}</ul>
      </aside>
    </section>
  );
};

export default UploadData;
