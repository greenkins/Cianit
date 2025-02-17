package ru.greenkins.agencymrest.api;


import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.*;
import lombok.Getter;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import ru.greenkins.agencymrest.api.converters.FlatConverter;
import ru.greenkins.agencymrest.api.errors.BadRequestExceptionMapper;
import ru.greenkins.agencymrest.api.errors.NotFoundExceptionMapper;
import ru.greenkins.agencymrest.api.errors.ServerErrorExceptionMapper;
import ru.greenkins.agencymrest.model.Flat;
import ru.greenkins.agencymrest.util.RealEstateAppraisal;

import java.net.URI;

@Getter
@Path("/get-cheapest")
public class CheapController {
    @Context
    private UriInfo uriInfo;
    Config config = ConfigProvider.getConfig();
    private final String HOUSING_SERVICE_URL = config.getValue("housing.service.url", String.class); // URL из конфига

    @GET
    @Path("/")
    @Produces("application/xml")
    public Response wrongWay(){
        return BadRequestExceptionMapper.getResponse(400, "Неверный запрос", uriInfo.getPath());
    }

    @GET
    @Path("/{id1}")
    @Produces("application/xml")
    public Response wrongWay2(){
        return BadRequestExceptionMapper.getResponse(400, "Неверный запрос", uriInfo.getPath());
    }

    @GET
    @Path("/{id1}/{id2}")
    @Produces("application/xml")
    public Response getCheapest(@PathParam("id1") String id1, @PathParam("id2") String id2) {
        int flatId1, flatId2;
        try {
            flatId1 = Integer.parseInt(id1); // Пытаемся преобразовать строку в число
            flatId2 = Integer.parseInt(id2);
        } catch (NumberFormatException e) {
            return BadRequestExceptionMapper.getResponse("Неверный запрос", uriInfo.getPath());
        }
        if (flatId1 < 0 || flatId2 < 0) {return BadRequestExceptionMapper.getResponse("Неверный запрос", uriInfo.getPath());}
        System.out.println("Запрос на сравнение квартир: " + uriInfo.getPath());
        try (Client client = ClientBuilder.newClient()) {
            // Формируем базовый URL для первого сервиса
            URI baseUri = URI.create(HOUSING_SERVICE_URL);
            WebTarget targetFlat1 = client.target(UriBuilder.fromUri(baseUri).path("/flats/" + flatId1));
            WebTarget targetFlat2 = client.target(UriBuilder.fromUri(baseUri).path("/flats/" + flatId2));
            //Requesting
            final Response responseFlat1 = targetFlat1.request().get();
            if (responseFlat1.getStatus() == 422)
                return NotFoundExceptionMapper.getResponse(404, "Не найдено", uriInfo.getPath(), "Квартиры с таким id не существует");
            final Response responseFlat2 = targetFlat2.request().get();
            if (responseFlat2.getStatus() == 422)
                return NotFoundExceptionMapper.getResponse(404, "Не найдено", uriInfo.getPath(), "Квартиры с таким id не существует");
            if (responseFlat1.getStatus() != 200 || responseFlat2.getStatus() != 200)
                return ServerErrorExceptionMapper.getResponse(503, "Внутренняя ошибка сервера", uriInfo.getPath());
            // Appraising
            String flat1StringEntity = responseFlat1.readEntity(String.class);
            Flat flat1 = (responseFlat1.getStatus() == 200) ?
                    FlatConverter.parseFlatFromXml(flat1StringEntity) : null;
            int priceFlat1 = RealEstateAppraisal.appraise(flat1);
            String flat2StringEntity = responseFlat2.readEntity(String.class);
            Flat flat2 = (responseFlat2.getStatus() == 200) ?
                    FlatConverter.parseFlatFromXml(flat2StringEntity) : null;
            int priceFlat2 = RealEstateAppraisal.appraise(flat2);
            // Agency Response
            return Response.status(Response.Status.OK).type(MediaType.APPLICATION_XML_TYPE).entity(
                    (priceFlat1 < priceFlat2) ? flat1StringEntity : flat2StringEntity
            ).build();

        } catch (ProcessingException e) {
            return ServerErrorExceptionMapper.getResponse(503, "Внутренняя ошибка сервера", uriInfo.getPath());
        } catch (Exception e) {
            System.err.println("Запрос " + uriInfo.getPath() + " Завершился с ошибкой: " + e.getMessage());
            return ServerErrorExceptionMapper.getResponse(500, "Внутренняя ошибка сервера", uriInfo.getPath());
        }
    }
}
