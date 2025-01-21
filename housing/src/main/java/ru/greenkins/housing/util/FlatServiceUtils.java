package ru.greenkins.housing.util;

import ru.greenkins.housing.model.Flat;

import java.util.Comparator;
import java.util.List;

public class FlatServiceUtils {
    // Логика для конкретного фильтра
    public static boolean matchesFilter(Flat flat, String field, String value, String condition) throws IllegalArgumentException{
        switch (field) {
            case "name":
                return flat.getName().contains(value);
            case "area":
                double area = Double.parseDouble(value);
                return condition.contains(">") ? flat.getArea() > area : flat.getArea() < area;
            case "numberOfRooms":
                long rooms = Long.parseLong(value);
                return flat.getNumberOfRooms() == rooms;
            default:
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
        switch (fieldName) {
            case "name":
                return Comparator.comparing(Flat::getName);
            case "area":
                return Comparator.comparingDouble(Flat::getArea);
            case "numberOfRooms":
                return Comparator.comparingLong(Flat::getNumberOfRooms);
            default:
                throw new IllegalArgumentException("Invalid field: " + fieldName);
        }
    }
}
