package ru.greenkins.housingm.model;

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
import ru.greenkins.housingm.util.LocalDateAdapter;

import java.io.Serializable;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@XmlRootElement(name = "Flat")
@XmlAccessorType(XmlAccessType.FIELD)
public class Flat implements Serializable {
    public Flat(int id, @NotNull String name, @NotNull Coordinates coordinates, @Null LocalDate creationDate, double area, @NotNull Long numberOfRooms,
                int livingSpace, @Null Boolean isNew, @Null Transport transport, @NotNull House house) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate == null ? LocalDate.now() : creationDate;
        this.area = area;
        this.numberOfRooms = numberOfRooms;
        this.isNew = isNew;
        this.transport = transport;
        this.house = house;
    }

    public Flat(int id) {
        this.id = id;
        this.name = "testFlat" + id;
        this.coordinates = new Coordinates(7, 7);
        this.creationDate = LocalDate.now();
        this.area = 0.0;
        this.numberOfRooms = 1L;
        this.isNew = true;
        this.transport = null;
        this.house = new House("TestEliteHouse", 2004, 5L, 2);
    }

    @XmlElement
    private int id;                 //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    @XmlElement @NotNull @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate creationDate;   //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    @XmlElement @NotNull
    private String name;            //Поле не может быть null, Строка не может быть пустой
    @XmlElement @NotNull
    private Coordinates coordinates;    //Поле не может быть null
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
