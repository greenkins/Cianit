package ru.greenkins.agency.api.converters;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import ru.greenkins.agency.model.Flat;

import java.io.StringReader;

public class FlatConverter {
    public static Flat parseFlatFromXml(String xml) throws JAXBException {
        JAXBContext jaxbContext = JAXBContext.newInstance(Flat.class);
        Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
        return (Flat) unmarshaller.unmarshal(new StringReader(xml));
    }
}
