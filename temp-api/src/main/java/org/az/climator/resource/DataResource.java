package org.az.climator.resource;

import org.az.climator.entity.DataEntity;
import org.az.climator.entity.UserEntity;
import org.az.climator.services.DataService;
import org.bson.types.ObjectId;
import org.hibernate.service.spi.InjectService;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
//TODO: Improve security
@Path("/data")
public class DataResource {

    @Inject
    DataService dataService;

    @POST
    @Path("/upload")
    @Transactional
    @RolesAllowed("user")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response handleFileUploadForm(@QueryParam("userid") Long id, @MultipartForm MultipartFormDataInput input){
        try {
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
    @RolesAllowed("user")
    @Path("files/get")
    public Response getUserFile(@QueryParam("userid") Long id){
        try {
            Map<ObjectId, String> files = dataService.getUserFiles(id);
            return Response.ok(files).build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @RolesAllowed("user")
    @Path("files/read")
    public Response readFile(@QueryParam("fileid") String id) throws IOException {
        ObjectId objectId = new ObjectId(id);
        byte[] csvFromDB = dataService.getCSVFromDB(objectId);
        if(csvFromDB != null) {
            return Response.ok(csvFromDB).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @RolesAllowed("user")
    @Path("files/delete")
    public Response deleteFile(@QueryParam("fileid") String id) {
        ObjectId objectId = new ObjectId(id);
        dataService.deleteFile(objectId);
        return Response.noContent().build();
    }

}