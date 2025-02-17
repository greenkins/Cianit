package ru.greenkins.agencymejb.service;

import jakarta.ejb.Remote;
import ru.greenkins.agencymejb.model.Flat;

import java.util.List;

@Remote
public interface FlatComparisonService {
    public Flat getCheapest(Flat flat1, Flat flat2);
}
