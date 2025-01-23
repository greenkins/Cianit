package ru.greenkins.housing.dto;


public class HouseDTO {
    private String name; //Поле может быть null
    private long year; //Значение поля должно быть больше 0
    private Long numberOfFloors; //Поле может быть null, Значение поля должно быть больше 0
    private int numberOfLifts; //Значение поля должно быть больше 0
}
