package com.opsbridge.r18a6.model;

public class EndpointInfo {

    private String controller;
    private String path;
    private String httpMethod;

    public EndpointInfo() {}

    public EndpointInfo(String controller, String path, String httpMethod) {
        this.controller = controller;
        this.path = path;
        this.httpMethod = httpMethod;
    }

    public String getController() { return controller; }
    public String getPath() { return path; }
    public String getHttpMethod() { return httpMethod; }

    @Override
    public String toString() {
        return controller + " " + httpMethod + " " + path;
    }

}
