package com.lucas.geocoding.service.routes;

import java.io.IOException;

import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.apache.camel.http.common.HttpOperationFailedException;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

import com.lucas.geocoding.service.config.GeocodingServiceProperties;

@Component
public class GoogleGeolocationRoute extends RouteBuilder {

    @Autowired
    private GeocodingServiceProperties properties;

    @Autowired
    private JaxbDataFormat geocodeDataFormat;

    @Override
    public void configure() throws Exception {

        GeocodingServiceProperties.Google googleProperties = properties.getGoogle();

        onException(IOException.class, HttpOperationFailedException.class)
                .maximumRedeliveries(3)
                .handled(true)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(500))
                .setBody(simple("Operation failed"));

        restConfiguration()
                .component("servlet")
                .contextPath("/")
                .bindingMode(RestBindingMode.json);

        rest("/api/")
                .id("api-geocoding")
                .get("/translate?address={address}")
                .produces(MediaType.APPLICATION_JSON_VALUE)
                .consumes(MediaType.APPLICATION_JSON_VALUE)
                .to("seda:translate-service");

        from("seda:translate-service")
            .id("translate-service")
            .setHeader(Exchange.HTTP_METHOD, constant("GET"))
            .setHeader(Exchange.HTTP_QUERY, simple("address=${in.header.address}" + "&key=" + googleProperties.getApiKey()))
            .process(exchange -> log.info("googleProperties.getUrl() is {} ", googleProperties.getUrl()))
            .to("https4://" + googleProperties.getUrl() + "&bridgeEndpoint=true&throwExceptionOnFailure=false")
            .choice()
                .when(simple("${header.CamelHttpResponseCode} == '200'"))
                    .process(exchange -> log.info("The response code is: {}", exchange.getIn().getHeader(Exchange.HTTP_RESPONSE_CODE)))
                    .unmarshal(geocodeDataFormat)
                    .to("seda:format-response")
                .otherwise()
                    .to("seda:error-message");

        from("seda:format-response")
            .id("format-response")
            .choice()
                .when(simple("${body.status} == 'OK'"))
                    .to("dozer:transformGeoformat?mappingFile=classpath:dozerMapping.xml&targetModel=com.lucas.geocoding.common.model.GeoCodingResponse")
                    .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
                    .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200))
                .otherwise()
                    .to("seda:not-found");

        from("seda:not-found")
            .id("not-found")
            .setBody(simple("Address geolocation not found."))
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400));

        from("seda:error-message")
            .id("error-message")
            .setBody(simple("Error getting the information from the google geolocation service."))
            .setHeader(Exchange.CONTENT_TYPE, constant(MediaType.APPLICATION_JSON_VALUE))
            .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(400));
    }

}
