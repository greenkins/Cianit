package ru.greenkins.housing.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import lombok.Getter;
import ru.greenkins.housing.api.converters.FlatsResponseWrapperToXmlConverter;
import ru.greenkins.housing.model.Flat;
import ru.greenkins.housing.model.FlatsResponseWrapper;
import ru.greenkins.housing.service.FlatService;

import java.util.List;
import java.util.Optional;


@Getter
@Path("/flats")
public class FlatController {
    @Inject
    private FlatService flatService;
    @Inject
    private FlatsResponseWrapperToXmlConverter flatsResponseWrapperToXmlConverter;

    @GET
    @Produces("application/xml")
    public String getAllFlats() {
        List<Flat> flats = flatService.getAllFlats();
        FlatsResponseWrapper wrapper = new FlatsResponseWrapper(1, flats.size(), 1, flats);
        Optional<String> response = flatsResponseWrapperToXmlConverter.convertToXml(wrapper);
        return response.orElse("<Non Produced Error>");
    }

//    @GET
//    @Produces("application/xml")
//    public String getAllFlats() {
//        return "Connected";
////        try {
////            return flats.getAllFlats();
////        } catch (JAXBException e) {
////            throw new RuntimeException(e);
////        }
//    }

//    @POST
//    public String addFlat() {
//        Flat flat = new Flat(currentId);
//        flatCollection.add(flat);
//        currentId++;
//        return flat.toString();
//    }

//    @POST
//    @Produces("application/xml")
//    public String addFlat() {
//
//    }
}