package ru.greenkins.housing;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import ru.greenkins.housing.api.EnumsController;
import ru.greenkins.housing.api.FlatController;
import ru.greenkins.housing.api.errors.*;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api/cian/housing")
public class HousingApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        classes.add(FlatController.class);
        classes.add(EnumsController.class);
        classes.add(BadRequestExceptionMapper.class);
        classes.add(ServerErrorExceptionMapper.class);
        classes.add(NotFoundFlatExceptionMapper.class);
        classes.add(NotFoundExceptionMapper.class);
        classes.add(UniversalExceptionMapper.class);
        return classes;
    }
}