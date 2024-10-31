package com.alluz.m2m.sender.dummy;

import com.alluz.m2m.handler.M2MMessageHandler;
import com.alluz.m2m.message.M2MCommunicationException;
import com.alluz.m2m.message.M2MPlainTextMessage;
import com.alluz.m2m.message.M2MMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This sending worker does nothing but printing the message to the console
 */
 public class DummySender extends M2MMessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(DummySender.class);

    public DummySender() {
        //lazy
    }

    public DummySender(String inTypeClassName, String outTypeClassName) {
        super(inTypeClassName, outTypeClassName);
    }

    public void doSend(M2MMessage  message) throws M2MCommunicationException {
        LOGGER.debug("Sending: {}\n",((M2MPlainTextMessage) message).getSensorData());
    }

    @Override
    public <T extends M2MMessage> void handleMessage(T message) throws M2MCommunicationException {
        LOGGER.debug("handling message");
        this.doSend(message);
    }
}
