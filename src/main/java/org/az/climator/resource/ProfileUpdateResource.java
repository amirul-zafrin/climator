package org.az.climator.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.az.climator.entity.UserEntity;
import org.az.climator.validation.PasswordValidation.ValidPassword;
import org.eclipse.microprofile.openapi.annotations.enums.SecuritySchemeType;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.security.SecurityScheme;

import javax.annotation.security.RolesAllowed;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

@Path("/manage")
public class ProfileUpdateResource {

//    TODO: Add security features: JWT

    @PUT
    @Transactional
    @RolesAllowed("user")
    @Path("/update-pass/{id}")
    @APIResponse(
        responseCode = "200",
        description = "Update User's Password"
    )
    public Response updatePasswordById(@PathParam("id") Long id, @ValidPassword @QueryParam("newPass") String newPass) {
        UserEntity user = UserEntity.findById(id);
        String prevPassword = user.encodedPassword;
        user.encodedPassword = BcryptUtil.bcryptHash(newPass);

        return user.encodedPassword != prevPassword ? Response.ok().build()
                :  Response.status(Response.Status.BAD_REQUEST).build();
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
        boolean deleted = UserEntity.deleteById(id);
        return deleted ? Response.noContent().build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @GET
    @RolesAllowed("user")
    @Path("/me")
    public String me(@Context SecurityContext securityContext) {
        return securityContext.getUserPrincipal().getName();
    }

}
