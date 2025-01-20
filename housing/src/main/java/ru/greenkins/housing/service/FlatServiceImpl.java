package ru.greenkins.housing.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;

import lombok.NoArgsConstructor;
import ru.greenkins.housing.model.Coordinates;
import ru.greenkins.housing.model.Flat;
import ru.greenkins.housing.model.House;
import ru.greenkins.housing.model.Transport;
import ru.greenkins.housing.repository.FlatPersistenceService;


import java.io.StringWriter;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;

@ApplicationScoped
public class FlatServiceImpl implements FlatService {
    private final List<Flat> flats = new CopyOnWriteArrayList<>();
    private final AtomicInteger idGenerator = new AtomicInteger(1);
    private final FlatPersistenceService persistenceService = new FlatPersistenceService();

    public FlatServiceImpl() {
        // Восстанавливаем коллекцию при старте
        // flats.addAll(persistenceService.loadCollectionFromDatabase());
        this.fillWithTestData();
    }

    // Add a new flat
    public boolean addFlat(Flat flat) {
        flats.add(flat);
        return true;
    }

    // Get all flats
    public List<Flat> getAllFlats() {
        return Collections.unmodifiableList(flats);
    }

    // Get a flat by ID
    public Optional<Flat> getFlatById(int id) {
        return flats.stream()
                .filter(flat -> flat.getId() == id)
                .findFirst();
    }

    // Update a flat by ID
    public boolean updateFlat(int id, Flat updatedFlat) {
        for (int i = 0; i < flats.size(); i++) {
            if (flats.get(i).getId() == id) {
                flats.set(i, updatedFlat);
                return true;
            }
        }
        return false;
    }

    public boolean deleteFlat(int id) {
        return false;
    }

    // TODO test samples

    /**
     * Fills the flat collection with test data.
     */
    public void fillWithTestData() {
        flats.clear(); // Clear existing data if any
        for (int i = 1; i <= 10; i++) {
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
                new House("TestHouse" + id, 2000 + id, 5L + id, id % 3 + 1) // Example house data
        );
    }
}
