package org.onosproject.drivers.juniper.tools;

import org.apache.commons.configuration.XMLConfiguration;

public interface ParseFilter {

    public void parseGetFilter(XMLConfiguration cfg, String path, String filter);
    void parseEditFilter(XMLConfiguration cfg, String path, String filter);
}
