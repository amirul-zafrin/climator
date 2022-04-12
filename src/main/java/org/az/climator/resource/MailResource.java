package org.az.climator.resource;

import io.smallrye.mutiny.Uni;
import org.az.climator.entity.UserEntity;
import org.az.climator.template.ActivationMailTemplate;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.net.URI;

@Path("mail")
public class MailResource {
    @GET
    @Path("activation/{id}")
    @APIResponse(
            responseCode = "200",
            description = "Sent"
    )
    public static Uni<Void> sendActivationLink(@PathParam("id") Long id) {
        UserEntity user = UserEntity.findById(id);
        URI uri = URI.create("localhost:8081/register/activation/" + user.id + "?token=" + user.activationCode.token);
        return ActivationMailTemplate.activation(user.username,uri.toString())
                .to(user.email)
                .subject("Activation")
                .send();
    }

}

