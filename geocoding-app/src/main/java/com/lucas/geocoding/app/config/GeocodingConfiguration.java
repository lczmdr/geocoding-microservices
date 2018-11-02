package com.lucas.geocoding.app.config;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.RestTemplate;

import com.lucas.geocoding.app.exception.GeocodingApplicationException;

/**
 * Provides the beans used in the geocoding application.
 */
@Configuration
@EnableConfigurationProperties(GeocodingProperties.class)
@ControllerAdvice
public class GeocodingConfiguration {

    /**
     * Creates a rest template bean to communicate with the geocoding service. Includes a custom
     * error handler and a jackson htttp converter to serialze to json.
     * @param builder the rest template builder
     * @return the rest template
     */
    @Bean
    protected RestTemplate geocodingServiceRestTemplate(RestTemplateBuilder builder) {
        return builder.additionalMessageConverters(new MappingJackson2HttpMessageConverter())
                .errorHandler(new RestTemplateErrorHandler())
                .build();
    }

    @ExceptionHandler(GeocodingApplicationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public Object handleException(HttpServletResponse servletResponse, GeocodingApplicationException exception) throws IOException {

        servletResponse.setContentType(MediaType.APPLICATION_JSON_VALUE);
        servletResponse.setStatus(400);

        return exception.getErrorMessage();
    }

}
