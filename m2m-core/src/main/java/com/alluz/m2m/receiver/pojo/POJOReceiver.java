package com.alluz.m2m.receiver.pojo;

import com.alluz.m2m.handler.M2MMessageHandler;
import com.alluz.m2m.message.M2MCommunicationException;
import com.alluz.m2m.message.M2MMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class POJOReceiver extends M2MMessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(POJOReceiver.class);

    @Override
    public <T extends M2MMessage> void handleMessage(T message) throws M2MCommunicationException {
        LOGGER.debug("handling message");

        // lazy, nothing happens here,
        // normally you would handle the message, convert it to a new one
        // and set the message but as the outgoing one is the same as the incoming one we don't
        // need to do anything.
        // so this is pro format
        this.setMessage(message);
    }
}
