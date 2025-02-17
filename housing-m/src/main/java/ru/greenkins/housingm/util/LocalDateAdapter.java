package ru.greenkins.housingm.util;

import jakarta.xml.bind.annotation.adapters.XmlAdapter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    @Override
    public LocalDate unmarshal(String v) {
        return v != null ? LocalDate.parse(v, FORMATTER) : null;
    }

    @Override
    public String marshal(LocalDate v) {
        return v != null ? v.format(FORMATTER) : null;
    }
}
