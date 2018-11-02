package com.lucas.geocoding.app.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.lucas.geocoding.app.client.GeocodingServiceClient;
import com.lucas.geocoding.app.exception.GeocodingApplicationException;
import com.lucas.geocoding.common.model.GeoCodingResponse;

@RestController
public class GeocodingController {

    @Autowired
    private RestTemplate geocodingServiceRestTemplate;

    @Autowired
    private GeocodingServiceClient geocodingServiceClient;

    @RequestMapping(value = "/geolocation", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public GeoCodingResponse getGeoLocation(@RequestParam("address") String address) throws GeocodingApplicationException {

        if (StringUtils.isEmpty(address)) {
            throw new GeocodingApplicationException("Invalid or empty address");
        }

        Optional<GeoCodingResponse> response = geocodingServiceClient.getGeolocation(address);
        if (!response.isPresent()) {
            throw new GeocodingApplicationException("Empty response from the geolocation service");
        }
        return response.get();
    }

}
