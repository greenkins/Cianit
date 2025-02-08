package ru.greenkins.agency.api.errors;

import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ServerErrorExceptionMapper implements ExceptionMapper<ServerErrorException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(ServerErrorException exception) {
        String path = uriInfo.getPath(); // Получаем текущий путь
        return getResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Внутренняя ошибка сервера", path);
    }

    public static Response getResponse(int code, String error, String path) {
        if (error == null) error = "Внутренняя ошибка сервера";
        ServerErrorAnswer errorResponse = new ServerErrorAnswer(
                error,
                path
        );
        return Response.status(code)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_XML)
                .build();
    }

    public static Response getResponse(String error, String path) {
        return getResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), error, path);
    }
}