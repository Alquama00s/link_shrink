package com.linkshrink.backend.util.customValidators;

import com.linkshrink.backend.entity.Urls;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;



//This validator validates the shortUrl only if it is not auto generated
//This leaves the validation of the generated url to the generator class
public class ShortUrlValidator implements ConstraintValidator<ValidateShortUrl, Urls> {

    @Override
    public void initialize(ValidateShortUrl constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Urls url, ConstraintValidatorContext constraintValidatorContext) {
        return url.isGenerated() || (url!=null &&  url.getShortUrl().matches("^[a-zA-Z0-9-]+$"));
    }
}
