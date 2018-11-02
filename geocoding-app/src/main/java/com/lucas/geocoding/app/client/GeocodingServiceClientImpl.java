package com.lucas.geocoding.app.client;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.lucas.geocoding.app.config.GeocodingProperties;
import com.lucas.geocoding.common.model.GeoCodingResponse;

@Service
public class GeocodingServiceClientImpl implements GeocodingServiceClient {

    @Autowired
    private RestTemplate geocodingServiceRestTemplate;

    @Autowired
    private GeocodingProperties properties;

    @Override
    public Optional<GeoCodingResponse> getGeolocation(String address) {
        String url = properties.getGeocodingService().getUrl() + "/geocoding-service/api/translate?address=" + address;
        GeoCodingResponse geoCodingResponse = geocodingServiceRestTemplate.getForObject(url, GeoCodingResponse.class);
        return Optional.of(geoCodingResponse);
    }
}
