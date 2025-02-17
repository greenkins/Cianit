package ru.greenkins.agencymrest;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import ru.greenkins.agencymrest.api.CheapController;
import ru.greenkins.agencymrest.api.MetroController;
import ru.greenkins.agencymrest.api.cors.CorsFilter;
import ru.greenkins.agencymrest.api.errors.BadRequestExceptionMapper;
import ru.greenkins.agencymrest.api.errors.NotFoundExceptionMapper;
import ru.greenkins.agencymrest.api.errors.ServerErrorExceptionMapper;
import ru.greenkins.agencymrest.api.errors.UniversalExceptionMapper;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/cian/agency")
public class AgencyApplication extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        // Controllers (Jax-RS resources)
        classes.add(CheapController.class);
        classes.add(MetroController.class);
        // ErrorMappers
        classes.add(BadRequestExceptionMapper.class);
        classes.add(ServerErrorExceptionMapper.class);
        classes.add(NotFoundExceptionMapper.class);
        classes.add(UniversalExceptionMapper.class);
        // CORS
        classes.add(CorsFilter.class);
        return classes;
    }
}