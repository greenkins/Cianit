package ru.greenkins.housingm.api.errors;

import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;

import java.util.Arrays;

@Provider
@XmlAccessorType(XmlAccessType.FIELD)    // Автоматическая сериализация полей
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
