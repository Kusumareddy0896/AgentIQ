package com.opsbridge.r18a6.service;

import com.opsbridge.r18a6.model.ApplicationStructure;

public class ApplicationStructureService {

    public ApplicationStructure analyze(String repoPath) {
        // TODO: wire together scanner/parsers to build structure
        com.opsbridge.r18a6.parser.JavaApplicationParser parser = new com.opsbridge.r18a6.parser.JavaApplicationParser();
        return parser.parse(repoPath);
    }

}
