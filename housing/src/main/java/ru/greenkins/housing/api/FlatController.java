package ru.greenkins.housing.api;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import lombok.Getter;
import ru.greenkins.housing.api.converters.XmlConverter;
import ru.greenkins.housing.api.errors.BadRequestExceptionMapper;
import ru.greenkins.housing.api.errors.NotFoundFlatExceptionMapper;
import ru.greenkins.housing.api.errors.NotFoundExceptionMapper;
import ru.greenkins.housing.api.errors.ServerErrorExceptionMapper;
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
    @Context
    private UriInfo uriInfo;

    @GET
    @Produces("application/xml")
    public Response getFlats(
            @QueryParam("page") @DefaultValue("1") int page,
            @QueryParam("size") @DefaultValue("20") int size,
            @QueryParam("sort") @DefaultValue("") String sort,
            @QueryParam("filter") @DefaultValue("") String filter) {
        System.out.println("Запрос на получение данных о квартирах:" + filter + "\nВсего квартир: " + flatService.getFlatsCount());
        // Предварительная обработка параметров
        if (page < 1 || size < 1 || size > 1000)
            throw new IllegalArgumentException("Параметры заданы некорректно");
        // Получаем отфильтрованные и отсортированные квартиры с пагинацией
        List<Flat> flats = flatService.getFlats(sort, filter);
        // Пагинация
        int total = flats.size();
        int fromIndex = (page - 1) * size;
        int toIndex = Math.min(fromIndex + size, total);
        if (fromIndex >= total) {
            flats.clear();
        }
        flats = flats.subList(fromIndex, toIndex);

        System.out.println("Результат запроса: " + flats.size() + " квартир (" + flats + ')');
        // Общая информация о коллекции
        int totalPages = (int) Math.ceil((double) total / size);
        totalPages = totalPages > 0 ? totalPages : 1;
        // Оборачиваем в ответный объект
        FlatsResponseWrapper wrapper = new FlatsResponseWrapper(totalPages, flats.size(), page, flats);
        // Конвертируем в XML
        Optional<String> responseXml = xmlConverter.convertResponseWrapperToXml(wrapper);

        if (responseXml.isPresent()) {
            return Response.ok(responseXml.get()).build();
        } else {
            throw new ServerErrorException("Внутренняя ошибка сервиса", Response.Status.INTERNAL_SERVER_ERROR);
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
            return Response.status(Response.Status.OK)
                    .entity(responseXml.get())
                    .build();
        } else {
            throw new ServerErrorException("Ошибка конвертации ответа", Response.Status.INTERNAL_SERVER_ERROR);
        }

    }

    @GET
    @Path("/{id}")
    @Produces("application/xml")
    public Response getFlatById(@PathParam("id") String id) {
        int flatId;
        try {
            flatId = Integer.parseInt(id); // Пытаемся преобразовать строку в число
        } catch (NumberFormatException e) {
           return BadRequestExceptionMapper.getResponse("Неверный запрос", uriInfo.getPath());
        }

        Optional<Flat> flatOptional = flatService.getFlatById(flatId);
        Flat flat = flatOptional.orElse(null);
        if (flat == null) {
            return NotFoundFlatExceptionMapper.getResponse(422, "Не найдено", uriInfo.getPath(), "Квартиры с таким id не существует");
        }
        try{
            String flatResponseXml = xmlConverter.convertFlatToXml(flat).orElse("");
            if (flatResponseXml.isEmpty())
                throw new ServerErrorException("Внутренняя ошибка сервера", Response.Status.INTERNAL_SERVER_ERROR);
            return Response.status(Response.Status.OK).entity(flatResponseXml).build();
        } catch (Exception e) {
            throw new ServerErrorException("Внутренняя ошибка сервера", Response.Status.INTERNAL_SERVER_ERROR);
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes("application/xml")
    @Produces("application/xml")
    public Response updateFlatById(@PathParam("id") String id, String content) {
        System.out.println("Получен запрос на обновление квартиры");
        int flatId;
        try {
            flatId = Integer.parseInt(id); // Пытаемся преобразовать строку в число
        } catch (NumberFormatException e) {
            return BadRequestExceptionMapper.getResponse(400, "Неверный запрос", uriInfo.getPath());
        }
        // Десериализация строки XML в объект FlatCreateRequest (аналогично методу POST)
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
                flatId,
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
        boolean updated = flatService.updateFlat(flatId, newFlat);
        Optional<Flat> addedFlat = flatService.getFlatById(flatId);
        if (updated && addedFlat.isPresent()) {
            return Response.status(Response.Status.OK)
                    .entity(addedFlat.get())
                    .type(MediaType.APPLICATION_XML)
                    .build();
        } else if (!updated && addedFlat.isEmpty()) {
            return NotFoundExceptionMapper.getResponse(404, "Не найдено", uriInfo.getPath(), "Квартиры с таким id не существует");
        } else if (!updated) { // and addedFlat.Present()
            return BadRequestExceptionMapper.getResponse(422, "Неверный запрос", "/flats" + id);
        }
        throw new ServerErrorException("Не удалось сохранить квартиру", Response.Status.INTERNAL_SERVER_ERROR);
    }

    @DELETE
    @Path("/{id}")
    @Produces("application/xml")
    public Response deleteFlatById(@PathParam("id") String id) {
        System.out.println("Получен запрос на удаление квартиры " + id);
        int flatId;
        try {
            flatId = Integer.parseInt(id); // Пытаемся преобразовать строку в число
        } catch (NumberFormatException e) {
            return BadRequestExceptionMapper.getResponse(400, "Неверный запрос", uriInfo.getPath());
        }
        if (flatService.getFlatById(flatId).isEmpty())
            return NotFoundExceptionMapper.getResponse(404, "Не найдено", uriInfo.getPath(), "Квартиры с таким id не существует");
        if (flatService.deleteFlat(flatId)){
            return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build(); // OK 204
        }
        return BadRequestExceptionMapper.getResponse(422, "Неверный запрос", uriInfo.getPath());
    }

    @DELETE
    @Path("/any-with-new/{new}")
    @Produces("application/xml")
    public Response deleteFlatByNewStatus(@PathParam("new") boolean isNew) {
        System.out.println("Получен запрос на удаление квартиры, где isNew: " + isNew);
        String filter = isNew ?  "isNew=true" : "isNew=false";
        List<Flat> flats = flatService.getFlats( null, filter);
        if (flats.isEmpty())
            return NotFoundExceptionMapper.getResponse(404, "Не найдено", uriInfo.getPath(), "Квартиры с таким id не существует");
        if (flatService.deleteFlat(flats.get(0).getId())){
            System.out.println("Удалена квартира с id: " + flats.get(0).getId());
            return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build(); // OK 204
        }
        return BadRequestExceptionMapper.getResponse(422, "Неверный запрос", uriInfo.getPath());
    }

    @DELETE
    @Path("/house/{houseName}")
    @Produces("application/xml")
    public Response deleteFlatsByHouseName(@PathParam("houseName") String houseName) { // TODO: complete request
        System.out.println("Получен запрос на удаление квартир, где House.name: " + houseName);
        String filter = "house.name=" + houseName;
        List<Flat> flats = flatService.getFlats(null, filter);
        if (flats.isEmpty())
            return NotFoundExceptionMapper.getResponse(404, "Не найдено", uriInfo.getPath(), "Квартиры с таким id не существует");
        boolean allDeleted = true;
        for (Flat flat : flats) {
            boolean deleted = flatService.deleteFlat(flat.getId());
            if (!deleted) {
                allDeleted = false;
                System.err.println("Ошибка удаления квартиры с ID: " + flat.getId());
            }
        }
        if (allDeleted){
            System.out.println(flats.size() + " квартир с houseName: " + houseName + " удалено");
            return Response.status(Response.Status.NO_CONTENT.getStatusCode()).build(); // OK 204
        }
        return ServerErrorExceptionMapper.getResponse(500, "Внутренняя ошибка сервера", uriInfo.getPath());
    }


}
