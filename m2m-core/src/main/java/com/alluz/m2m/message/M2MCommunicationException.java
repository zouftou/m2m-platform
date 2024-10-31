package com.alluz.m2m.message;

public class M2MCommunicationException extends Exception {

    public M2MCommunicationException() {
        super();
    }

    public M2MCommunicationException(String msg) {
        super(msg);
    }

    public M2MCommunicationException(Throwable cause) {
        super(cause);
    }
}
