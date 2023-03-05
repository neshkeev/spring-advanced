package com.luxoft.springadvanced.springtesting.mockito.web;

import java.io.InputStream;

public class WebClient {

    public String getContent(ConnectionFactory connectionFactory) {
        final StringBuilder content = new StringBuilder();
        try (InputStream is = connectionFactory.getData()) {
            int count;
            while ((count = is.read()) != -1) {
                content.append(new String(Character.toChars(count)));
            }
            return content.toString();
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
