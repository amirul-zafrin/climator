import React, { useState, useEffect } from "react";
import Dropzone from "react-dropzone-uploader";
import http from "../baseAPI";

const DropZone = () => {
  const [userId, setUserId] = useState([]);

  useEffect(() => {
    let isMounted = true;
    http.get("/manage/userid").then((res) => {
      if (isMounted) setUserId(res.data);
    });
    return () => {
      isMounted = false;
    };
  }, [userId]);

  const getUploadParams = () => {
    return {
      url: "http://localhost:8081/experiment/upload?userid=" + userId,
    };
  };

  const handleSubmit = (files, allFiles) => {
    console.log(files.map((f) => f.meta));
    allFiles.forEach((f) => f.remove());
  };

  return (
    <>
      <Dropzone
        getUploadParams={getUploadParams}
        onSubmit={handleSubmit}
        accept="text/csv"
      />
    </>
  );
};

export default DropZone;
