package com.linkshrink.redirector.client;


import com.linkshrink.redirector.entity.Url;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "shortner",url = "${urls.shortner:http://localhost:8080}",path = "/api/urls")
public interface ShortnerClient {

    @GetMapping("/get")
    Url get(@RequestParam(value = "short_url") String shortUrl);
}
