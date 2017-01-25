package org.onosproject.cli;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;


/**
 * Created by zhou on 16-11-12.
 */
public class XmlStructureParser {
    static final String ACTION1 = "nc:operation#";
    static final String ACTION2 = "yang:insert#";
    static final String ACTION3 = "yang:key#[name='";
    static final String XMLFILEPATH = "/juniper.xml";

    public XmlStructureParser() {
    }

    String shortestPath;

    public int stringNum(String action, String split) {
        int cnt = 1;
        int offset = 0;
        while ((offset = action.indexOf(split, offset)) != -1) {
            offset = offset + 1;
            cnt++;
        }
        return cnt;
    }

    public boolean parseChild(Node node, String path, String lookName) {
        if (null == node) {
            return false;
        }
        if (null != path && !path.equals("")) {
            path = path + ";" + node.getNodeName();
        } else {
            path = node.getNodeName();
        }

        //find the shortestPath
        String[] nameLevel = lookName.split(":");
        int nameNum = stringNum(lookName, ":");
        boolean containAll = true;
        for (int i = 0; i < nameNum; i++) {
            if (!path.contains(nameLevel[i])) {
                containAll = false;
            }
        }
        if (containAll && (path.endsWith(nameLevel[nameNum - 1]))) {
            if (shortestPath == null) {
                shortestPath = path;
            } else if (stringNum(path, ";") < stringNum(shortestPath, ";")) {
                shortestPath = path;
            }
        }

        NodeList nodelist = node.getChildNodes();
        for (int i = 0; i < nodelist.getLength(); i++) {
            Node child = (Node) nodelist.item(i);
            parseChild(child, path, lookName);
        }
        return true;
    }

    public String getPath(String lookName) {
        Document document;
        try {
            document = DocumentBuilderFactory.newInstance().newDocumentBuilder().
                    parse(getClass().getResourceAsStream(XMLFILEPATH));
            Node mapTag = document.getChildNodes().item(0);
            parseChild(mapTag, null, lookName);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        return shortestPath;
    }

    public String configAction(String action, String split) {
        if (action == null) {
            return null;
        }
        int counter = stringNum(action, split);
        String xmlAction = null;
        String[] actionSplit = null;
        if (counter == 1) {
            xmlAction = ACTION1 + action;
        } else if (counter == 2) {
            actionSplit = action.split(":");
            xmlAction = ACTION1 + actionSplit[0] + ";" + ACTION2 + actionSplit[1];
        } else if (counter == 3) {
            actionSplit = action.split(":");
            xmlAction = ACTION1 + actionSplit[0] +
                    ";" + ACTION2 + actionSplit[1] +
                    ";" + ACTION3 + actionSplit[2] + "']";
        }
        return xmlAction;
    }

}
