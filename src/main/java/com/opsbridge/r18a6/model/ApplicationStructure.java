package com.opsbridge.r18a6.model;

import java.util.ArrayList;
import java.util.List;

public class ApplicationStructure {

    private List<DependencyInfo> dependencies = new ArrayList<>();
    private List<EndpointInfo> endpoints = new ArrayList<>();
    private List<ServiceInfo> services = new ArrayList<>();
    private List<ConfigKey> configKeys = new ArrayList<>();

    public List<DependencyInfo> getDependencies() { return dependencies; }
    public void addDependency(DependencyInfo d) { dependencies.add(d); }

    public List<EndpointInfo> getEndpoints() { return endpoints; }
    public void addEndpoint(EndpointInfo e) { endpoints.add(e); }

    public List<ServiceInfo> getServices() { return services; }
    public void addService(ServiceInfo s) { services.add(s); }

    public List<ConfigKey> getConfigKeys() { return configKeys; }
    public void addConfigKey(ConfigKey k) { configKeys.add(k); }

}
