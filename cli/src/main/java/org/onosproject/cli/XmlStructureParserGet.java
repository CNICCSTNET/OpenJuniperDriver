package org.onosproject.cli;

/**
 * Created by zhou on 16-11-12.
 */
public class XmlStructureParserGet {
    static final String PRE = "get-config;filter;";
    static final String SPLIT = ":";

    public String getXmlStructure(String firstLevel) {
        XmlStructureParser config = new XmlStructureParser();
        return PRE + config.getPath(firstLevel);
    }

    public String configAction(String action) {
        XmlStructureParser config = new XmlStructureParser();
        return config.configAction(action, SPLIT);
    }

}
