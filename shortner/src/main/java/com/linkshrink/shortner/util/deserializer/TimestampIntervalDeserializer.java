package com.linkshrink.shortner.util.deserializer;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.Duration;

public class TimestampIntervalDeserializer extends JsonDeserializer<Timestamp> {
    @Override
    public Timestamp deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.readValueAs(String.class);
        if(value==null)return null;
        var now =new Timestamp(System.currentTimeMillis());
        var interval = Duration.parse(value);
        return Timestamp.from(now.toInstant().plus(interval));

    }
}
