import React from "react";
import { useDropzone } from "react-dropzone";
import { UploadButton } from "./styles/Upload.styled";

const UploadData = () => {
  const { acceptedFiles, getRootProps, getInputProps } = useDropzone();

  const files = acceptedFiles.map((file) => (
    <li key={file.path}>
      <ul>{file.path} </ul>
      <ul>
        <UploadButton>Upload</UploadButton>
      </ul>
    </li>
  ));

  return (
    <section className="container">
      <div {...getRootProps({ className: "dropzone" })}>
        <input {...getInputProps()} />
        <p>Drag 'n' drop some files here, or click to select files</p>
      </div>
      <aside>
        <ul>{files}</ul>
      </aside>
    </section>
  );
};

export default UploadData;
