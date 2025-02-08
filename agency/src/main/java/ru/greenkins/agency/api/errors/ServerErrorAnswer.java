package ru.greenkins.agency.api.errors;

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
@XmlType(propOrder = {"timestamp", "error", "path"})
public class ServerErrorAnswer {
    private String timestamp;
    private String error;
    private String path;

    public ServerErrorAnswer(String error, String path) {
        this.timestamp = ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME);
        this.error = error;
        this.path = path;
    }
}
