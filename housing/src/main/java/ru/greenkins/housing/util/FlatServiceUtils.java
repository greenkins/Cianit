package ru.greenkins.housing.util;

import ru.greenkins.housing.model.Flat;
import ru.greenkins.housing.model.Transport;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class FlatServiceUtils {
    // Логика для конкретного фильтра
    public static boolean matchesFilter(Flat flat, String field, String value, String condition) throws IllegalArgumentException{
        field = field.toLowerCase(); // TODO: check filters
        switch (field) {
            case "id":
                int id = Integer.parseInt(value);
                if (condition.contains(">")) return flat.getId() > id;
                if (condition.contains("<")) return flat.getId() < id;
                return flat.getId() == id;
            case "name":
                return flat.getName().contains(value);
            case "coordinates.x":
                long x = Long.parseLong(value);
                if (condition.contains(">")) return flat.getCoordinates().getX() > x;
                if (condition.contains("<")) return flat.getCoordinates().getX() < x;
                return flat.getCoordinates().getX() == x;
            case "coordinates.y":
                long y = Long.parseLong(value);
                if (condition.contains(">")) return flat.getCoordinates().getY() > y;
                if (condition.contains("<")) return flat.getCoordinates().getY() < y;
                return flat.getCoordinates().getY() == y;
            case "area":
                double area = Double.parseDouble(value);
                return condition.contains(">") ? flat.getArea() > area : flat.getArea() < area;
            case "numberofrooms":
                long rooms = Long.parseLong(value);
                if (condition.contains(">")) return flat.getNumberOfRooms() > rooms;
                if (condition.contains("<")) return flat.getNumberOfRooms() < rooms;
                return flat.getNumberOfRooms() == rooms;
            case "livingspace":
                int livingSpace = Integer.parseInt(value);
                if (condition.contains(">")) return flat.getLivingSpace() > livingSpace;
                if (condition.contains("<")) return flat.getLivingSpace() < livingSpace;
                return flat.getLivingSpace() == livingSpace;
            case "isnew":
            case "new":
                boolean isNew = Boolean.parseBoolean(value);
                return flat.getIsNew() == isNew;
            case "transport":
                String transport = value.toUpperCase(Locale.ENGLISH);
                return  ((transport.equals(Transport.FEW.toString()) && flat.getTransport() == Transport.FEW) ||
                        (transport.equals(Transport.LITTLE.toString()) && flat.getTransport() == Transport.LITTLE) ||
                        (transport.equals(Transport.NORMAL.toString()) && flat.getTransport() == Transport.NORMAL));
            case "house.name":
                return flat.getHouse().getName().equals(value);
            case "house.year":
                long houseYear = Long.parseLong(value);
                if (condition.contains(">")) return flat.getHouse().getYear() > houseYear;
                if (condition.contains("<")) return flat.getHouse().getYear() < houseYear;
                return flat.getHouse().getYear() == houseYear;
            case "house.numberoffloors":
                long houseNumberOfFloors = Long.parseLong(value);
                if (condition.contains(">")) return flat.getHouse().getNumberOfFloors() > houseNumberOfFloors;
                if (condition.contains("<")) return flat.getHouse().getNumberOfFloors() < houseNumberOfFloors;
                return flat.getHouse().getNumberOfFloors() == houseNumberOfFloors;
            case "house.numberoflifts":
                int numberOfLifts = Integer.parseInt(value);
                if (condition.contains(">")) return flat.getHouse().getNumberOfLifts() > numberOfLifts;
                if (condition.contains("<")) return flat.getHouse().getNumberOfLifts() < numberOfLifts;
                return flat.getHouse().getNumberOfLifts() == numberOfLifts;
            default:
                System.out.println("Filter error: " + value);
                throw new IllegalArgumentException("Invalid field: " + field);
        }
    }

    // Применение сортировки
    public static void applySorting(List<Flat> flats, String sort) throws IllegalArgumentException {
        Comparator<Flat> comparator = null;
        for (String field : sort.split(",")) {
            boolean descending = field.startsWith("-");
            String fieldName = descending ? field.substring(1) : field;

            Comparator<Flat> fieldComparator = getFieldComparator(fieldName);
            if (fieldComparator != null) {
                comparator = (comparator == null) ? fieldComparator : comparator.thenComparing(fieldComparator);
                if (descending) {
                    comparator = comparator.reversed();
                }
            }
        }
        if (comparator != null) {
            flats.sort(comparator);
        }
    }

    // Получение компаратора для конкретного поля
    public static Comparator<Flat> getFieldComparator(String fieldName) throws IllegalArgumentException {
        fieldName = fieldName.toLowerCase();
        switch (fieldName) {
            case "id":
                return Comparator.comparing(Flat::getId);
            case "name":
                return Comparator.comparing(Flat::getName);
            case "area":
                return Comparator.comparingDouble(Flat::getArea);
            case "numberofrooms":
                return Comparator.comparingLong(Flat::getNumberOfRooms);
            case "livingspace":
                return Comparator.comparingLong(Flat::getLivingSpace);
            case "transport":
                return Comparator.comparing(Flat::getTransport);
            case "isnew":
            case "new":
                return Comparator.comparing(Flat::getIsNew);
            default:
                throw new IllegalArgumentException("Invalid field: " + fieldName);
        }
    }
}
