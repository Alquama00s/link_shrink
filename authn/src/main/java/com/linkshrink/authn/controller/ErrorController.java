package com.linkshrink.authn.controller;


import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.Map;

@CrossOrigin
@Slf4j
@RestController
public class ErrorController {

    @PostMapping("/error")
    public String error(HttpServletRequest request) throws IOException {
        log.info(request.getReader().lines().reduce((i,j)->i+j).get());
        return "error occured";
    }

    @GetMapping("/error-auth-entrypoint")
    public ResponseEntity<Map<String,String>> errorEntryPont() throws IOException {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(Map.of("message","Un authenticated"));
    }

}
