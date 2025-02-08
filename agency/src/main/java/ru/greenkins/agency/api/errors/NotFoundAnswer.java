package ru.greenkins.agency.api.errors;

import jakarta.xml.bind.annotation.XmlElement;
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
@XmlRootElement(name = "NotFoundAnswer")
@XmlType(propOrder = {"timestamp", "error", "path", "details"})
public class NotFoundAnswer { // Модель 422ой ошибки
    private String timestamp;
    private String error;
    private String path;
    private String details;

    public NotFoundAnswer(String error, String path) {
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        this.error = error;
        this.path = path;
    }

    @XmlElement(required = false)
    public String getDetails() {
        return details;
    }

}