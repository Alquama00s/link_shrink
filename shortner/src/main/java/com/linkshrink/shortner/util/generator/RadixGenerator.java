package com.linkshrink.shortner.util.generator;

import com.linkshrink.shortner.customException.KnownException;
import java.util.Random;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

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


    private String convertToRadixString(long number) {

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
    public String generateShortUrl(Long currentCounter) throws Exception {
        if(currentCounter>MAX_VAL)throw new KnownException("capacity reached");
        String url = String.format("%" + URL_LENGTH + "s", convertToRadixString(currentCounter)).replace(' ', CHARACTER_SET.charAt(0));
        return "@"+url;

    }

    @Override
    public String generateShortUrl() {
        var random = new Random();
        Long randomSeed = random.nextLong(MAX_VAL);
        String url = String.format("%" + URL_LENGTH + "s", convertToRadixString(randomSeed)).replace(' ', CHARACTER_SET.charAt(0));
        return "@"+url;
    }


}
