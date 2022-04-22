import { LoadingOverlay } from "@mantine/core";
import React, { useEffect, useState } from "react";
import http from "../baseAPI";
import { DeleteFile } from "../services/DataBaseService";
import { Button } from "./styles/Button";

const FileList = ({ setData }) => {
  const [userId, setUserId] = useState([]);
  const [files, setFiles] = useState([]);
  const [visible, setVisible] = useState(false);

  useEffect(() => {
    let isMounted = true;
    http.get("/manage/userid").then((res) => {
      if (isMounted) setUserId(res.data);
      http.get("/data/files/get?userid=" + res.data).then((res) => {
        if (isMounted) setFiles(res.data);
      });
    });
    return () => {
      isMounted = false;
    };
  }, [userId]);

  const getData = async ({ fileid }) => {
    try {
      const res = await http.get("/data/files/read?fileid=" + fileid);
      return setData(
        res.data.split("\n").filter((element) => {
          if (Object.keys(element).length !== 0) {
            return true;
          }
          return false;
        })
      ).then(() => setVisible(false));
    } catch (err) {
      setVisible(false);
    }
  };

  const handleOnClick = (fileid) => {
    setVisible(true);
    getData({ fileid: fileid });
  };

  const handleOnDelete = (fileid) => {
    DeleteFile(fileid);
  };

  return (
    <div>
      <LoadingOverlay visible={visible} />
      <ol>
        {files.length > 0 ? (
          files.map((f) => (
            <li key={f.key}>
              {f.value}{" "}
              <Button onClick={() => handleOnClick(f.key)}>Load</Button>
              <Button onClick={() => handleOnDelete(f.key)}>Delete</Button>
            </li>
          ))
        ) : (
          <p>No previous data</p>
        )}
      </ol>
    </div>
  );
};

export default FileList;
