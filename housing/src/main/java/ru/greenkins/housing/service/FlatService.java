package ru.greenkins.housing.service;

import ru.greenkins.housing.model.Flat;
import java.util.List;
import java.util.Optional;

public interface FlatService {
    boolean addFlat(Flat flat); // Добавление квартиры
    Optional<Flat> getFlatById(int id); // Получение квартиры по ID
    List<Flat> getAllFlats(); // Получение всех квартир
    boolean updateFlat(int id, Flat flat); // Обновление квартиры
    boolean deleteFlat(int id); // Удаление квартиры
}
