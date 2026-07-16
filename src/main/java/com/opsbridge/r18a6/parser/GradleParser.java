package com.opsbridge.r18a6.parser;

import com.opsbridge.r18a6.model.DependencyInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradleParser {

    private static final Pattern DEP_PATTERN = Pattern.compile("['\"]([\w\-.]+):([\w\-.]+):([\w\-.]+)['\"]");

    public static List<DependencyInfo> parseBuildFile(Path buildFile) {
        List<DependencyInfo> deps = new ArrayList<>();
        try {
            String content = new String(Files.readAllBytes(buildFile));
            Matcher m = DEP_PATTERN.matcher(content);
            while (m.find()) {
                String group = m.group(1);
                String artifact = m.group(2);
                String version = m.group(3);
                deps.add(new DependencyInfo(group, artifact, version));
            }
        } catch (IOException e) {
            // ignore
        }
        return deps;
    }

}
