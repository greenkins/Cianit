package ru.greenkins.agencymejb.model;

import jakarta.validation.constraints.Max;
import jakarta.xml.bind.annotation.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Getter
@NoArgsConstructor
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "Coordinates")
public class Coordinates implements Serializable {
    @XmlElement @Max(256)
    private int x; //Максимальное значение поля: 256

    @XmlElement @Max(256)
    private int y; //Максимальное значение поля: 256

    public Coordinates(int x, int y) {
        this.setX(x);
        this.setY(y);
    }

    @XmlTransient
    public void setX(int x) {
        if (-256 <= x && x <= 256)
            this.x = x;
        else if (x < -256)
            this.x = -256;
        else this.x = 256;
    }

    @XmlTransient
    public void setY(int y) {
        if (-256 <= y && y <= 256)
            this.y = y;
        else if (y < -256)
            this.y = -256;
        else this.y = 256;
    }
}
