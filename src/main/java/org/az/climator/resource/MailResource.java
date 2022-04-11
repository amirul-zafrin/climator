package org.az.climator.resource;

import org.az.climator.entity.UserEntity;
import org.az.climator.template.ActivationMailTemplate;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;


@Path("/mail")
@Tag(name = "Mail Resource", description = "Mailing REST APIs")
public class MailResource {
    @GET
    @Path("/activation")
    @Operation(
        operationId = "sendActivationLink",
        summary = "Send Activation Link",
        description = "description")
//    TODO: Return Response
    public Response sendActivationLink(@QueryParam("id") Long id) {
        UserEntity user = UserEntity.findById(id);
        URI uri = URI.create("/activation/" + user.id + "/" + user.activationCode.token);
        ActivationMailTemplate.activation(user.username,uri.toString())
//                dirox83250@royins.com [Testing email]
                .to(user.email)
                .subject("Activation")
                .send();
        return Response.ok().entity("Activation code sent to: " + user.email).build();
    }

}
