package com.linkshrink.backend.util.generator;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RadixGeneratorTest {
    @Autowired
    private RadixGenerator radixGenerator;

    @Test
    @DisplayName(value = "Generated url should not be null")
    public void generatedUrlNotNullCheck() {
        String url = radixGenerator.generateShortUrl();
        Assertions.assertNotNull(url,"generated url should not be null");
    }

    @Test
    @DisplayName(value = "Generated urls sequence should be correct")
    public void sequenceCheck()throws Exception{
        Assertions.assertEquals(radixGenerator.generateShortUrl(0l),"@aaaaaaa");
        Assertions.assertEquals(radixGenerator.generateShortUrl(1l),"@aaaaaab");
        Assertions.assertEquals(radixGenerator.generateShortUrl(2l),"@aaaaaac");

    }




}
