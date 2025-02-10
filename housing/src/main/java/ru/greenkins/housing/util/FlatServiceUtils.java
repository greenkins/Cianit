package ru.greenkins.housing.util;

import ru.greenkins.housing.model.Flat;
import ru.greenkins.housing.model.Transport;

import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class FlatServiceUtils {
    // Логика для конкретного фильтра
    public static boolean matchesFilter(Flat flat, String field, String operatorValue) throws IllegalArgumentException {
        field = field.toLowerCase();

        // Разделяем оператор и значение
        if (operatorValue.length() <= 1) return true;
        String operator = operatorValue.substring(0, 1);
        String value = operatorValue.substring(1).trim();

        switch (field) {
            case "id":
                return compareInt(flat.getId(), value, operator);
            case "name":
                return compareString(flat.getName(), value, operator);
            case "coordinates.x":
                return compareInt(flat.getCoordinates().getX(), value, operator);
            case "coordinates.y":
                return compareInt(flat.getCoordinates().getY(), value, operator);
            case "area":
                return compareDouble(flat.getArea(), value, operator);
            case "numberofrooms":
                return compareLong(flat.getNumberOfRooms(), value, operator);
            case "livingspace":
                return compareInt(flat.getLivingSpace(), value, operator);
            case "isnew":
            case "new":
                return flat.getIsNew() == Boolean.parseBoolean(value);
            case "transport":
                return compareEnum(flat.getTransport(), value, operator);
            case "house.name":
                return compareString(flat.getHouse().getName(), value, operator);
            case "house.year":
                return compareLong(flat.getHouse().getYear(), value, operator);
            case "house.numberoffloors":
                return compareLong(flat.getHouse().getNumberOfFloors(), value, operator);
            case "house.numberoflifts":
                return compareInt(flat.getHouse().getNumberOfLifts(), value, operator);
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

    private static boolean compareInt(int fieldValue, String value, String operator) {
        switch (operator) {
            case ":":
                String[] range = value.split("-");
                int min = Integer.parseInt(range[0]);
                int max = (range.length == 2) ? Integer.parseInt(range[1]) : Integer.MAX_VALUE;
                return fieldValue >= min && fieldValue <= max;
            case ",":
                String[] values = value.split(",");
                for (String v : values) {
                    if (fieldValue == Integer.parseInt(v.trim())) {
                        return true;
                    }
                }
                return false;
        }
        try {
            int intValue = Integer.parseInt(value);
            switch (operator) {
                case "=":
                    return fieldValue == intValue;
                case ">":
                    return fieldValue > intValue;
                case "<":
                    return fieldValue < intValue;
                case "≥":
                    return fieldValue >= intValue;
                case "≤":
                    return fieldValue <= intValue;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private static boolean compareDouble(double fieldValue, String value, String operator) {
        switch (operator) {
            case ":":
                String[] range = value.split("-");
                double min = Double.parseDouble(range[0]);
                double max = (range.length == 2) ? Double.parseDouble(range[1]) : Double.MAX_VALUE;
                return fieldValue >= min && fieldValue <= max;
            case ",":
                String[] values = value.split(",");
                for (String v : values) {
                    if (fieldValue == Double.parseDouble(v.trim())) {
                        return true;
                    }
                }
                return false;
        }
        try {
            double doubleValue = Double.parseDouble(value);
            switch (operator) {
                case "=":
                    return fieldValue == doubleValue;
                case ">":
                    return fieldValue > doubleValue;
                case "<":
                    return fieldValue < doubleValue;
                case "≥":
                    return fieldValue >= doubleValue;
                case "≤":
                    return fieldValue <= doubleValue;
                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private static boolean compareLong(long fieldValue, String value, String operator) {
        switch (operator) {
            case ":":
                String[] range = value.split("-");
                long min = Long.parseLong(range[0]);
                long max = (range.length == 2) ? Long.parseLong(range[1]) : Long.MAX_VALUE;
                return fieldValue >= min && fieldValue <= max;
            case ",":
                String[] values = value.split(",");
                for (String v : values) {
                    if (fieldValue == Long.parseLong(v.trim())) {
                        return true;
                    }
                }
                return false;
        }
        try {
            long longValue = Long.parseLong(value);
            switch (operator) {
                case "=":
                    return fieldValue == longValue;
                case ">":
                    return fieldValue > longValue;
                case "<":
                    return fieldValue < longValue;
                case "≥":
                    return fieldValue >= longValue;
                case "≤":
                    return fieldValue <= longValue;

                default:
                    throw new IllegalArgumentException("Invalid operator: " + operator);
            }
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private static boolean compareString(String fieldValue, String value, String operator) {
        switch (operator) {
            case "=":
                return fieldValue.equals(value);
            case "~":
                return fieldValue.contains(value);
            case ",":
                String[] values = value.split(",");
                for (String v : values) {
                    if (fieldValue.equals(v.trim())) {
                        return true;
                    }
                }
                return false;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }

    private static boolean compareEnum(Enum<?> fieldValue, String value, String operator) {
        switch (operator) {
            case "=":
                return fieldValue.name().equalsIgnoreCase(value);
            case ",":
                String[] values = value.split(",");
                for (String v : values) {
                    if (fieldValue.name().equalsIgnoreCase(v.trim())) {
                        return true;
                    }
                }
                return false;
            default:
                throw new IllegalArgumentException("Invalid operator: " + operator);
        }
    }
}
