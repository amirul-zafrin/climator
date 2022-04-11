package org.az.climator.resource;

import io.quarkus.elytron.security.common.BcryptUtil;
import org.az.climator.entity.UserEntity;
import org.az.climator.dto.LoginDTO;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("login")
public class LoginResource {

//    TODO: Login logic

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @APIResponse(
        responseCode = "200",
        description = "Login Success"
    )
    public Response loginConfirmation(LoginDTO info){
        UserEntity user = UserEntity.searchByUsername(info.getUsername());
        boolean checkPassword = BcryptUtil.matches(info.getPassword(),user.encodedPassword);
        boolean activated = user.activated;

        if(checkPassword && activated) {
            return Response.ok().build();
        }
        else {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

}
