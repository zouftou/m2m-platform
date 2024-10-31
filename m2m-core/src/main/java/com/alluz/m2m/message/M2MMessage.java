package com.alluz.m2m.message;

/**
 * The message abstraction we will send around
 * Later on we might add fields common to all messages
 */
public abstract class M2MMessage {

    protected String sensorId;

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }
}
