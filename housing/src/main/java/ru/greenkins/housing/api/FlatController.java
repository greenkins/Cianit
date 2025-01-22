package ru.greenkins.housing.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Response;
import lombok.Getter;
import ru.greenkins.housing.api.converters.XmlConverter;
import ru.greenkins.housing.api.requests.FlatCreateRequest;
import ru.greenkins.housing.api.requests.FlatCreateResponse;
import ru.greenkins.housing.model.Flat;
import ru.greenkins.housing.api.requests.FlatsResponseWrapper;
import ru.greenkins.housing.service.FlatService;
import ru.greenkins.housing.util.RequestValidators;

import java.util.List;
import java.util.Optional;


@Getter
@Path("/flats")
public class FlatController {
    @Inject
    private FlatService flatService;
    @Inject
    private XmlConverter xmlConverter;

    @GET
    @Produces("application/xml")
    public Response getFlats(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("sort") @DefaultValue("") String sort,
            @QueryParam("filter") @DefaultValue("") String filter) {
        System.out.println("Запрос на получение данных о квартирах:" + filter + "\nВсего квартир: " + flatService.getFlatsCount());
        // Предварительная обработка параметров
        if (page < 1 || size < 1 || size > 100)
            throw new IllegalArgumentException("Параметры заданы некорректно");
        // Получаем отфильтрованные и отсортированные квартиры с пагинацией
        List<Flat> flats = flatService.getFlats(page, size, sort, filter);
        System.out.println("Результат запроса: " + flats.size() + " квартир (" + flats + ')');
        // Общая информация о коллекции
        int totalElements = flatService.getFlatsCount();
        int totalPages = (int) Math.ceil((double) totalElements / size);
        // Оборачиваем в ответный объект
        FlatsResponseWrapper wrapper = new FlatsResponseWrapper(totalPages, size, page, flats);
        // Конвертируем в XML
        Optional<String> responseXml = xmlConverter.convertResponseWrapperToXml(wrapper);

        if (responseXml.isPresent()) {
            return Response.ok(responseXml.get()).build();
        } else {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("<error>Unable to produce XML</error>")
                    .build();
        }
        // IllegalStatement and Exception exceptions are caught by Mappers in /api/errors
    }

    @POST
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response postFlat(String content) {
        System.out.println("Получен запрос на создание квартиры");

        // Десериализация строки XML в объект FlatCreateRequest
        // Используем XmlConverter для преобразования XML в объект FlatCreateRequest
        Optional<FlatCreateRequest> requestOptional = xmlConverter.convertXmlToFlatCreateRequest(content);

        // Проверка валидности входных данных
        if (requestOptional.isEmpty())
            throw new IllegalArgumentException();
        FlatCreateRequest request = requestOptional.get();

        // Проверка валидности входных данных
        RequestValidators.validateFlatCreateRequest(request);

        // Создание и сохранение объекта Flat через сервис
        Flat newFlat = new Flat(
                0,
                request.getName(),
                request.getCoordinates(),
                null, // Дата создания устанавливается в сервисе
                request.getArea(),
                request.getNumberOfRooms(),
                request.getLivingSpace(),
                request.getIsNew(),
                request.getTransport(),
                request.getHouse()
        );

        // Добавление квартиры через сервис
        boolean added = flatService.addFlat(newFlat);
        if (!added) {
            throw new ServerErrorException("Не удалось сохранить квартиру", Response.Status.INTERNAL_SERVER_ERROR);
        }

        // Формируем успешный ответ
        FlatCreateResponse response = new FlatCreateResponse(newFlat.getId(), newFlat.getCreationDate());
        Optional<String> responseXml = xmlConverter.convertFlatCreateResponseToXml(response);

        if (responseXml.isPresent()) {
            return Response.status(Response.Status.CREATED)
                    .entity(responseXml.get())
                    .build();
        } else {
            throw new ServerErrorException("Ошибка конвертации ответа", Response.Status.INTERNAL_SERVER_ERROR);
        }

    }

    @GET
    @Path("/{id}")
    @Produces("application/xml")
    public Response getFlatById(@PathParam("id") String id) { // TODO: complete request
        throw new ServerErrorException("Not implemented", Response.Status.INTERNAL_SERVER_ERROR);
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response updateFlatById(@PathParam("id") String id, String flatUpdateRequest) { // TODO: complete request
        throw new ServerErrorException("Not implemented", Response.Status.INTERNAL_SERVER_ERROR);
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/xml")
    public Response deleteFlatById(@PathParam("id") String id) { // TODO: complete request
        throw new ServerErrorException("Not implemented", Response.Status.INTERNAL_SERVER_ERROR);
    }

    @DELETE
    @Path("/any-with-new/{new}")
    @Produces("application/xml")
    public Response deleteFlatByNewStatus(@PathParam("new") boolean isNew) { // TODO: complete request
        throw new ServerErrorException("Not implemented", Response.Status.INTERNAL_SERVER_ERROR);
    }

    @DELETE
    @Path("/house/{houseName}")
    @Produces("application/xml")
    public Response deleteFlatsByHouseName(@PathParam("houseName") String houseName) { // TODO: complete request
        throw new ServerErrorException("Not implemented", Response.Status.INTERNAL_SERVER_ERROR);
    }


}
