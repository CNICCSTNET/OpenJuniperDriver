/*
 * Copyright 2016-present Open Networking Laboratory
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.onosproject.drivers.juniper.tools;


import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.HierarchicalConfiguration;
import org.apache.commons.configuration.XMLConfiguration;

import java.io.StringWriter;

/**
 * Parser for Netconf XML configurations and replies from Cisco devices.
 */
public final class XmlParserJuniper {

    private XmlParserJuniper() {
        // Not to be called.
    }
    public static String getReplyXml(HierarchicalConfiguration cfg) {
        StringWriter stringWriter = new StringWriter();
        try {
            XMLConfiguration xcfg = (XMLConfiguration) cfg;
            xcfg.save(stringWriter);
            } catch (ConfigurationException e) {
                System.out.println(e.getMessage());
                }
        String s = stringWriter.toString();
        return s;
    }
}
