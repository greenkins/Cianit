package ru.greenkins.agency.util;

import ru.greenkins.agency.model.Flat;

import java.time.LocalDate;

public class RealEstateAppraisal {
    public static int appraise(Flat flat) {
        if (flat == null) {
            throw new IllegalArgumentException("Квартира не может быть null");
        }
        int basePricePerSquareMeter = 180_000; // Базовая цена за м²
        int price = (int) (flat.getArea() * basePricePerSquareMeter);
        // Учитываем количество комнат
        price += (int) ((flat.getNumberOfRooms() % 5 + 1) * 500_000);
        // Учитываем жилую площадь
        price += flat.getLivingSpace() * 20_000;

        // Учитываем возраст дома
        int houseAge = (int) (LocalDate.now().getYear() - flat.getHouse().getYear());
        if (houseAge < 10) {
            price *= 1.2; // Новые дома дороже
        } else if (houseAge > 50) {
            price *= 0.8; // Старые дома дешевле
        }

        return Math.max(price, 0); // Гарантируем, что цена не будет отрицательной
    }
}
