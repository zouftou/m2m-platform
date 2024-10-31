package com.zouftou.m2m.sender.http;

import com.zouftou.m2m.handler.M2MMessageHandler;
import com.zouftou.m2m.message.M2MMessage;

import com.zouftou.m2m.message.M2MPlainTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpSender extends M2MMessageHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(HttpSender.class);

    // the url to send to
    private String url;

    public HttpSender() {
        super();
    }

    public HttpSender(String inTypeClassName, String outTypeClassName) {
        super(inTypeClassName, outTypeClassName);
    }

    @Override
    public <T extends M2MMessage> void handleMessage(T message) {
        LOGGER.debug("handling message");
        this.doSend(message);
    }

    public void doSend(M2MMessage message) {

        try {

            HttpClient client = HttpClient.newHttpClient();

            String json = ((M2MPlainTextMessage) message).getSensorData();

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .POST(HttpRequest.BodyPublishers.ofString(json))
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            LOGGER.debug("Sending 'POST' request to URL : {}",url);
            LOGGER.debug("Response Code : {}",response.statusCode());
        } catch (IOException | InterruptedException ex) {
            LOGGER.error("Something goes wrong", ex);
        }
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
