package ru.greenkins.housing.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.greenkins.housing.model.Transport;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FlatDTO {
    private int id;
    private String name;
    private CoordinatesDTO coordinates;
    private java.time.LocalDate creationDate; // ISO-8601 формат
    private double area;
    private Long numberOfRooms;
    private int livingSpace;
    private Boolean isNew;
    private Transport transport; // Enum как строка
    private HouseDTO house;
}
