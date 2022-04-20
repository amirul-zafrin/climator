package org.az.climator.services;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DataService{

    private static MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
    private static MongoDatabase mydb = mongoClient.getDatabase("DataEntity");
    private static GridFSBucket gridFSBucket = GridFSBuckets.create(mydb, "files");

    public ObjectId dataToDB(@MultipartForm MultipartFormDataInput input) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        List<String> fileNames = new ArrayList<>();
        ObjectId objectId = null;

        List<InputPart> inputParts = uploadForm.get("file");
        System.out.println("inputParts size: " + inputParts.size());
        String fileName = null;

        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                fileNames.add(fileName);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                objectId = DataService.saveCSVToDB(fileName, inputStream);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return objectId;
    }

    public void getCSVFromDB(ObjectId objectId) throws IOException {
        File tempFile = null;
        Document id = new Document();
        id.put("_id", objectId);
        GridFSFile fsFile = gridFSBucket.find(id).first();

        if (fsFile != null) {

            String tempDir = System.getProperty("java.io.tmpdir");

            tempFile = new File(tempDir + "/" + fsFile.getFilename());

            FileOutputStream streamToDownloadTo = new FileOutputStream(tempFile);

            gridFSBucket.downloadToStream(objectId, streamToDownloadTo);
            streamToDownloadTo.close();

        }
    }

    private String getFileName(MultivaluedMap<String, String> header) {
        String[] contentDisposition = header.getFirst("Content-Disposition").split(";");
        for (String filename : contentDisposition) {
            if ((filename.trim().startsWith("filename"))) {
                String[] name = filename.split("=");
                String finalFileName = name[1].trim().replaceAll("\"", "");
                return finalFileName;
            }
        }
        return "unknown";
    }


    private static ObjectId saveCSVToDB(String filename, InputStream inputStream) {
        ObjectId fileId = null;
        try {
            GridFSUploadOptions options = new GridFSUploadOptions()
                    .chunkSizeBytes(1024 * 1024)
                    .metadata(new Document("type", "csv").append("content_type","text/csv"));
            
            fileId = gridFSBucket.uploadFromStream(filename, inputStream, options);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            mongoClient.close();
        }
        return fileId;
    }

}