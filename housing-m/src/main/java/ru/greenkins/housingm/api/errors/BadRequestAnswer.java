package ru.greenkins.housingm.api.errors;


import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
@XmlRootElement(name = "BadRequestAnswer")
@XmlType(propOrder = {"timestamp", "error", "path"})
@XmlAccessorType(XmlAccessType.FIELD)    // Автоматическая сериализация полей
public class BadRequestAnswer { // Модель 400ой ошибки
    private String timestamp;
    private String error;
    private String path;

    public BadRequestAnswer(String error, String path) {
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        this.error = error;
        this.path = path;
    }
}
