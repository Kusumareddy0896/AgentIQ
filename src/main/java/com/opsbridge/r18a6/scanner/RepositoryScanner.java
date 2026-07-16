package com.opsbridge.r18a6.scanner;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class RepositoryScanner {

    public List<Path> scan(String rootPath) throws IOException {
        Path root = Paths.get(rootPath);
        if (!Files.exists(root)) return new ArrayList<>();

        List<Path> files = Files.walk(root, FileVisitOption.FOLLOW_LINKS)
                .filter(Files::isRegularFile)
                .filter(p -> {
                    String n = p.getFileName().toString().toLowerCase();
                    return n.equals("pom.xml") || n.equals("build.gradle") || n.equals("build.gradle.kts")
                            || n.equals("application.yml") || n.equals("application.yaml") || n.equals("application.properties")
                            || n.endsWith(".java");
                })
                .collect(Collectors.toList());

        return files;
    }

}
