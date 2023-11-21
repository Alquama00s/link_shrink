package com.linkshrink.backend.generaor;

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
// the reverse generation takes O(URL_LENGTH) time with O(CHARACTER_SET.length()) auxiliary space
//thus url redirection is very fast

@Component
@Primary
public class RadixGenerator implements UrlGenerator {

    private static final String CHARACTER_SET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    private static Map<Character,Integer> CHARACTER_SET_INDEX=new HashMap<>();

    private static final int URL_LENGTH=7;
    private static final long MAX_VAL=(long)Math.pow(CHARACTER_SET.length(),URL_LENGTH)-1;
    private static long CURRENT_VAL=-1;

    private EntityManager entityManager;

    static {
        for(int i=0;i<CHARACTER_SET.length();i++){
            CHARACTER_SET_INDEX.put(CHARACTER_SET.charAt(i),i);
        }
    }

    @Autowired
    public RadixGenerator(EntityManager entityManager) {
        this.entityManager = entityManager;
        Object ob = entityManager.createQuery("select max(id) from urls")
                .getSingleResult();

        long max = ob==null?0:(long)ob;

        CURRENT_VAL=max;
    }


    private static String convertToRadixString(long number)throws Exception {
        if(number>MAX_VAL){
            throw new Exception("the value: "+number+" exceeds system capacity");
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

    public static long convertFromRadixString(String RadixString) {
        int base = CHARACTER_SET.length();
        long result = 0;
        int power = 0;

        for (int i = RadixString.length() - 1; i >= 0; i--) {
            char c = RadixString.charAt(i);
            int digitValue = CHARACTER_SET_INDEX.get(c);
            result += digitValue * Math.pow(base, power);
            power++;
        }

        return result;
    }

    @Override
    public String generateShortUrl(){

        try {
            return convertToRadixString(++CURRENT_VAL);
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
