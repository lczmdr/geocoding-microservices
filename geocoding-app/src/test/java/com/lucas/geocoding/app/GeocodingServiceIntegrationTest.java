package com.lucas.geocoding.app;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.get;
import static com.github.tomakehurst.wiremock.client.WireMock.urlPathEqualTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

import org.apache.commons.io.IOUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.lucas.geocoding.app.client.GeocodingServiceClient;
import com.lucas.geocoding.app.exception.GeocodingApplicationException;
import com.lucas.geocoding.common.model.GeoCodingResponse;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class GeocodingServiceIntegrationTest {

    @Rule
    public WireMockRule wireMockRule = new WireMockRule(9019);

    @Autowired
    private GeocodingServiceClient geocodingServiceClient;

    @Test
    public void invalidGeolocationServiceResponse() throws Exception {

        wireMockRule.stubFor(get(urlPathEqualTo("/geocoding-service/api/translate"))
                .willReturn(aResponse()
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(500)));

        Exception exception = null;

        try {
            geocodingServiceClient.getGeolocation(null);
        }
        catch (Exception e) {
            exception = (Exception) e.getCause();
        }

        assertNotNull(exception);
        assertEquals(GeocodingApplicationException.class, exception.getClass());

    }

    private String readResponseBodyFile(String fileName) throws IOException {
        InputStream resourceAsStream = getClass().getClassLoader().getResourceAsStream(fileName);
        return IOUtils.toString(resourceAsStream, "UTF-8");
    }

    @Test
    public void validGeolocationServiceResponse() throws Exception {

        String body = readResponseBodyFile("geocoding-service/valid-response.json");

        String address = "900 6th Aveuve, New York";

        wireMockRule.stubFor(get(urlPathEqualTo("/geocoding-service/api/translate"))
                .willReturn(aResponse()
                        .withBody(body)
                        .withHeader(CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .withStatus(200)));

        Optional<GeoCodingResponse> geolocation = geocodingServiceClient.getGeolocation(address);

        assertNotNull(geolocation.get());

        GeoCodingResponse response = geolocation.get();
        assertEquals("900 6th Ave, New York, NY 10118, USA", response.getFormattedAddress());
        assertEquals("40.7485327", response.getLatitude());
        assertEquals("-73.9884479", response.getLongitude());
    }

}
