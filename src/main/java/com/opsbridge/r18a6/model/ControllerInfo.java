package com.opsbridge.r18a6.model;

import java.util.ArrayList;
import java.util.List;

public class ControllerInfo {

    private String className;
    private List<EndpointInfo> endpoints = new ArrayList<>();

    public String getClassName() { return className; }
    public void setClassName(String className) { this.className = className; }

    public List<EndpointInfo> getEndpoints() { return endpoints; }

}
