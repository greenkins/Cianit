package ru.greenkins.housing;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.core.Application;
import ru.greenkins.housing.api.FlatController;

import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/api")
public class HousingApplication extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(FlatController.class);
        return classes;
    }
}