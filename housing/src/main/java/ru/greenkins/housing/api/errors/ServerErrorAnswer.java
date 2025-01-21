package ru.greenkins.housing.api.errors;

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
@XmlRootElement(name = "ServerErrorAnswer")
@XmlType(propOrder = {"timestamp", "status", "error", "path"})
public class ServerErrorAnswer {
    private String timestamp;
    private int status;
    private String error;
    private String path;

    public ServerErrorAnswer(int status, String error, String path) {
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        this.status = status;
        this.error = error;
        this.path = path;
    }
}
