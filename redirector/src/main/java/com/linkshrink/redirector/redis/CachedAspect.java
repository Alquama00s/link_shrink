package com.linkshrink.redirector.redis;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class CachedAspect {

    @Autowired
    Client client;

    @Around("@annotation(Cached)")
    public Object tryCaching(ProceedingJoinPoint jp) throws Throwable {
        String key="";
        try{
            var method = ((MethodSignature)jp.getSignature()).getMethod();
            var ann = method.getAnnotation(Cached.class);
            key = ann.prefix()+jp.getArgs()[ann.paramIndex()];
            Class<?> clazz = method.getReturnType();
            var cachedres = client.get(key,clazz);
            log.info("found value: "+cachedres);
            if(cachedres!=null)return cachedres;
        }catch (Exception ex){
            log.error(ex.toString());
        }
        var res = jp.proceed();
        try {
            if(res!=null&&!key.isEmpty())client.put(key,res);
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return res;
    }


    @Around("@annotation(CachedRaw)")
    public Object tryCachingRaw(ProceedingJoinPoint jp) throws Throwable {
        String key="";
        try{
            var method = ((MethodSignature)jp.getSignature()).getMethod();
            var ann = method.getAnnotation(CachedRaw.class);
            key = ann.prefix()+jp.getArgs()[ann.paramIndex()];
            var cachedres = client.rawGet(key);
            log.info("found raw value: "+cachedres);
            if(cachedres!=null)return cachedres;
        }catch (Exception ex){
            log.error(ex.toString());
        }
        var res = jp.proceed();
        try {
            if(res!=null&&!key.isEmpty())client.rawPut(key,res.toString());
        }catch (Exception ex){
            log.error(ex.toString());
        }
        return res;
    }


}
