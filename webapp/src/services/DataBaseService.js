import http from "../baseAPI";

// TODO: Clean up code / Restructure
export const DeleteFile = async (fileid) => {
  http.delete("/data/files/delete?fileid=" + fileid);
};
