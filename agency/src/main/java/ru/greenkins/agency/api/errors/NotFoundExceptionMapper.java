package ru.greenkins.agency.api.errors;

import jakarta.ws.rs.NotFoundException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;

public class NotFoundExceptionMapper implements ExceptionMapper<NotFoundException> {
    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(NotFoundException exception) {
        String path = uriInfo.getPath();
        return getResponse(404, "Не найдено", path);
    }

    public static Response getResponse(int code, String error, String path, String details) {
        NotFoundAnswer notFoundAnswer = new NotFoundAnswer(error, path);
        notFoundAnswer.setDetails(details);
        return Response.status(code)
                .entity(notFoundAnswer)
                .type(MediaType.APPLICATION_XML)
                .build();
    }

    public static Response getResponse(int code, String error, String path) {
        return getResponse(code, error, path, null);
    }
}
