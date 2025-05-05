package com.example.converterdemo.controller;

import com.example.converterdemo.service.ConverterServiceImpl;
import jakarta.xml.bind.JAXBException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/converter")
public class ConverterController {

    private final ConverterServiceImpl converterService;

    public ConverterController(ConverterServiceImpl converterService) {
        this.converterService = converterService;
    }

    @PostMapping(consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_XML_VALUE)
    public String converter(@RequestBody String request) throws JAXBException {
        return converterService.convertMTtoMXUsingGeneratedClass(request);
    }

    @PostMapping(value = "/prowide", consumes = MediaType.TEXT_PLAIN_VALUE, produces = MediaType.TEXT_XML_VALUE)
    public String convertUsingProwide(@RequestBody String request) {
        return converterService.convertMTtoMXUsingProwide(request);
    }
}
