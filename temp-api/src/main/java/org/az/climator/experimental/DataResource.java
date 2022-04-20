package org.az.climator.experimental;

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

@Path("/experiment/upload")
public class DataResource {

    @POST
    @Path("/files")
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response handleFileUploadForm(@QueryParam("userid") Long id, @MultipartForm MultipartFormDataInput input){
        try {
            DataService dataService = new DataService();
            ObjectId objectId = dataService.dataToDB(input);
            UserEntity user = UserEntity.findById(id);
            DataEntity.addData(user, "filename",objectId);
            return Response.ok().build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

    @GET
    @Path("/readfile")
    @Transactional
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response readFile(){
        try {
            ObjectId id = new ObjectId("625fb5a2716fc6557c6c31db");
            DataService dataService = new DataService();
            dataService.getCSVFromDB(id);

            return Response.ok().build();
        }catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

    }

}