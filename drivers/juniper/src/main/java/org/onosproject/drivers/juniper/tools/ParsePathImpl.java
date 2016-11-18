package org.onosproject.drivers.juniper.tools;

import org.apache.commons.configuration.XMLConfiguration;

public class ParsePathImpl implements ParsePath {
    @Override
    public String parsePath(XMLConfiguration xcfg, String path) {
        String[]pathList = path.trim().split(";");
        StringBuilder paths = new StringBuilder();
        boolean isAdd = false;
        for (String p:pathList) {
            isAdd = false;
            if (p.contains("[")) {
                p = p.replace('[', ';');
                p = p.replace(']', ';');
                String[] pList = p.split(";");
                paths.append("." + pList[0]);
                String[] nAndV = pList[1].replace('@', ' ').trim().split("=");
                String tempPath = paths.toString() + "." + nAndV[0];
                xcfg.addProperty(tempPath, nAndV[1]);
                isAdd = true;
                } else {
                    paths.append("." + p);
                    }
            }
        if (!isAdd) {
            xcfg.addProperty(paths.toString(), "");
            }
        return paths.toString();
        }
    @Override
    public String parsePath(XMLConfiguration cfg, String path, String operation) {
        String[]pathList = path.trim().split(";");
        StringBuilder paths = new StringBuilder();
        boolean isAdd = false;
        for (String p:pathList) {
            isAdd = false;
            if (p.contains("[")) {
                p = p.replace('[', ';');
                p = p.replace(']', ';');
                String[] pList = p.split(";");
                paths.append("." + pList[0]);
                String[] nAndV = pList[1].replace('@', ' ').trim().split("=");
                String tempPath = paths.toString() + "." + nAndV[0];
                cfg.addProperty(tempPath, nAndV[1]);
                isAdd = true;
                } else {
                    paths.append("." + p);
                    }
            }
        if (!isAdd) {
            cfg.addProperty(paths.toString(), "");
            }
        if (operation != null) {
            addAttribute(cfg, paths, operation);
            }
        return paths.toString();
        }
    public void addAttribute(XMLConfiguration cfg, StringBuilder path, String operation) {
        String[]operationList = operation.trim().split(";");
        for (String op:operationList) {
            String tempPath = path.toString();
            tempPath = tempPath + "[";
            String[]nAndV = op.trim().split("#");
            tempPath = tempPath + ("@" + nAndV[0] + "]");
            cfg.addProperty(tempPath.toString(), nAndV[1]);
            }
        }
    }
