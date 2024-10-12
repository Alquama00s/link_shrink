package com.linkshrink.redirector.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Slf4j
public class Client {

    private RedisClient client;
    private StatefulRedisConnection<String, String> connection;
    private RedisCommands<String, String> redisCommand;
    private final ObjectMapper om = new ObjectMapper();

    private Client() {
    }


    public static Client build() {
        var uri = RedisURI.builder()
                .withHost("localhost")
                .withPort(6379)
                .withTimeout(Duration.of(60, ChronoUnit.SECONDS))
                .build();
        return build(uri);
    }

    public static Client build(RedisURI redisURI) {
        var c = new Client();
        c.client = RedisClient.create(redisURI);
        return c;
    }

    private boolean tryConnect() {
        try{
            if (connection==null||!connection.isOpen()) {
                connection = client.connect();
                redisCommand = connection.sync();
            }
            return connection.isOpen();
        } catch (Exception e) {
            log.error(e.toString());
        }

        return false;
    }

    public <v> v get(String key, Class<v> clazz) {
        if (tryConnect()) {
            try {
                var res = redisCommand.get(key);
                return om.readValue(res, clazz);
            } catch (JsonProcessingException ex) {
                redisCommand.del(key);
            } catch (Exception ex) {
                log.error(ex.toString());
            }
        }
        return null;
    }

    public void put(String key, Object Value) {
        if (tryConnect()) {
            try {
                var serValue = om.writeValueAsString(Value);
                redisCommand.set(key, serValue, SetArgs.Builder.ex(200));
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }


    public String rawGet(String key) {
        if (tryConnect()) {
            try {
                return redisCommand.get(key);
            }catch (Exception ex) {
                log.error(ex.toString());
            }
        }
        return null;
    }


    public void rawPut(String key, String value) {
        if (tryConnect()) {
            try {
                redisCommand.set(key, value,SetArgs.Builder.ex(200));
            } catch (Exception e) {
                log.error(e.toString());
            }
        }
    }




    public void destroy() {
        client.shutdown();
        connection.close();
    }

}
