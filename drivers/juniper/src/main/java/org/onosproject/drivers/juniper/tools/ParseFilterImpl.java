package org.onosproject.drivers.juniper.tools;


import org.apache.commons.configuration.XMLConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ParseFilterImpl implements ParseFilter {

    public List<String> getFilterAndNameXml(String filter, String name)throws Exception {
        if (filter == null || name == null || filter.isEmpty() || name.isEmpty()) {
            Exception e = new Exception("enter null value!!");
            throw e;
            }
        List<String> filterAndNameXml = new ArrayList<String>();
        String begin = "<" + filter + ">" + "<name>" + name + "</name>";
        String end = "</" + filter + ">";
        filterAndNameXml.add(begin);
        filterAndNameXml.add(end);
        return filterAndNameXml;
        }
    @Override
    public void parseEditFilter(XMLConfiguration cfg, String path, String filter) {
        if (filter == null) {
            return;
            }
        String[] filterList = filter.trim().split(";");
        for (String fAndv:filterList) {
            String tempPath = path;
            String[]fAndvList = fAndv.trim().split(":");
            if (fAndvList[0].contains("/")) {
                fAndvList[0] = fAndvList[0].replace('/', '.');
                }
            tempPath = tempPath + "." + fAndvList[0];
            if (fAndvList.length > 1) {
                cfg.addProperty(tempPath, fAndvList[1]);
            } else {
                cfg.addProperty(tempPath, "");
            }
            }
        }
    public void parseGetFilter(XMLConfiguration cfg, String path, String filter) {
        if (filter == null) {
            return;
            }
        String[] filterList = filter.trim().split(";");
        for (String fAndv:filterList) {
            String tempPath = path;
            String fAndvList = fAndv.trim();
            if (fAndvList.contains("/")) {
                fAndvList = fAndvList.replace('/', '.');
                }
            tempPath = tempPath + "." + fAndvList;
            cfg.addProperty(tempPath, "");
            }
        }
    }
