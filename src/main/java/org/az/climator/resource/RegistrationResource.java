package org.az.climator.resource;

import org.az.climator.entity.ActivationEntity;
import org.az.climator.entity.UserEntity;
import org.az.climator.dto.RegistrationDTO;
import org.az.climator.validation.EmailValidation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("register")
public class RegistrationResource {

    @POST
    @Path("/create")
    @Transactional
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(
            responseCode = "201",
            description = "Create User",
            content = @Content(mediaType = MediaType.APPLICATION_JSON)
    )
    public Response create(@RequestBody RegistrationDTO info) {

        if(!EmailValidation.validate(info.getEmail())) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Email already registered!").build();
        }

        UserEntity user = UserEntity.addUser(info.getEmail(), info.getUsername(), info.getPassword());
        ActivationEntity.setActivation(user);

        return user.isPersistent() ? Response.created(URI.create("register/create/"+user.id)).build()
                : Response.status(Response.Status.BAD_REQUEST).build();

    }

    @PUT
    @Transactional
    @Path("/activation/{id}")
    public Response activated(@PathParam("id") Long id, @QueryParam("token") String token){
        UserEntity user = UserEntity.findById(id);
        boolean activation = user.activationCode.token == token;
        boolean expired = ActivationEntity.checkExpiration(user);

        if(activation && expired) {
            user.activated = true;
            return Response.accepted().build();
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).entity("Token is invalid or expired! Please request new token").build();
        }
    }

    @PUT
    @Transactional
    @Path("/activation/reset")
    public Response resetActivationToken(@QueryParam("id") Long id) {
        UserEntity user = UserEntity.findById(id);
        String token = user.activationCode.token;
        String newToken = ActivationEntity.resetActivation(user);
        return token != newToken? Response.ok().entity("New Token Generated").build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }
}
