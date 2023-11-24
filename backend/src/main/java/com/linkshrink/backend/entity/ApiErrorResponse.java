package com.linkshrink.backend.entity;

import java.sql.Timestamp;

public class ApiErrorResponse {

    private String message;
    private Timestamp timeStamp;

    public ApiErrorResponse(String message) {
        this.message = message;
        this.timeStamp = new Timestamp(System.currentTimeMillis());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Timestamp getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Timestamp timeStamp) {
        this.timeStamp = timeStamp;
    }
}
