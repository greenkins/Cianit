package ru.greenkins.housing.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
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
    public Response getFlats(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("sort") @DefaultValue("") String sort,
            @QueryParam("filter") @DefaultValue("") String filter) {
        System.out.println("Запрос на получение данных о квартирах:" + filter + "\nВсего квартир: " + flatService.getFlatsCount());
        try {
            // Получаем отфильтрованные и отсортированные квартиры с пагинацией
            List<Flat> flats = flatService.getFlats(page, size, sort, filter);
            System.out.println("Результат запроса: " + flats.size() + " квартир (" + flats + ')');
            // Общая информация о коллекции
            int totalElements = flatService.getFlatsCount();
            int totalPages = (int) Math.ceil((double) totalElements / size);
            // Оборачиваем в ответный объект
            FlatsResponseWrapper wrapper = new FlatsResponseWrapper(totalPages, size, page, flats);
            // Конвертируем в XML
            Optional<String> responseXml = flatsResponseWrapperToXmlConverter.convertToXml(wrapper);

            if (!responseXml.isPresent()) {
                return Response.ok(responseXml.get()).build();
            } else {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                        .entity("<error>Unable to produce XML</error>")
                        .build();
            }
        } catch (IllegalArgumentException e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("<error>Invalid parameters: " + e.getMessage() + "</error>")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("<error>Internal server error</error>")
                    .build();
        }
    }




}
