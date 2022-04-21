package org.az.climator.resource;

import org.az.climator.entity.DataEntity;
import org.az.climator.entity.UserEntity;
import org.az.climator.services.DataService;
import org.bson.types.ObjectId;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.HashMap;
import java.util.List;

@Path("/data")
public class DataResource {

    @POST
    @Path("/upload")
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response handleFileUploadForm(@QueryParam("userid") Long id, @MultipartForm MultipartFormDataInput input){
        try {
            DataService dataService = new DataService();
            HashMap<ObjectId, String> dbRes = dataService.dataToDB(input);
            UserEntity user = UserEntity.findById(id);

            for (ObjectId objectId: dbRes.keySet()) {
                DataEntity.addData(user, dbRes.get(objectId), String.valueOf(objectId));
            }

            return Response.ok().build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }
    @GET
    @Path("/getfiles")
    @Transactional
    public Response getUserFile(@QueryParam("userid") Long id){
        try {
            List<String> files = DataService.getUserFiles(id);
            return Response.ok(files).build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

}