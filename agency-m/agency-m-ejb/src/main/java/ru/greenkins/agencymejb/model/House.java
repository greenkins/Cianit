package ru.greenkins.agencymejb.model;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;
import jakarta.validation.constraints.Positive;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Setter
@Getter
@NoArgsConstructor
@XmlRootElement(name = "House")
@XmlAccessorType(XmlAccessType.FIELD)
public class House implements Serializable {
    @XmlElement
    @Null
    private String name; //Поле может быть null
    @XmlElement
    @NotNull @Positive
    private long year; //Значение поля должно быть больше 0
    @XmlElement
    @Null @Positive
    private Long numberOfFloors; //Поле может быть null, Значение поля должно быть больше 0
    @XmlElement
    @Null
    private int numberOfLifts; //Значение поля должно быть больше 0

    public House(@Null String name, long year, @Null Long numberOfFloors, int numberOfLifts) {
        this.name = name;
        this.year = year;
        this.numberOfFloors = numberOfFloors;
        this.numberOfLifts = numberOfLifts;
    }
}
