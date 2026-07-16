package com.opsbridge.r18a6.parser;

import com.opsbridge.r18a6.model.ApplicationStructure;
import com.opsbridge.r18a6.model.ConfigKey;
import com.opsbridge.r18a6.model.DependencyInfo;
import com.opsbridge.r18a6.model.EndpointInfo;
import com.opsbridge.r18a6.model.ServiceInfo;
import com.opsbridge.r18a6.scanner.RepositoryScanner;

import java.io.File;
import java.nio.file.Path;
import java.util.List;

public class JavaApplicationParser {

    public ApplicationStructure parse(String rootPath) {
        ApplicationStructure structure = new ApplicationStructure();
        try {
            RepositoryScanner scanner = new RepositoryScanner();
            List<Path> files = scanner.scan(rootPath);
            for (Path p : files) {
                String name = p.getFileName().toString().toLowerCase();
                if (name.equals("pom.xml")) {
                    List<DependencyInfo> deps = MavenParser.parsePom(p.toFile());
                    deps.forEach(structure::addDependency);
                } else if (name.equals("build.gradle") || name.equals("build.gradle.kts")) {
                    List<DependencyInfo> deps = GradleParser.parseBuildFile(p);
                    deps.forEach(structure::addDependency);
                } else if (name.endsWith(".java")) {
                    List<EndpointInfo> endpoints = SpringControllerParser.parseFile(p);
                    endpoints.forEach(structure::addEndpoint);
                    List<ServiceInfo> services = SpringServiceParser.parseFile(p);
                    services.forEach(structure::addService);
                } else if (name.equals("application.yml") || name.equals("application.yaml")) {
                    List<ConfigKey> keys = JavaConfigParser.parseYaml(p);
                    keys.forEach(structure::addConfigKey);
                } else if (name.equals("application.properties")) {
                    List<ConfigKey> keys = JavaConfigParser.parseProperties(p);
                    keys.forEach(structure::addConfigKey);
                }
            }
        } catch (Exception e) {
            // ignore errors - return partial structure
        }
        return structure;
    }

}
