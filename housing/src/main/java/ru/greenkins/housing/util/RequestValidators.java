package ru.greenkins.housing.util;

import ru.greenkins.housing.api.requests.FlatCreateRequest;

public class RequestValidators {
    public static void validateFlatCreateRequest(FlatCreateRequest request) throws IllegalArgumentException {
        if (request.getName() == null || request.getName().isEmpty()) {
            throw new IllegalArgumentException("Имя квартиры не может быть пустым");
        }
        if (request.getCoordinates() == null) {
            throw new IllegalArgumentException("Координаты обязательны");
        }
        if (request.getArea() <= 0) {
            throw new IllegalArgumentException("Площадь должна быть больше 0");
        }
        if (request.getNumberOfRooms() <= 0) {
            throw new IllegalArgumentException("Количество комнат должно быть больше 0");
        }
        if (request.getLivingSpace() <= 0 || request.getLivingSpace() > request.getArea()) {
            throw new IllegalArgumentException("Жилая площадь должна быть больше 0 и меньше общей площади");
        }
        if (request.getTransport() == null) {
            throw new IllegalArgumentException("Транспорт обязателен");
        }
        if (request.getHouse() == null) {
            throw new IllegalArgumentException("Данные о доме обязательны");
        }
    }

}
