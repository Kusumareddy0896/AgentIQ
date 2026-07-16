package com.opsbridge.r18a6.parser;

import com.opsbridge.r18a6.model.DependencyInfo;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MavenParser {

    public static List<DependencyInfo> parsePom(File pomFile) {
        List<DependencyInfo> deps = new ArrayList<>();
        try {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(pomFile);
            doc.getDocumentElement().normalize();

            NodeList dependencyNodes = doc.getElementsByTagName("dependency");
            for (int i = 0; i < dependencyNodes.getLength(); i++) {
                Node n = dependencyNodes.item(i);
                if (n.getNodeType() == Node.ELEMENT_NODE) {
                    Element e = (Element) n;
                    String groupId = getTagValue(e, "groupId");
                    String artifactId = getTagValue(e, "artifactId");
                    String version = getTagValue(e, "version");
                    deps.add(new DependencyInfo(groupId, artifactId, version));
                }
            }
        } catch (Exception ex) {
            // ignore parse errors for now
        }
        return deps;
    }

    private static String getTagValue(Element parent, String tag) {
        NodeList nodes = parent.getElementsByTagName(tag);
        if (nodes.getLength() == 0) return null;
        Node n = nodes.item(0);
        return n != null ? n.getTextContent().trim() : null;
    }

}
