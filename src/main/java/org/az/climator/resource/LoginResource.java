package org.az.climator.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.az.climator.entity.UserEntity;
import org.az.climator.dto.LoginDTO;
import org.az.climator.services.JWTService;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.NewCookie;
import javax.ws.rs.core.Response;

//TODO: Maybe just use keycloak

@Path("login")
public class LoginResource {

    @Inject
    JWTService jwtService;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.TEXT_PLAIN)
    @APIResponse(
        responseCode = "200",
        description = "Login Success"
    )
    public Response loginConfirmation(LoginDTO info) {

        if (!UserEntity.existUsername(info.getUsername())) {
            return Response.status(Response.Status.BAD_REQUEST).entity(info.getUsername() + " is not exist!").build();
        }

        UserEntity user = UserEntity.searchByUsername(info.getUsername());
        boolean checkPassword = BcryptUtil.matches(info.getPassword(), user.encodedPassword);

        if (!user.activated) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please activate your account first!")
                    .type(MediaType.TEXT_PLAIN_TYPE).build();

        } else if (!checkPassword) {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Wrong password")
                    .type(MediaType.TEXT_PLAIN_TYPE).build();
        }
            return Response.ok(jwtService.generateJWT(user)).type(MediaType.TEXT_PLAIN_TYPE).build();
//        return Response.ok(user.username).cookie(new NewCookie("jwt", jwtService.generateJWT(user))).build();
    }
}
