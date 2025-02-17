package ru.greenkins.agencymejb;

import ru.greenkins.agencymejb.model.Flat;

public interface FlatComparisonService {
    public Flat getCheapest(String id1, String id2);
}
