package org.onosproject.drivers.juniper.tools;

import org.apache.commons.configuration.XMLConfiguration;

public interface ParsePath {
    public String parsePath(XMLConfiguration xcfg, String path);
    public String parsePath(XMLConfiguration cfg, String path, String operation);
}
