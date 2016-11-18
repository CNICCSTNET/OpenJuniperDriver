package org.onosproject.drivers.juniper.tools;

import java.io.StringWriter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;


public class GetXmlCreateJuniper implements XmlCreateJuniper {
    static final String SOURCEPATH = "get-config.source.";
    @Override
    public String createGetXml(String cfyTypt, String path, String filter)throws Exception {
        XMLConfiguration cfg = new XMLConfiguration();
        String parseStringPath = "";
        ParsePath parsePath = new ParsePathImpl();
        ParseFilter parseFilter = new ParseFilterImpl();
        try {
            cfg.load(getClass().getResourceAsStream("/netconfGet.xml"));
            if (cfyTypt.equals("running") || cfyTypt.equals("candidate")) {
                cfg.addProperty(SOURCEPATH + cfyTypt, "");
                } else {
                    throw new IllegalArgumentException("The cfyType you enter is invalid!");
                    }
            parseStringPath = parsePath.parsePath(cfg, path, null);
            parseFilter.parseGetFilter(cfg, parseStringPath, filter);
            } catch (Exception e) {
                throw new IllegalArgumentException("Cannot load configuration xml", e);
                }
        StringWriter stringWriter = new StringWriter();
        try {
            cfg.save(stringWriter);
            } catch (ConfigurationException e) {
                System.out.println(e.getMessage());
                }
        String s = stringWriter.toString();
        return s + "]]>]]>";
        }
    }
