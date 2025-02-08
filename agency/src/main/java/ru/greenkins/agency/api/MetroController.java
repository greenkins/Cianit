package ru.greenkins.agency.api;

import jakarta.ws.rs.*;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.*;
import lombok.Getter;
import org.eclipse.microprofile.config.Config;
import org.eclipse.microprofile.config.ConfigProvider;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import ru.greenkins.agency.api.errors.BadRequestExceptionMapper;
import ru.greenkins.agency.api.errors.ServerErrorExceptionMapper;

import java.net.URI;

@Getter
@Path("/get-ordered-by-time-to-metro")
public class MetroController {
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
    @Path("/{by-transport}")
    @Produces("application/xml")
    public Response wrongWay2(){
        return BadRequestExceptionMapper.getResponse(400, "Неверный запрос", uriInfo.getPath());
    }

    @GET
    @Path("/{by-transport}/{desc}")
    @Produces("application/xml")
    public Response getMetro(@PathParam("by-transport") boolean byTransport, @PathParam("desc") boolean desc) {
        System.out.println("Запрос на получение отсортированного списка квартир: " + uriInfo.getBaseUri());
        try {
            Client client = ClientBuilder.newClient(); // Создаём JAX-RS клиент
            // Формируем базовый URL для первого сервиса
            URI baseUri = URI.create(HOUSING_SERVICE_URL);
            WebTarget target;
            if (byTransport)
                target = client.target(UriBuilder.fromUri(baseUri).path("/flats")).queryParam("sort", (desc ? "-transport" : "transport")).queryParam("size", 1000);
            else
                target = client.target(UriBuilder.fromUri(baseUri).path("/flats")).queryParam("sort", (desc ? "-area" : "area")).queryParam("size", 1000);
            // Выполняем GET-запрос
            Response housingResponse = target.request(MediaType.APPLICATION_XML).get();
            // Проверяем статус ответа
            String responseEntity = housingResponse.readEntity(String.class);
            if (responseEntity == null || responseEntity.isEmpty())
                return ServerErrorExceptionMapper.getResponse(503, "Внутренняя ошибка сервера", uriInfo.getPath());

            if (housingResponse.getStatus() == 200) {
                client.close();
                return Response.ok(responseEntity).build();
            } else if (housingResponse.getStatus() == 404) {
                return ServerErrorExceptionMapper.getResponse(503, "Внутренняя ошибка сервера", uriInfo.getPath());
            } else {
                return Response.status(housingResponse.getStatus()).entity("").build();
            }
        } catch (ProcessingException e) {
            return ServerErrorExceptionMapper.getResponse(503, "Внутренняя ошибка сервера", uriInfo.getPath());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ServerErrorExceptionMapper.getResponse("Внутренняя ошибка сервера", uriInfo.getPath());
        }
    }
}
