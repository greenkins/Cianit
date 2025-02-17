package ru.greenkins.agencymejb.model;

import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor
public enum Transport implements Serializable {
    FEW,
    LITTLE,
    NORMAL;
}
