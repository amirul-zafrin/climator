package org.az.climator.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.az.climator.entity.UserEntity;
import org.az.climator.services.JWTService;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/manage")
public class UserResource {

//    TODO: Optimize this class: avoid jwtservice injection
    @Inject
    JWTService jwtService;

    @Context
    SecurityContext securityContext;

    @GET
    @RolesAllowed("user")
    @Path("/username")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUsername() {
        String username = securityContext.getUserPrincipal().getName();
        return Response.ok(username).build();
    }

    @PUT
    @Transactional
    @RolesAllowed("user")
    @Path("/update-pass/{id}")
    @APIResponse(
        responseCode = "200",
        description = "Update User's Password"
    )
    public Response updatePasswordById(@PathParam("id") Long id, @QueryParam("newPass") String newPass) {
        if (jwtService.verifyJWT(id, securityContext)) {
            UserEntity user = UserEntity.findById(id);
            user.encodedPassword = BcryptUtil.bcryptHash(newPass);
            return Response.status(Response.Status.ACCEPTED).build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @DELETE
    @Transactional
    @RolesAllowed("user")
    @Path("/delete/{id}")
    @APIResponse(
            responseCode = "204",
            description = "Delete User"
    )
    public Response deleteById(@PathParam("id") Long id) {
        if (jwtService.verifyJWT(id, securityContext)){
            UserEntity.deleteById(id);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }


}
