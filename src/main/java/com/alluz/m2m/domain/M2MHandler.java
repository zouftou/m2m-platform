package com.alluz.m2m.domain;

import java.util.HashMap;
import java.util.Map;

public class M2MHandler {

    private Class<?> className;

    private final Map<String, String> properties = new HashMap<>();

    public Class<?> getClassName() {
        return className;
    }

    public void setClassName(Class<?> className) {
        this.className = className;
    }

    public void addProperty(String name, String value) {
        properties.put(name,value);
    }

    public Map<String, String> getProperties() {
        return properties;
    }
}
