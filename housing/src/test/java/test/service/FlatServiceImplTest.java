package test.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.greenkins.housing.model.Coordinates;
import ru.greenkins.housing.model.Flat;
import ru.greenkins.housing.model.House;
import ru.greenkins.housing.model.Transport;
import ru.greenkins.housing.service.FlatServiceImpl;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FlatServiceImplTest {

    private FlatServiceImpl flatService;

    @BeforeEach
    void setUp() {
        flatService = new FlatServiceImpl();
        flatService.fillWithTestData(10);
    }

    @Test
    void testAddFlat() {
        Flat newFlat = new Flat(
                0,
                "NewTestFlat",
                new Coordinates(100, 200),
                LocalDate.now(),
                75.0,
                3L,
                50,
                true,
                Transport.NORMAL,
                new House("NewTestHouse", 2021, 10L, 5)
        );

        assertTrue(flatService.addFlat(newFlat));
        List<Flat> flats = flatService.getAllFlats();
        assertEquals(11, flats.size());
        assertEquals("NewTestFlat", flats.get(10).getName());
    }

    @Test
    void testGetAllFlats() {
        List<Flat> flats = flatService.getAllFlats();
        assertEquals(10, flats.size());
    }

    @Test
    void testGetFlatById() {
        Optional<Flat> flat = flatService.getFlatById(1);
        assertTrue(flat.isPresent());
        assertEquals("TestFlat1", flat.get().getName());
    }

    @Test
    void testUpdateFlat() {
        Flat updatedFlat = new Flat(
                0,
                "UpdatedFlat",
                new Coordinates(50, 50),
                LocalDate.now(),
                80.0,
                4L,
                60,
                false,
                Transport.FEW,
                new House("UpdatedHouse", 2025, 15L, 3)
        );
        flatService.fillWithTestData(10);
        assertTrue(flatService.updateFlat(1, updatedFlat));

        Optional<Flat> flat = flatService.getFlatById(1);
        assertTrue(flat.isPresent());
        assertEquals("UpdatedFlat", flat.get().getName());
        assertEquals(80.0, flat.get().getArea());
    }

    @Test
    void testDeleteFlat() {
        assertTrue(flatService.deleteFlat(1));
        Optional<Flat> flat = flatService.getFlatById(1);
        assertFalse(flat.isPresent());
    }

    @Test
    void testDeleteAnyFlatWithNewStatus() {
        assertTrue(flatService.deleteAnyFlatWithNewStatus(true));
        List<Flat> flats = flatService.getAllFlats();
        assertTrue(flats.stream().noneMatch(Flat::getIsNew));
    }

    @Test
    void testDeleteFlatsByHouseName() {
        assertTrue(flatService.deleteFlatsByHouseName("TestHouse1"));
        List<Flat> flats = flatService.getAllFlats();
        assertTrue(flats.stream().noneMatch(flat -> "TestHouse1".equals(flat.getHouse().getName())));
    }

    @Test
    void testGetTransportValues() {
        List<String> transportValues = flatService.getTransportValues();
        assertEquals(Transport.values().length, transportValues.size());
        assertTrue(transportValues.contains("FEW"));
        assertTrue(transportValues.contains("NORMAL"));
    }

    @Test
    void testGetFlatsWithFilterAndPagination() {
        List<Flat> flats = flatService.getFlats("-name", "area>52");
        assertEquals(5, flats.size());
        assertTrue(flats.get(0).getArea() > 52);
    }
}

