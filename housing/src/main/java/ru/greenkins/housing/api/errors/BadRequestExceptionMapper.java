package ru.greenkins.housing.api.errors;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

// Обработчик 400ой ошибки
@Provider
public class BadRequestExceptionMapper implements ExceptionMapper<IllegalArgumentException> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(IllegalArgumentException exception) {
        String path = uriInfo.getPath(); // Получаем текущий путь
        BadRequestAnswer errorResponse = new BadRequestAnswer(
                400,
                "Неверный запрос",
                path
        );
        return Response.status(Response.Status.BAD_REQUEST)
                .entity(errorResponse)
                .type(MediaType.APPLICATION_XML)
                .build();
    }
}