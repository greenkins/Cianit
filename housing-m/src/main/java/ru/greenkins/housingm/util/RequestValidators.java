package ru.greenkins.housingm.util;

import ru.greenkins.housingm.api.requests.FlatCreateRequest;

public class RequestValidators {
    public static void validateFlatCreateRequest(FlatCreateRequest request) throws IllegalArgumentException {
        if (request.getName() == null || request.getName().isEmpty()) {
            System.out.println("Имя квартиры не может быть пустым");
            throw new IllegalArgumentException("Имя квартиры не может быть пустым");
        }
        if (request.getCoordinates() == null) {
            System.out.println("Координаты обязательны");
            throw new IllegalArgumentException("Координаты обязательны");
        }
        if (request.getArea() <= 0) {
            System.out.println("Площадь должна быть больше 0");
            throw new IllegalArgumentException("Площадь должна быть больше 0");
        }
        if (request.getNumberOfRooms() <= 0) {
            System.out.println("Количество комнат должно быть больше 0");
            throw new IllegalArgumentException("Количество комнат должно быть больше 0");
        }
        if (request.getLivingSpace() <= 0 || request.getLivingSpace() > request.getArea()) {
            System.out.println("Жилая площадь должна быть больше 0 и меньше общей площади");
            throw new IllegalArgumentException("Жилая площадь должна быть больше 0 и меньше общей площади");
        }
        if (request.getTransport() == null) {
            System.out.println("Транспорт обязателен");
            throw new IllegalArgumentException("Транспорт обязателен");
        }
        if (request.getHouse() == null) {
            System.out.println("Данные о доме обязательны");
            throw new IllegalArgumentException("Данные о доме обязательны");
        }
    }

}
