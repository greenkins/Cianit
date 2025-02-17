package ru.greenkins.agencymejb;

import jakarta.ejb.Stateless;
import ru.greenkins.agencymejb.model.Flat;


@Stateless
public class FlatComparisonServiceImpl implements FlatComparisonService {
    @Override
    public Flat getCheapest(String id1, String id2) {
        // Вызов Housing API для получения данных и сравнения
        return new Flat(1); // Вернуть наименьшую квартиру (заглушка)
    }
}