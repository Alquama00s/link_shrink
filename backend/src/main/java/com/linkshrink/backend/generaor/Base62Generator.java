package com.linkshrink.backend.generaor;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;


public class Base62Generator implements GeneratorInterface{

    private static final String BASE62_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private int CURRENT_VAL=-1;
    @Autowired
    EntityManager entityManager;

    public Base62Generator() {
        Object ob = entityManager.createQuery("select max(id) from urls")
                .getSingleResult();

        int max = ob==null?0:(int)ob;

        CURRENT_VAL=max;
    }

    private static String convertToBase62(long number) {
        if (number == 0) {
            return "0";
        }

        StringBuilder base62String = new StringBuilder();
        int base = BASE62_CHARACTERS.length();

        while (number > 0) {
            int remainder = (int) (number % base);
            base62String.insert(0, BASE62_CHARACTERS.charAt(remainder));
            number /= base;
        }

        return base62String.toString();
    }

//    private static long convertFromBase62(String base62String) {
//        int base = BASE62_CHARACTERS.length();
//        long result = 0;
//        int power = 0;
//
//        for (int i = base62String.length() - 1; i >= 0; i--) {
//            char c = base62String.charAt(i);
//            int digitValue = BASE62_CHARACTERS.indexOf(c);
//            result += digitValue * Math.pow(base, power);
//            power++;
//        }
//
//        return result;
//    }

    @Override
    public String generateShortUrl() {
        return convertToBase62(++CURRENT_VAL);
    }

    @Override
    public boolean urlExist(String url) {
        Object ob = entityManager.createQuery("select id from urls u where u.short_url = :url")
                .setParameter("url",url)
                .getSingleResult();
        return ob!=null;
    }
}
