package com.lucas.geocoding.app.client;

import java.util.Optional;

import com.lucas.geocoding.common.model.GeoCodingResponse;

public interface GeocodingServiceClient {

    Optional<GeoCodingResponse> getGeolocation(String address);

}
