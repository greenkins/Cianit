package ru.greenkins.agency.api;


import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.Getter;
import ru.greenkins.agency.api.errors.ServerErrorExceptionMapper;

@Getter
@Path("/get-cheapest/{id1}/{id2}")
public class CheapController {
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces("application/xml")
    public Response getCheapest(@PathParam("id1") String id1, @PathParam("id2") String id2) {
        return ServerErrorExceptionMapper.getResponse("Не готово!", uriInfo.getPath());
    }
}
