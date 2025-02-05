package ru.greenkins.housing.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.Getter;

@Getter
@Path("/enums")
public class EnumsController {
    String jsonTransportTypes = "{\n" +
            "\"enumName\": \"Transport\",\n" +
            "\"values\": [\n" +
            "\"FEW\",\n" +
            "\"LITTLE\",\n" +
            "\"NORMAL\"\n" +
            "]\n" +
            "}";

    String xmlTransportTypes = "<enum>" +
            "<enumName>Transport</enumName> " +
            "<values>" +
            "<value>FEW</value>" +
            "<value>LITTLE</value>" +
            "<value>NORMAL</value>" +
            "</values>" +
            "</enum>";


    @GET
    @Path("/transport")
    @Produces("application/xml")
    public Response getTransport() {
        return Response.status(Response.Status.OK).entity(jsonTransportTypes).type(MediaType.APPLICATION_JSON).build();
    }

    @POST
    @Path("/transport")
    @Produces("application/xml")
    public Response getXmlTransport() {
        return Response.status(Response.Status.OK).entity(xmlTransportTypes).type(MediaType.APPLICATION_XML).build();
    }
}
