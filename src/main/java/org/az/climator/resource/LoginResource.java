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
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

//TODO: Next time maybe just use keycloak

@Path("login")
public class LoginResource {

//    TODO: Login logic & return JWT to user

    @Inject
    JWTService jwtService;

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Login Success"
    )

//    TODO: Tidy up > move to services
    public Response loginConfirmation(LoginDTO info){

        UserEntity user = UserEntity.existUsername(info.getUsername()) ? UserEntity.searchByUsername(info.getUsername()) : null;

        if(user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(info.getUsername() + " is not exist!")
                    .type(MediaType.TEXT_PLAIN_TYPE).build();
        }

        boolean checkPassword = BcryptUtil.matches(info.getPassword(),user.encodedPassword);
        boolean activated = user.activated;

        if(!activated) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Please activate your account first!")
                    .type(MediaType.TEXT_PLAIN_TYPE).build();

        }else if(checkPassword && activated) {
            return Response.ok(jwtService.generateJWT(user)).type(MediaType.TEXT_PLAIN_TYPE).build();
        }
        else {
            return Response.status(Response.Status.UNAUTHORIZED).entity("Wrong password")
                    .type(MediaType.TEXT_PLAIN_TYPE).build();
        }
    }

}
