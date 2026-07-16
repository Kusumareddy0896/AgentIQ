package com.opsbridge.r18a6.model;

public class ConfigKey {

    private String key;

    public ConfigKey() {}

    public ConfigKey(String key) { this.key = key; }

    public String getKey() { return key; }

    @Override
    public String toString() { return key; }

}
