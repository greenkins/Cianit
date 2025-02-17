package ru.greenkins.housingm.api.requests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.greenkins.housingm.util.LocalDateAdapter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@XmlRootElement(name = "FlatCreateResponse")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlatCreateResponse {
    @XmlElement @Positive
    private int flatId;
    @XmlElement @NotNull @XmlJavaTypeAdapter(LocalDateAdapter.class)
    private LocalDate creationDate;
}
