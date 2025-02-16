package ru.greenkins.housingm.api.errors;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

import java.util.Arrays;

@Provider
public class UniversalExceptionMapper implements ExceptionMapper<Exception> {

    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(Exception exception) {
        System.out.println("Не удалось обработать запрос: " + Arrays.toString(exception.getStackTrace()) + exception.getMessage());
        String path = uriInfo.getPath(); // Получаем текущий путь
        return BadRequestExceptionMapper.getResponse("Неверный запрос", path);
    }
}
