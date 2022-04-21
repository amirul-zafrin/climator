import React, { useEffect, useState } from "react";
import http from "../baseAPI";
import { Button } from "./styles/Button";

const FileList = ({ setData }) => {
  const [userId, setUserId] = useState([]);
  const [files, setFiles] = useState([]);

  useEffect(() => {
    let isMounted = true;
    http
      .get("/manage/userid", {
        headers: {
          Authorization: localStorage.getItem("Authorization"),
        },
      })
      .then((res) => {
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
      setData(
        res.data.split("\n").filter((element) => {
          if (Object.keys(element).length !== 0) {
            return true;
          }
          return false;
        })
      );
    } catch (err) {
      console.log(err);
    }
  };

  const handleOnClick = (fileid) => {
    getData({ fileid: fileid });
  };

  return (
    <div>
      <ol>
        {files.map((f) => (
          <li key={f.key}>
            {f.value} <Button onClick={() => handleOnClick(f.key)}>Load</Button>
          </li>
        ))}
      </ol>
    </div>
  );
};

export default FileList;
