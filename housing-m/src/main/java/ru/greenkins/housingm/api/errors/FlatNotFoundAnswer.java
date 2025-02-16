package ru.greenkins.housingm.api.errors;

import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Setter
@Getter
@NoArgsConstructor
@XmlRootElement(name = "NotFoundAnswer")
@XmlType(propOrder = {"timestamp", "error", "path", "details"})
@XmlAccessorType(XmlAccessType.FIELD)    // Автоматическая сериализация полей
public class FlatNotFoundAnswer { // Модель 422ой ошибки
    private String timestamp;
    private String error;
    private String path;
    private String details;

    public FlatNotFoundAnswer(String error, String path) {
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        this.error = error;
        this.path = path;
    }

    @XmlElement(required = false)
    public String getDetails() {
        return details;
    }

}