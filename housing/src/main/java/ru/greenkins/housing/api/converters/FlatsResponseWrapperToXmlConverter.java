package ru.greenkins.housing.api.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Singleton;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import lombok.Builder;
import ru.greenkins.housing.api.requests.FlatsResponseWrapper;


import java.io.StringWriter;
import java.util.Optional;

@ApplicationScoped
public class FlatsResponseWrapperToXmlConverter {
    private final Marshaller marshaller;

    public FlatsResponseWrapperToXmlConverter() {
        try {
            JAXBContext context = JAXBContext.newInstance(FlatsResponseWrapper.class);
            this.marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to initialize ResponseToXmlConverter", e);
        }
    }

    /**
     * Преобразует объект ResponseWrapper в XML-строку.
     *
     * @param flatsResponseWrapper объект ResponseWrapper для преобразования
     * @return XML-строка или пустой Optional в случае ошибки
     */
    public Optional<String> convertToXml(FlatsResponseWrapper flatsResponseWrapper) {
        try {
            StringWriter writer = new StringWriter();
            marshaller.marshal(flatsResponseWrapper, writer);
            return Optional.of(writer.toString());
        } catch (JAXBException e) {
            System.err.println("Error converting Response to XML: " + e.getMessage());
            return Optional.empty();
        }
    }

//    // Get all flats
//    public String getAllFlatsXML() {
//        StringBuilder xml = new StringBuilder();
//        this.getAllFlats().forEach(flat -> {
//            try {
//                xml.append(this.convertToXml(flat));
//            } catch (JAXBException e) {
//                throw new RuntimeException(e);
//            }
//        });
//        return xml.toString();
//    }

}
