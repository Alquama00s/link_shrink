package com.linkshrink.backend.util.generaor;

import com.linkshrink.backend.customException.KnownException;
import com.linkshrink.backend.entity.Urls;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

//This class implements url generator as Radix generator
//This takes a number and converts it into `CHARACTER_SET.length()` base string
// using the same character set
//This also provides a reverse conversion back to the number which can be used to
// find data in database (string comparison is slower that number comparison)

// the generation of unique url takes O(ln(number)/ln(CHARACTER_SET.length())) no extra space

@Component
@Primary
public class RadixGenerator extends UrlGenerator {

    private static final String CHARACTER_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";


    private static final int URL_LENGTH=7;
    private static final long MAX_VAL=(long)Math.pow(CHARACTER_SET.length(),URL_LENGTH)-1;

//    private  static  final long MAX_VAL=2;

    private static long CURRENT_VAL=0;
    private EntityManager entityManager;


    @Autowired
    public RadixGenerator(EntityManager entityManager) {
        try{
            this.entityManager = entityManager;
            Urls ob = entityManager
                    .createQuery("select u from Urls u where u.generated=true order by u.creationTime desc, u.id desc limit 1", Urls.class)
                    .getSingleResult();

            System.out.println(ob.getShortUrl());

            long max = convertFromBase62(ob.getShortUrl().substring(1));


            CURRENT_VAL=(max+1)%MAX_VAL;
        }catch (Exception ex){
            System.out.println(ex);
            CURRENT_VAL=0;
        }

        System.out.println(CURRENT_VAL);

    }


    private String convertToRadixString(long number)throws Exception {

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

        if(urlExist(RadixString.toString())){
            throw new KnownException("Generated url capacity reached");
        }

        return RadixString.toString();
    }

    public static long convertFromBase62(String base62String) {
        int base = CHARACTER_SET.length();
        long result = 0;
        int power = 0;

        for (int i = base62String.length() - 1; i >= 0; i--) {
            char c = base62String.charAt(i);
            int digitValue = CHARACTER_SET.indexOf(c);
            result += digitValue * Math.pow(base, power);
            power++;
        }

        return result;
    }


    @Override
    public String generateShortUrl() throws Exception {
        String url = String.format("%" + URL_LENGTH + "s", convertToRadixString(CURRENT_VAL)).replace(' ', CHARACTER_SET.charAt(0));
        CURRENT_VAL++;
        CURRENT_VAL %= MAX_VAL;
        return url;

    }
}
