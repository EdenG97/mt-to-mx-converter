package com.example.converterdemo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ConverterDemoApplicationTests {

    @LocalServerPort
    private String port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testConverter() throws IOException {
        String uri = "http://localhost:" + port + "/api/converter";
        File file = new File("requests/mx103-request.makefile");
        String request = Files.readString(file.toPath());

        ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);
        Assertions.assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
