package com.luxoft.springadvanced.springproxypostprocessor;

import com.luxoft.springadvanced.springproxypostprocessor.jmx.JmxExporter;
import org.springframework.stereotype.Component;

@Component
@JmxExporter
@SuppressWarnings("unused")
public class ExampleJmxService {
    public void printText(String text) {
        System.out.println(text);
    }

    public int ultimateAnswer() {
        return 42;
    }
}