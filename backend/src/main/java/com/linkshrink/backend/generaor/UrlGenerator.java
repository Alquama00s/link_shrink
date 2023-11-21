package com.linkshrink.backend.generaor;

public interface UrlGenerator {

    String generateShortUrl();

    boolean urlExist(String url);


}
