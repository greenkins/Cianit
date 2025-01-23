package ru.greenkins.housing.repository;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import ru.greenkins.housing.model.Flat;
import ru.greenkins.housing.model.Coordinates;
import ru.greenkins.housing.model.House;
import ru.greenkins.housing.model.Transport;

import java.time.LocalDate;

@Entity
@Getter @Setter
@Table(name = "flats")
public class FlatEntity {
    @Id
    private int id;
    @Column(name = "flat_name") // Указываем имя столбца как flat_name
    private String name;
    private double area;
    private Long numberOfRooms;
    private int livingSpace;
    private Boolean isNew;
    private String transport; // Хранение ENUM как String
    private LocalDate creationDate;

    @Embedded
    private Coordinates coordinates;

    @Embedded
    private House house;



    // Преобразование в бизнес-модель
    public Flat toModel() {
        return new Flat(id, name, coordinates, creationDate, area, numberOfRooms, livingSpace, isNew, Transport.valueOf(transport), house);
    }

    // Преобразование из бизнес-модели
    public static FlatEntity fromModel(Flat flat) {
        FlatEntity entity = new FlatEntity();
        entity.id = flat.getId();
        entity.name = flat.getName();
        entity.area = flat.getArea();
        entity.numberOfRooms = flat.getNumberOfRooms();
        entity.livingSpace = flat.getLivingSpace();
        entity.isNew = flat.getIsNew();
        entity.transport = flat.getTransport() != null ? flat.getTransport().name() : null;
        entity.creationDate = flat.getCreationDate();
        entity.coordinates = flat.getCoordinates();
        entity.house = flat.getHouse();
        return entity;
    }
}

