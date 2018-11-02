package com.lucas.geocoding.app.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import com.lucas.geocoding.app.exception.GeocodingApplicationException;

/**
 * The rest template error handler used to get the information from the geocoding service.
 */
@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse clientHttpResponse) throws IOException {
        // returns error in case the status codes is not a 2xx
        return clientHttpResponse.getStatusCode().series() != HttpStatus.Series.SUCCESSFUL;
    }

    @Override
    public void handleError(ClientHttpResponse clientHttpResponse) throws IOException {
        // just thrown a custom io exception for simplicity
        // ideally we should check the error response and throw a more customized error code

        InputStream inputStream = clientHttpResponse.getBody();
        String bodyMessage = IOUtils.toString(inputStream, "UTF-8");

        throw new GeocodingApplicationException("Error response obtained from the geocoding " +
                "service.\n" + bodyMessage);
    }
}
