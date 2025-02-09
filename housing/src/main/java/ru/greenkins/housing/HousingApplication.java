package ru.greenkins.housing;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import ru.greenkins.housing.api.EnumsController;
import ru.greenkins.housing.api.FlatController;
import ru.greenkins.housing.api.cors.CorsFilter;
import ru.greenkins.housing.api.errors.*;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/cian/housing")
public class HousingApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        // Controllers (Jax-RS resources)
        classes.add(FlatController.class);
        classes.add(EnumsController.class);
        // ErrorMappers
        classes.add(BadRequestExceptionMapper.class);
        classes.add(ServerErrorExceptionMapper.class);
        classes.add(NotFoundFlatExceptionMapper.class);
        classes.add(NotFoundExceptionMapper.class);
        classes.add(UniversalExceptionMapper.class);
        // CORS
        classes.add(CorsFilter.class);
        return classes;
    }
}