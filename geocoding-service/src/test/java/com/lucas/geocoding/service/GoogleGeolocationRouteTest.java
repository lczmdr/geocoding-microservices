package com.lucas.geocoding.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GoogleGeolocationRouteTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void succesfullResponseTest() {
        // We used London as the testing address since it should remain in the same place
        // and not move around
        String expectedResponse = "{\"formattedAddress\":\"London, UK\",\"latitude\":\"51" +
                ".5073509\",\"longitude\":\"-0.1277583\"}";

        ResponseEntity<String> response = restTemplate.getForEntity("/api/translate?address=London",
                String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertNotNull(response.getBody());
        String responseBody = response.getBody();
        assertEquals(expectedResponse, responseBody);
    }

    @Test
    public void badRequestResponseTest() {
        String invalidAddress = "FFFFFFFOOOOPPPP";
        ResponseEntity<String> response = restTemplate.getForEntity("/api/translate?address=" + invalidAddress,
                        String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody()).isEqualTo("Address geolocation not found.");
    }

}
