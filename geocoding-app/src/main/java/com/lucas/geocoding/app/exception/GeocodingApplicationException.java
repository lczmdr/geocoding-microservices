package com.lucas.geocoding.app.exception;

import java.io.IOException;

public class GeocodingApplicationException extends IOException {

    private String errorMessage;

    public GeocodingApplicationException() {
    }

    public GeocodingApplicationException(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
