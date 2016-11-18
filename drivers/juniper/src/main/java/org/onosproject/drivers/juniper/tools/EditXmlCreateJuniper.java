package org.onosproject.drivers.juniper.tools;

import java.io.StringWriter;

import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.XMLConfiguration;

public class EditXmlCreateJuniper {
    private static final String BEGIN = "<rpc xmlns:nc=\"urn:ietf:params:xml:ns:netconf:base:1.0\"";
    private static final String MESSAGE = " message-id=\"1\">";
    private static final String END = "</rpc>]]>]]>";
    public String editConfiguration(String path, String filter, String operation) {
        XMLConfiguration cfg = new XMLConfiguration();
        String parseStringPath = "";
        ParsePath parsePath = new ParsePathImpl();
        ParseFilter parseFilter = new ParseFilterImpl();
        try {
            cfg.load(getClass().getResourceAsStream("/netconfEdit.xml"));
            parseStringPath = parsePath.parsePath(cfg, path, operation);
            parseFilter.parseEditFilter(cfg, parseStringPath, filter);
        } catch (ConfigurationException e) {
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
    public String getCommitXml() {
        StringBuilder commitXml = new StringBuilder();
        commitXml.append(BEGIN + MESSAGE);
        commitXml.append("<commit/>");
        commitXml.append(END);
        return commitXml.toString();
        }
    public String getConfirmedCommit() {
        StringBuilder confirmedCommitXml = new StringBuilder();
        confirmedCommitXml.append(BEGIN + MESSAGE);
        confirmedCommitXml.append("<commit>");
        confirmedCommitXml.append("<confirmed/>");
        confirmedCommitXml.append("</commit>");
        confirmedCommitXml.append(END);
        return confirmedCommitXml.toString();
        }
    //Timeout period for confirmed commit, in seconds. If
    //unspecified, the confirm timeout defaults to 600 seconds
    public String getConfirmedCommit(String timeout)throws Exception {
        Integer t = Integer.parseInt(timeout);
        if (t < 0) {
            throw new IllegalArgumentException("The timeout you entered is invalid!");
            }
        StringBuilder confirmedCommitXml = new StringBuilder();
        confirmedCommitXml.append(BEGIN + MESSAGE);
        confirmedCommitXml.append("<commit>");
        confirmedCommitXml.append("<confirmed/>");
        confirmedCommitXml.append("<confirm-timeout>" + timeout + "</confirm-timeout>");
        confirmedCommitXml.append("</commit>");
        confirmedCommitXml.append(END);
        return confirmedCommitXml.toString();
        }
    public String rollBack() {
        StringBuilder rollBackXml = new StringBuilder();
        rollBackXml.append(BEGIN + MESSAGE);
        rollBackXml.append("<load-configuration rollback=\"1\"/>");
        rollBackXml.append(END);
        return rollBackXml.toString();
        }
    public String rollBack(String versionNum)throws Exception {
        Integer v = Integer.parseInt(versionNum);
        if (v < 0) {
            throw new IllegalArgumentException("rollback num is invalid!");
            }
        StringBuilder rollBackXml = new StringBuilder(BEGIN + MESSAGE);
        rollBackXml.append("<load-configuration rollback=" + versionNum + "/>");
        rollBackXml.append(END);
        return rollBackXml.toString();
        }
    }
