package com.lucas.geocoding.service.config;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;

import org.apache.camel.converter.jaxb.JaxbDataFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.lucas.geocoding.service.model.GeocodeResponse;

/**
 * The Geocoding configuration bean.
 */
@Configuration
@EnableConfigurationProperties(GeocodingServiceProperties.class)
public class GeocodingConfiguration {

    /** The geocoding service properties. */
    private GeocodingServiceProperties properties;

    /**
     * Instantiates a new Geocoding configuration.
     *
     * @param properties the properties.
     */
    @Autowired
    public GeocodingConfiguration(GeocodingServiceProperties properties) {
        this.properties = properties;
    }

    /**
     * Create a jaxb data format with the mapped beans.
     * @return the jaxb data format.
     * @throws JAXBException a jaxb exception in case the jaxb context has an error.
     */
    @Bean
    public JaxbDataFormat geocodeDataFormat() throws JAXBException {
        JaxbDataFormat geocodeDataFormat = new JaxbDataFormat();
        JAXBContext jaxbContext = JAXBContext.newInstance(GeocodeResponse.class);
        geocodeDataFormat.setContext(jaxbContext);
        return geocodeDataFormat;
    }

}
