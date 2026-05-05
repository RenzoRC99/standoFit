package com.standofit.back.configuration.web;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.UUID;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void configureMessageConverters(java.util.List<HttpMessageConverter<?>> converters) {
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof MappingJackson2HttpMessageConverter jacksonConverter) {
                jacksonConverter.getObjectMapper().registerModule(
                    new com.fasterxml.jackson.databind.module.SimpleModule()
                        .addSerializer(UUID.class, new com.fasterxml.jackson.databind.ser.std.UUIDSerializer())
                );
            }
        }
    }
}