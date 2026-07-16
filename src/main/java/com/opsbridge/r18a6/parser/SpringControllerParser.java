package com.opsbridge.r18a6.parser;

import com.opsbridge.r18a6.model.EndpointInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SpringControllerParser {

    private static final Pattern CLASS_PATTERN = Pattern.compile("class\\s+(\\w+)");
    private static final Pattern REQUEST_MAPPING = Pattern.compile("@RequestMapping\\s*\(\s*\"([^\"]*)\"\s*\)");
    private static final Pattern METHOD_MAPPING = Pattern.compile("@(GetMapping|PostMapping|PutMapping|DeleteMapping|RequestMapping)\\s*(?:\\(\\s*\"([^\"]*)\"\\s*\\))?");

    public static List<EndpointInfo> parseFile(Path javaFile) {
        List<EndpointInfo> endpoints = new ArrayList<>();
        try {
            String src = new String(Files.readAllBytes(javaFile));
            boolean isController = src.contains("@RestController") || src.contains("@Controller");
            if (!isController) return endpoints;

            String className = null;
            Matcher cm = CLASS_PATTERN.matcher(src);
            if (cm.find()) className = cm.group(1);

            String classPath = null;
            Matcher cpm = REQUEST_MAPPING.matcher(src);
            if (cpm.find()) classPath = cpm.group(1);

            Matcher mm = METHOD_MAPPING.matcher(src);
            while (mm.find()) {
                String annotation = mm.group(1);
                String path = mm.group(2);
                String http = "GET";
                if (annotation.equals("PostMapping")) http = "POST";
                else if (annotation.equals("PutMapping")) http = "PUT";
                else if (annotation.equals("DeleteMapping")) http = "DELETE";
                else if (annotation.equals("RequestMapping")) http = "REQUEST";

                String fullPath = (classPath != null ? classPath : "") + (path != null ? path : "");
                if (fullPath.isEmpty()) fullPath = "/";
                endpoints.add(new EndpointInfo(className, fullPath, http));
            }
        } catch (IOException e) {
            // ignore
        }
        return endpoints;
    }

}
