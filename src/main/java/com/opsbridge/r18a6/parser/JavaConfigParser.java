package com.opsbridge.r18a6.parser;

import com.opsbridge.r18a6.model.ConfigKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.Properties;

public class JavaConfigParser {

    public static List<ConfigKey> parseYaml(Path yamlFile) {
        List<ConfigKey> keys = new ArrayList<>();
        try (BufferedReader r = Files.newBufferedReader(yamlFile)) {
            Deque<String> parents = new ArrayDeque<>();
            String line;
            int[] indents = new int[100];
            while ((line = r.readLine()) != null) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) continue;
                int indent = line.indexOf(trimmed);
                // key: value or key:
                int colon = trimmed.indexOf(':');
                if (colon <= 0) continue;
                String key = trimmed.substring(0, colon).trim();
                // maintain parent stack based on indent
                while (!parents.isEmpty() && indent <= indents[parents.size()-1]) {
                    parents.removeLast();
                }
                parents.addLast(key);
                indents[parents.size()-1] = indent;
                String full = String.join(".", parents);
                keys.add(new ConfigKey(full));
                // if line ends with ':' it means nested block continues
                if (!trimmed.endsWith(":")) {
                    // value present, but we still recorded key only
                }
            }
        } catch (IOException e) {
            // ignore
        }
        return keys;
    }

    public static List<ConfigKey> parseProperties(Path propFile) {
        List<ConfigKey> keys = new ArrayList<>();
        try {
            Properties p = new Properties();
            p.load(Files.newInputStream(propFile));
            for (Object k : p.keySet()) {
                keys.add(new ConfigKey(k.toString()));
            }
        } catch (IOException e) {
            // ignore
        }
        return keys;
    }

}
