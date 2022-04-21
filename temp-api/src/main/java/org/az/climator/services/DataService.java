package org.az.climator.services;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.gridfs.GridFSBucket;
import com.mongodb.client.gridfs.GridFSBuckets;
import com.mongodb.client.gridfs.GridFSDownloadStream;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.mongodb.client.gridfs.model.GridFSUploadOptions;
import org.az.climator.entity.DataEntity;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.InputPart;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.enterprise.context.RequestScoped;
import javax.inject.Singleton;
import javax.ws.rs.core.MultivaluedMap;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@RequestScoped
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
                fileName = getFilename(header);
                System.out.println("Filename:" + fileName);
                InputStream inputStream = inputPart.getBody(InputStream.class, null);
                objectId = DataService.saveCSVToDB(fileName, inputStream);
                result.put(objectId, fileName);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public byte[] getCSVFromDB(ObjectId objectId) throws IOException {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        MongoDatabase mydb = mongoClient.getDatabase("DataEntity");
        GridFSBucket gridFSBucket = GridFSBuckets.create(mydb, "files");

        Document id = new Document();
        id.put("_id", objectId);
        GridFSFile fsFile = gridFSBucket.find(id).first();

        if (fsFile != null) {
            GridFSDownloadStream gridFSDownloadStream = gridFSBucket.openDownloadStream(objectId);
            byte[] bytes = gridFSDownloadStream.readAllBytes();
            return bytes;
        }
        return null;
    }

    public Map<ObjectId, String> getUserFiles(Long id) {
        List<DataEntity> dataEntities = DataEntity.searchByUserId(id);
        return dataEntities.stream().collect(Collectors.toMap(d -> new ObjectId(d.objectId), d -> d.filename));
    }

    private String getFilename(MultivaluedMap<String, String> header) {
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