package com.linkshrink.redirector.redirector.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.view.RedirectView

@RestController
@RequestMapping("/api")
class RedirectController {

    @GetMapping("/")
    fun version(): RedirectView {
        return RedirectView().apply {
            this.url = "https://www.google.com"
        }
    }
}