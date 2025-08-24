package com.linkshrink.shortner.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
import com.linkshrink.shortner.entity.Status;

@RestController
@RequestMapping("/api/status")
public class StatusController {

    @GetMapping("")
    public Status getStatus() {
        return new Status();
    }


    @GetMapping("/admin")
    public Status getStatusAdmin() {
        return new Status();
    }

    @GetMapping("/enc")
    public Status getStatusEnc() {
        return new Status();
    }

    @GetMapping("/encAdmin")
    public Status getStatusEncAdmin() {
        return new Status();
    }

}
