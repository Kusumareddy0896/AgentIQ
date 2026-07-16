package com.opsbridge.r18a6.parser;

import com.opsbridge.r18a6.model.ServiceInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class SpringServiceParser {

    public static List<ServiceInfo> parseFile(Path javaFile) {
        List<ServiceInfo> services = new ArrayList<>();
        try {
            String src = new String(Files.readAllBytes(javaFile));
            boolean isService = src.contains("@Service") || src.contains("@Component") || src.contains("@Repository");
            if (!isService) return services;

            // crude class name extraction
            int idx = src.indexOf("class ");
            if (idx >= 0) {
                String remainder = src.substring(idx + 6);
                String[] parts = remainder.split("\\s+|\\{", 2);
                if (parts.length > 0) {
                    ServiceInfo s = new ServiceInfo();
                    s.setClassName(parts[0].trim());
                    services.add(s);
                }
            }
        } catch (IOException e) {
            // ignore
        }
        return services;
    }

}
