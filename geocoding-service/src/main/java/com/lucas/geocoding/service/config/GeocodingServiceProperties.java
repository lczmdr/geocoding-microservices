package com.lucas.geocoding.service.config;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

/**
 * The type Geocoding service properties.
 */
@ConfigurationProperties("geocoding-service")
@Validated
public class GeocodingServiceProperties {

    /** The google config. */
    @Valid
    private Google google = new Google();

    /**
     * Gets google.
     *
     * @return the google
     */
    public Google getGoogle() {
        return google;
    }

    /**
     * Sets google.
     *
     * @param google the google
     */
    public void setGoogle(Google google) {
        this.google = google;
    }

    /**
     * The type Google.
     */
    public class Google {

        /** The url. */
        @NotEmpty
        private String url;

        /** The api key. */
        @NotEmpty
        private String apiKey;

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

        /**
         * Gets api key.
         *
         * @return the api key
         */
        public String getApiKey() {
            return apiKey;
        }

        /**
         * Sets api key.
         *
         * @param apiKey the api key
         */
        public void setApiKey(String apiKey) {
            this.apiKey = apiKey;
        }
    }

}
