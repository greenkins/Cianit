package ru.greenkins.housingm.api.converters;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import ru.greenkins.housingm.api.requests.FlatCreateRequest;
import ru.greenkins.housingm.api.requests.FlatCreateResponse;
import ru.greenkins.housingm.api.requests.FlatsResponseWrapper;
import ru.greenkins.housingm.model.Flat;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.Optional;

@ApplicationScoped
public class XmlConverter {
    private final Marshaller flatsToXmlMarshaller;
    private final Marshaller flatCreateToXmlMarshaller;
    private final Marshaller flatToXmlMarshaller;
    private final Unmarshaller xmlToFlatCreateUnmarshaller;


    public XmlConverter() {
        try { // FlatsToXmlMarshaller
            JAXBContext context = JAXBContext.newInstance(FlatsResponseWrapper.class);
            this.flatsToXmlMarshaller = context.createMarshaller();
            flatsToXmlMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to initialize FlatToXmlMarshaller", e);
        }

        try { // XmlToFlatCreateUnmarshaller
            JAXBContext context = JAXBContext.newInstance(FlatCreateRequest.class);
            this.xmlToFlatCreateUnmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to initialize XmlToFlatCreateUnmarshaller", e);
        }

        try {
            JAXBContext context = JAXBContext.newInstance(Flat.class);
            this.flatToXmlMarshaller = context.createMarshaller();
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to initialize XmlToFlatMarshaller", e);
        }

        try { // FlatCreateResponse to Xml
            JAXBContext context = JAXBContext.newInstance(FlatCreateResponse.class);
            this.flatCreateToXmlMarshaller = context.createMarshaller();
            flatCreateToXmlMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (JAXBException e) {
            throw new RuntimeException("Failed to initialize FlatCreateToXmlMarshaller", e);
        }
    }

    /**
     * Преобразует объект ResponseWrapper в XML-строку.
     *
     * @param flatsResponseWrapper объект ResponseWrapper для преобразования
     * @return XML-строка или пустой Optional в случае ошибки
     */
    public Optional<String> convertResponseWrapperToXml(FlatsResponseWrapper flatsResponseWrapper) {
        try {
            StringWriter writer = new StringWriter();
            flatsToXmlMarshaller.marshal(flatsResponseWrapper, writer);
            return Optional.of(writer.toString());
        } catch (JAXBException e) {
            System.err.println("Error converting Response to XML: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Преобразует объект FlatCreateResponse в XML-строку.
     *
     * @param flatCreateResponse объект ResponseWrapper для преобразования
     * @return XML-строка или пустой Optional в случае ошибки
     */
    public Optional<String> convertFlatCreateResponseToXml(FlatCreateResponse flatCreateResponse) {
        try {
            StringWriter writer = new StringWriter();
            flatCreateToXmlMarshaller.marshal(flatCreateResponse, writer);
            return Optional.of(writer.toString());
        } catch (JAXBException e) {
            System.err.println("Error converting Response to XML: " + e.getMessage());
            return Optional.empty();
        }
    }

    public Optional<String> convertFlatToXml(Flat flat) {
        try {
            StringWriter writer = new StringWriter();
            flatsToXmlMarshaller.marshal(flat, writer);
            return Optional.of(writer.toString());
        } catch (JAXBException e) {
            System.err.println("Error converting Response to XML: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * Преобразует строку XML в объект FlatCreateRequest.
     *
     * @param xml строка XML для преобразования
     * @return объект FlatCreateRequest или пустой Optional в случае ошибки
     */
    public Optional<FlatCreateRequest> convertXmlToFlatCreateRequest(String xml) {
        try {
            StringReader reader = new StringReader(xml);
            FlatCreateRequest request = (FlatCreateRequest) this.xmlToFlatCreateUnmarshaller.unmarshal(reader);
            return Optional.of(request);
        } catch (JAXBException e) {
            System.err.println("Error converting XML to FlatCreateRequest: " + e.getMessage());
            return Optional.empty();
        }
    }
}
