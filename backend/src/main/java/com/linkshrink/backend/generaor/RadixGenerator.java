package com.linkshrink.backend.generaor;

import com.linkshrink.backend.entity.Urls;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

//This class implements url generator as Radix generator
//This takes a number and converts it into `CHARACTER_SET.length()` base string
// using the same character set
//This also provides a reverse conversion back to the number which can be used to
// find data in database (string comparison is slower that number comparison)

// the generation of unique url takes O(ln(number)/ln(CHARACTER_SET.length())) no extra space

@Component
@Primary
public class RadixGenerator implements UrlGenerator {

    private static final String CHARACTER_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    private static final int URL_LENGTH=7;
    private static final long MAX_VAL=(long)Math.pow(CHARACTER_SET.length(),URL_LENGTH)-1;
    private static long CURRENT_VAL=-1;

    private EntityManager entityManager;


    @Autowired
    public RadixGenerator(EntityManager entityManager) {
        this.entityManager = entityManager;
        Object ob = entityManager.createQuery("select max(id) from Urls", Urls.class)
                .getSingleResult();

        System.out.println(ob);

        long max = ob==null?0:(long)ob;

        CURRENT_VAL=max;
    }


    private static String convertToRadixString(long number)throws Exception {
        if(number>MAX_VAL){
            throw new Exception("the value: "+number+" exceeds system capacity!");
        }
        if (number == 0) {
            return CHARACTER_SET.substring(0,1);
        }

        StringBuilder RadixString = new StringBuilder();
        int base = CHARACTER_SET.length();

        while (number > 0) {
            int remainder = (int) (number % base);
            RadixString.insert(0, CHARACTER_SET.charAt(remainder));
            number /= base;
        }

        return RadixString.toString();
    }


    @Override
    public String generateShortUrl(){

        try {
            return String.format("%"+URL_LENGTH+"s",convertToRadixString(++CURRENT_VAL)).replace(' ',CHARACTER_SET.charAt(0));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean urlExist(String url) {
        Object ob = entityManager.createQuery("select id from urls u where u.short_url = :url")
                .setParameter("url",url)
                .getSingleResult();
        return ob!=null;
    }
}
