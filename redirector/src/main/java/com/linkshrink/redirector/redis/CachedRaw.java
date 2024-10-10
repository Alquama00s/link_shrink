package com.linkshrink.redirector.redis;


import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * [CachedRaw] is faster than [Cached]
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface CachedRaw {
    int paramIndex() default 0;
    String prefix() default "";
}
