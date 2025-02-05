package ru.greenkins.housing.service;

import jakarta.enterprise.context.ApplicationScoped;

import ru.greenkins.housing.model.Coordinates;
import ru.greenkins.housing.model.Flat;
import ru.greenkins.housing.model.House;
import ru.greenkins.housing.model.Transport;
import ru.greenkins.housing.util.FlatServiceUtils;


import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@ApplicationScoped
public class FlatServiceImpl implements FlatService {
    private final List<Flat> flats = new CopyOnWriteArrayList<>();
    private final AtomicInteger idGenerator;

    public FlatServiceImpl() {
        // Восстанавливаем коллекцию при старте
        // flats.addAll(persistenceService.loadCollectionFromDatabase());
        this.fillWithTestData(100);
        this.idGenerator = new AtomicInteger(this.flats.size());
    }

    // Добавление новой квартиры
    public boolean addFlat(Flat flat) {
        if (flat.getHouse().getYear() > java.time.LocalDate.now().getYear())
            return false;
        flat.setId(idGenerator.getAndIncrement());
        flat.setCreationDate(LocalDate.now());
        flats.add(flat);
        return true;
    }

    // Получить все квартиры в коллекции
    public List<Flat> getAllFlats() {
        return Collections.unmodifiableList(flats);
    }

    // Получение всех квартир с фильтрацией, сортировкой и пагинацией
    public List<Flat> getFlats(int page, int size, String sort, String filter) throws IllegalArgumentException {
        // Применяем фильтры
        List<Flat> filteredFlats = this.flats;
        if (!filter.isEmpty())
            filteredFlats = this.applyFilter(filter);
        // Сортируем
        if (sort != null && !sort.isEmpty())
            FlatServiceUtils.applySorting(filteredFlats, sort);
        // Пагинация
        int total = filteredFlats.size();
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, total);
        if (fromIndex >= total) {
            return Collections.emptyList();
        }
        return filteredFlats.subList(fromIndex, toIndex);
    }

    // Получение квартиры по ID
    public Optional<Flat> getFlatById(int id) {
        return flats.stream()
                .filter(flat -> flat.getId() == id)
                .findFirst();
    }

    // Обновление квартиры по ID
    public boolean updateFlat(int id, Flat updatedFlat) {
        for (int i = 0; i < flats.size(); i++) {
            if (flats.get(i).getId() == id) {
                updatedFlat.setId(id);
                updatedFlat.setCreationDate(flats.get(i).getCreationDate());
                flats.set(i, updatedFlat);
                return true;
            }
        }
        return false;
    }

    // Удаление квартиры по ID
    public boolean deleteFlat(int id) {
        return flats.removeIf(flat -> flat.getId() == id);
    }

    // Удаление квартир с указанным статусом "new"
    public boolean deleteAnyFlatWithNewStatus(boolean isNew) {
        return flats.removeIf(flat -> Objects.equals(flat.getIsNew(), isNew));
    }

    // Удаление всех квартир по имени дома
    public boolean deleteFlatsByHouseName(String houseName) {
        return flats.removeIf(flat -> flat.getHouse() != null && houseName.equals(flat.getHouse().getName()));
    }

    // Получение всех значений перечисления Transport
    public List<String> getTransportValues() {
        return Arrays.stream(Transport.values())
                .map(Enum::name)
                .collect(Collectors.toList());
    }

    // ------------- Filtration -------------------
    // Применение фильтрации
    private List<Flat> applyFilter(String filter) throws IllegalArgumentException {
        if (filter == null || filter.isEmpty()) {
            return new ArrayList<>(flats);
        }
        // Пример фильтрации: area>50,name~Kia
        return flats.stream()
                .filter(flat -> {
                    boolean matches = true;
                    for (String condition : filter.split(",")) {
                        String[] parts = condition.split("[~><=]");
                        if (parts.length == 2) {
                            String field = parts[0];
                            String value = parts[1];
                            matches &= FlatServiceUtils.matchesFilter(flat, field, value, condition);
                        } else throw new IllegalArgumentException("Invalid filter: " + filter); // TODO: проверить
                    }
                    return matches;
                })
                .collect(Collectors.toList());
    }

    // Вспомогательные геттеры
    public int getFlatsCount() {
        return flats.size();
    }

    // ------------- Test Samples -----------------
    /**
     * Fills the flat collection with test data.
     */
    public void fillWithTestData(int dataSize) {
        flats.clear(); // Clear existing data if any
        for (int i = 1; i <= dataSize; i++) {
            flats.add(createTestFlat(i));
        }
    }

    /**
     * Creates a single test Flat instance.
     *
     * @param id The ID of the flat.
     * @return A Flat object with test data.
     */
    private Flat createTestFlat(int id) {
        return new Flat(
                id,
                "TestFlat" + id,
                new Coordinates(10 * id, 20 * id),
                LocalDate.now(),
                50.0 + id, // Example area
                2L + id,   // Example number of rooms
                30 + id,   // Example living space
                id % 2 == 0, // Alternate isNew between true and false
                Transport.FEW, // Example transport
                new House("TestHouse" + id % 10, 2000 + id, 5L + id, id % 3 + 1) // Example house data
        );
    }
}
