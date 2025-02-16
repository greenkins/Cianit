package ru.greenkins.housingm.api.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.greenkins.housingm.model.Coordinates;
import ru.greenkins.housingm.model.House;
import ru.greenkins.housingm.model.Transport;
import ru.greenkins.housingm.util.LocalDateAdapter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@XmlRootElement(name = "FlatCreateRequest")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlatCreateRequest {
    public FlatCreateRequest(@NotNull String name, @NotNull Coordinates coordinates, double area, @NotNull Long numberOfRooms,
                             int livingSpace, @Null Boolean isNew, @Null Transport transport, @NotNull House house) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate == null ? LocalDate.now() : creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.isNew = isNew;
        this.transport = transport;
        this.house = house;
    }

    @XmlElement @NotNull
    private String name;            //Поле не может быть null, Строка не может быть пустой
    @XmlElement @NotNull
    private Coordinates coordinates;    //Поле не может быть null
    @XmlElement @NotNull @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate creationDate;   //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @XmlElement @NotNull @Positive
    private double area;            //Значение поля должно быть больше 0
    @XmlElement @NotNull @Positive
    private Long numberOfRooms;     //Поле не может быть null, Значение поля должно быть больше 0
    @XmlElement @Positive
    private int livingSpace;        //Значение поля должно быть больше 0
    @XmlElement(name = "new") @Null
    private Boolean isNew;          //Поле может быть null
    @XmlElement @Null
    private Transport transport;    //Поле может быть null
    @XmlElement @NotNull
    private House house;            //Поле не может быть null
}

