package ru.greenkins.housing.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
@Path("/enums")
public class EnumsController {
    @GET
    @Path("/transport")
    @Produces("application/xml")
    public Response getTransport() { // TODO: complete request
        throw new ServerErrorException(Response.Status.INTERNAL_SERVER_ERROR);
    }
}
