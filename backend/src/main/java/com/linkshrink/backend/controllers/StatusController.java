package com.linkshrink.backend.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linkshrink.backend.entity.Status;

@RestController
@RequestMapping("/status")
public class StatusController {

    @GetMapping("")
    public Status getStatus() {
        return new Status();
    }

}
