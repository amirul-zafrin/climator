package org.az.climator.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.az.climator.entity.UserEntity;
import org.az.climator.dto.LoginDTO;
import org.az.climator.services.JWTService;
import org.az.climator.services.LoginService;
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
        if (LoginService.verify(info) == null) {
            return Response.ok("Bearer " + jwtService.generateJWT(info.getUsername())).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(LoginService.verify(info)).build();
    }
}
