package com.thebank.contas;

import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.thebank.contas.entities.StringToStatusContaConverter;

/**
 * Configures RabbitMQ to use events in our application.
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToStatusContaConverter());
    }
}