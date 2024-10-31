package com.alluz.m2m.message;

/**
 * A so to say String representation of a/many sensor message/s
 */
public class M2MPlainTextMessage extends M2MMessage {

    private String sensorData;

    public String getSensorData() {
        return sensorData;
    }

    public void setSensorData(String sensorData) {
        this.sensorData = sensorData;
    }
}
