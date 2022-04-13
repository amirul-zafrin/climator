package org.az.climator.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import io.smallrye.jwt.auth.principal.ParseException;
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
public class ProfileUpdateResource {

    @Inject
    JWTService jwtService;

    @Context
    SecurityContext securityContext;

    @CookieParam("jwt")
    String jwt;

    @PUT
    @Transactional
    @RolesAllowed("user")
    @Path("/update-pass/{id}")
    @APIResponse(
        responseCode = "200",
        description = "Update User's Password"
    )
    public Response updatePasswordById(@PathParam("id") Long id, @QueryParam("newPass") String newPass) throws ParseException {

        UserEntity user = UserEntity.findById(id);
        user.encodedPassword = BcryptUtil.bcryptHash(newPass);
        return jwtService.verifyJWTCookies(id, jwt) ? Response.ok().build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @DELETE
    @Transactional
    @RolesAllowed("user")
    @Path("/delete/{id}")
    @APIResponse(
            responseCode = "204",
            description = "Delete User"
    )
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteById(@PathParam("id") Long id) {
        if (jwtService.verifyJWT(id, securityContext)){
            UserEntity.deleteById(id);
            return Response.noContent().build();
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
