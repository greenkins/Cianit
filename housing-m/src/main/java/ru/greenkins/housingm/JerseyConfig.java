package ru.greenkins.housingm;

import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;
import ru.greenkins.housingm.api.EnumsController;
import ru.greenkins.housingm.api.FlatController;
import ru.greenkins.housingm.api.converters.XmlConverter;
import ru.greenkins.housingm.api.cors.CorsFilter;
import ru.greenkins.housingm.api.errors.*;
import ru.greenkins.housingm.service.FlatService;
import ru.greenkins.housingm.service.FlatServiceImpl;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {
        register(new AbstractBinder() {
            @Override
            protected void configure() {
                bind(FlatServiceImpl.class).to(FlatService.class); // Регистрация FlatService
                bind(XmlConverter.class).to(XmlConverter.class); // Регистрация XmlConverter
            }
        });

// Регистрируем контроллеры
        register(FlatController.class);
        register(EnumsController.class);

        // Регистрируем обработчики ошибок
        register(BadRequestExceptionMapper.class);
        register(ServerErrorExceptionMapper.class);
        register(NotFoundFlatExceptionMapper.class);
//        register(NotFoundExceptionMapper.class);
        register(UniversalExceptionMapper.class);

        // Регистрируем CORS-фильтр
        register(CorsFilter.class);

        // Указываем путь, как было в старом приложении
        setApplicationName("housing-m");
    }
}
