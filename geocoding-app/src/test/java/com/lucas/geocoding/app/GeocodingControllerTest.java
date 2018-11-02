package com.lucas.geocoding.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.lucas.geocoding.app.client.GeocodingServiceClient;
import com.lucas.geocoding.common.model.GeoCodingResponse;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeocodingControllerTest {

    @MockBean
    private GeocodingServiceClient geocodingServiceClient;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void invalidAddressParameterValue() throws Exception {

        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:" + port +
                "/geocoding-app/geolocation?address=", String.class);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        String responseBody = response.getBody();
        assertEquals("Invalid or empty address", responseBody);

    }

    @Test
    public void successfulResponse() throws Exception {

        GeoCodingResponse mockedResponse = new GeoCodingResponse();
        mockedResponse.setFormattedAddress("dummy address");
        mockedResponse.setLatitude("+33.3333");
        mockedResponse.setLongitude("-33.3333");

        Mockito.when(geocodingServiceClient.getGeolocation(anyString()))
                .then((Answer<Optional<GeoCodingResponse>>) invocationOnMock -> Optional.of(mockedResponse));

        ResponseEntity<GeoCodingResponse> response = restTemplate.getForEntity("http://localhost:" + port +
                "/geocoding-app/geolocation?address=dummy", GeoCodingResponse.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        GeoCodingResponse responseBody = response.getBody();
        assertEquals(mockedResponse.getFormattedAddress(), responseBody.getFormattedAddress());
        assertEquals(mockedResponse.getLongitude(), responseBody.getLongitude());
        assertEquals(mockedResponse.getLatitude(), responseBody.getLatitude());
    }

}
