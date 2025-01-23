package test.service;

import ru.greenkins.housing.model.Coordinates;
import ru.greenkins.housing.model.Flat;
import ru.greenkins.housing.model.House;
import ru.greenkins.housing.model.Transport;


import java.time.LocalDate;
import java.util.concurrent.CopyOnWriteArrayList;

public class TestDataService {

    private final CopyOnWriteArrayList<Flat> flatCollection;

    public TestDataService(CopyOnWriteArrayList<Flat> flatCollection) {
        this.flatCollection = flatCollection;
    }
}