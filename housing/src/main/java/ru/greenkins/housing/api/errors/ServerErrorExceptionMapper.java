package ru.greenkins.housing.api.errors;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

// Обработчик 400ой ошибки
@Provider
public class ServerErrorExceptionMapper implements ExceptionMapper<Exception> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(Exception exception) {
        String path = uriInfo.getPath(); // Получаем текущий путь
        BadRequestAnswer errorResponse = new BadRequestAnswer(
                500,
                "Внутренняя ошибка сервера",
                path
        );
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_XML)
                .build();
    }
}