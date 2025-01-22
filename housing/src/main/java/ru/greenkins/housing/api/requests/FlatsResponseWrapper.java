package ru.greenkins.housing.api.requests;


import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.greenkins.housing.model.Flat;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@XmlRootElement(name = "Response")
@XmlAccessorType(XmlAccessType.FIELD)
public class FlatsResponseWrapper {
    @XmlElement
    private int totalPages;

    @XmlElement
    private int pageSize;

    @XmlElement
    private int currPage;

    @XmlElement(name = "Flat")
    private List<Flat> flats;
}
