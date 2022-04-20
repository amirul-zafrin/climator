package org.az.climator.resource;

import org.az.climator.dto.RegistrationDTO;
import org.az.climator.services.RegistrationService;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;

import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("register")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RegistrationResource {

    @POST
    @Path("/create")
    @Transactional
    @APIResponse(
            responseCode = "201",
            description = "Create User"
    )
    public Response create(@RequestBody RegistrationDTO info) {
        return RegistrationService.validation(info) ? Response.ok(RegistrationService.getMessage()).build() :
                Response.status(Response.Status.BAD_REQUEST).entity(RegistrationService.getMessage()).build();
    }

    @PUT
    @Transactional
    @Path("/activation/{id}")
    @APIResponse(
            responseCode = "202",
            description = "Activation Succeed"
    )
    public Response activated(@PathParam("id") Long id, @QueryParam("token") String token){
        return RegistrationService.userActivation(id, token) ? Response.accepted().build() :
                Response.status(Response.Status.BAD_REQUEST).build();
    }

    @PUT
    @Transactional
    @Path("/activation/reset")
    @APIResponse(
        responseCode = "200",
        description = "Reset activation code"
    )
    public Response resetActivationToken(@QueryParam("id") Long id) {
        RegistrationService.resetActivation(id);
        return Response.ok().build();
    }
}
