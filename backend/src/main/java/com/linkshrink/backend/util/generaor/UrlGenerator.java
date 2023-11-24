package com.linkshrink.backend.util.generaor;

public interface UrlGenerator {

    String generateShortUrl();

    boolean urlExist(String url);


}
