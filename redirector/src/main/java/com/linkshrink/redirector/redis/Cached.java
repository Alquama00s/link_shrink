package com.linkshrink.redirector.redis;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Cached {
    int paramIndex() default 0;
    String prefix() default "";
}

