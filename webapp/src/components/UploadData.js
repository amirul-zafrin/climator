import axios from "axios";
import React from "react";
import { useDropzone } from "react-dropzone";
import { TextContainer, UploadButton } from "./styles/Upload.styled";

// TODO: Improve loading time

const UploadData = () => {
  const { acceptedFiles, getRootProps, getInputProps } = useDropzone({
    key: "file",
  });

  const files = acceptedFiles.map((file) => (
    <li key={file.path}>
      <ul>{file.path} </ul>
    </li>
  ));

  const formData = new FormData();

  const fileObjects = acceptedFiles.map((file) => {
    formData.append("file", file, file.name);
  });

  const upDB = async () => {
    try {
      const res = await axios.post(
        "http://localhost:8081/experiment/upload?userid=1",
        {
          data: formData,
          headers: {
            "Content-Type": "multipart/form-data",
            ...formData.getHeaders,
          },
        }
      );
      console.log(res.data);
    } catch (err) {
      console.log(err);
    }
  };

  // const uploadToDB = () => {
  //   let formData = new FormData();
  //   formData.append("file", acceptedFiles[0].path);
  //   axios
  //     .post(
  //       "http://localhost:8081/experiment/upload/files?userid=1",
  //       formData,
  //       {
  //         headers: {
  //           Authorization: localStorage.getItem("Authorization"),
  //           "Content-Type": "multipart/form-data",
  //         },
  //       }
  //     )
  //     .then((res) => console.log(res.data))
  //     .catch((err) => console.log(err));
  // };

  const handleOnSubmit = (e) => {
    e.preventDefault();
    if (formData.getAll("file").length > 0) {
      console.log(formData.getAll("file"));
      console.log(acceptedFiles);
      upDB(formData);
    }
  };

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
        <UploadButton
          onClick={(e) => handleOnSubmit(e)}
          disabled={!acceptedFiles}
        >
          Upload
        </UploadButton>
      </aside>
    </section>
  );
};

export default UploadData;
