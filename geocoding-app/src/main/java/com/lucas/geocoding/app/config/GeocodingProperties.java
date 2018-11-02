package com.lucas.geocoding.app.config;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * The Geocoding application properties bean mapper.
 */
@ConfigurationProperties("geocoding-app")
@Validated
public class GeocodingProperties {

    /** The geocoding service. */
    @Valid
    private GeocodingService geocodingService = new GeocodingService();

    /**
     * Gets geocoding service.
     *
     * @return the geocoding service
     */
    public GeocodingService getGeocodingService() {
        return geocodingService;
    }

    /**
     * Sets geocoding service.
     *
     * @param geocodingService the geocoding service
     */
    public void setGeocodingService(GeocodingService geocodingService) {
        this.geocodingService = geocodingService;
    }

    /**
     * The type Geocoding service.
     */
    public class GeocodingService {

        /** The geocoding service url. */
        @NotEmpty
        private String url;

        /**
         * Gets url.
         *
         * @return the url
         */
        public String getUrl() {
            return url;
        }

        /**
         * Sets url.
         *
         * @param url the url
         */
        public void setUrl(String url) {
            this.url = url;
        }
    }


}
