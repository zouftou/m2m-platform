package com.alluz.m2m.handler.converter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.alluz.m2m.handler.M2MMessageHandler;
import com.alluz.m2m.message.M2MPlainTextMessage;
import com.alluz.m2m.message.M2MCommunicationException;
import com.alluz.m2m.message.M2MMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONConverter extends M2MMessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(JSONConverter.class);

    public JSONConverter() {
        super();
    }

    public JSONConverter(String inTypeClassName, String outTypeClassName) {
        super(inTypeClassName, outTypeClassName);
    }

    @Override
    public <T extends M2MMessage> void handleMessage(T message) throws M2MCommunicationException {
        LOGGER.debug("handling message");
        LOGGER.trace("Serialization of: {}",message.getClass().getSimpleName());
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            String json = objectMapper.writeValueAsString(message);
            LOGGER.trace("Serialization 2 JSON: \n {}",json);
            M2MPlainTextMessage msg = new M2MPlainTextMessage();
            msg.setSensorData(json);
            this.setMessage(msg);
        } catch (JsonProcessingException ex) {
            LOGGER.error("Json is not valid", ex);
        }
    }
}
