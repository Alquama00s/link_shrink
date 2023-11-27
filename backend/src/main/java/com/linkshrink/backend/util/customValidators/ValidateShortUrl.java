package com.linkshrink.backend.util.customValidators;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Constraint(validatedBy = ShortUrlValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateShortUrl {
    String message() default "ShortUrl must only contain alpha numeric characters and `-`";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
