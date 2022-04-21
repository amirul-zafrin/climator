package org.az.climator.services;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.az.climator.entity.DataEntity;
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
import java.util.*;
import java.util.stream.Collectors;

public class DataService{

    public HashMap<ObjectId,String> dataToDB(@MultipartForm MultipartFormDataInput input) {
        Map<String, List<InputPart>> uploadForm = input.getFormDataMap();
        ObjectId objectId = null;

        HashMap<ObjectId, String> result = new HashMap<>();

        List<InputPart> inputParts = uploadForm.get("file");
        System.out.println("inputParts size: " + inputParts.size());
        String fileName = null;

        for (InputPart inputPart : inputParts) {
            try {
                MultivaluedMap<String, String> header = inputPart.getHeaders();
                fileName = getFileName(header);
                System.out.println("Filename:" + fileName);

//                Experiment
                System.out.println("Header: "+inputPart.getHeaders());


                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                objectId = DataService.saveCSVToDB(fileName, inputStream);
                result.put(objectId, fileName);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void getCSVFromDB(ObjectId objectId) throws IOException {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase mydb = mongoClient.getDatabase("DataEntity");
        GridFSBucket gridFSBucket = GridFSBuckets.create(mydb, "files");
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

    public static List<String> getUserFiles(Long id) {
        List<DataEntity> dataEntities = DataEntity.searchByUserId(id);
        return dataEntities.stream().map(f -> f.filename).collect(Collectors.toList());
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
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase mydb = mongoClient.getDatabase("DataEntity");
        GridFSBucket gridFSBucket = GridFSBuckets.create(mydb, "files");
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