package com.linkshrink.backend.entity;

import java.time.LocalTime;


public class Status {

    private String status;
    private String serverTime;


    public Status(){
        this.status="OK";
        this.serverTime=LocalTime.now().toString();
    }


    public String getStatus() {
        return status;
    }


    public void setStatus(String status) {
        this.status = status;
    }


    public String getServerTime() {
        return serverTime;
    }


    public void setServerTime(String serverTime) {
        this.serverTime = serverTime;
    }

        
}
