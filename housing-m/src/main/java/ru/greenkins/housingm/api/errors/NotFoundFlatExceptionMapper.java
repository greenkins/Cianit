package ru.greenkins.housingm.api.errors;

import jakarta.ws.rs.NotAcceptableException;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class NotFoundFlatExceptionMapper implements ExceptionMapper<NotAcceptableException> {
    @Context
    private UriInfo uriInfo;

    @Override
    public Response toResponse(NotAcceptableException exception) {
        String path = uriInfo.getPath();
        return getResponse(422, "Не найдено", path);    }

    public static Response getResponse(int code, String error, String path, String details){
        return NotFoundExceptionMapper.getResponse(code, error, path, details);
    }

    public static Response getResponse(int code, String error, String path){
        return NotFoundExceptionMapper.getResponse(code, error, path);
    }
}
