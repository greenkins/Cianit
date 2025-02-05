package ru.greenkins.agency.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.Getter;
import ru.greenkins.agency.api.errors.ServerErrorExceptionMapper;

@Getter
@Path("/get-ordered-by-time-to-metro")
public class MetroController {
    @Context
    private UriInfo uriInfo;

    @GET
    @Path("/{by-transport}/{desc}")
    @Produces("application/xml")
    public Response getMetro(@PathParam("by-transport") boolean byTransport, @PathParam("desc") boolean desc) {
        return ServerErrorExceptionMapper.getResponse("Не готово!", uriInfo.getPath());
    }
}
