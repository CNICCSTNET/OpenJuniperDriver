package org.onosproject.drivers.juniper;

import org.onosproject.drivers.juniper.tools.EditXmlCreateJuniper;
import org.onosproject.drivers.juniper.tools.XmlParserJuniper;
import org.onosproject.drivers.utilities.XmlConfigParser;
import org.onosproject.net.behaviour.ConfigDetailSetter;
import org.onosproject.net.driver.AbstractHandlerBehaviour;
import org.onosproject.netconf.NetconfController;
import org.onosproject.netconf.NetconfSession;
import org.slf4j.Logger;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static com.google.common.base.Preconditions.checkNotNull;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by zhou on 16-11-5.
 */
public class ConfigDetailSetterJuniperImpl extends AbstractHandlerBehaviour
        implements ConfigDetailSetter {
    private final Logger log = getLogger(getClass());

    @Override
    public String setConfigurationDetail(String path, String typeAndValue, String action) {
        NetconfController controller =
                checkNotNull(handler().get(NetconfController.class));

        NetconfSession session = controller.getDevicesMap().get(handler()
                .data().deviceId()).getSession();
        EditXmlCreateJuniper edit = new EditXmlCreateJuniper();
        String reply;
        try {
            reply = session.requestSync(edit.editConfiguration(path, typeAndValue, action));
        } catch (Exception e) {
            log.error("Failed to retrieve configuration from device {}.",
                    handler().data().deviceId(), e);
            return null;
        }

        return XmlParserJuniper.getReplyXml(XmlConfigParser.loadXml(
                new ByteArrayInputStream(reply.getBytes(StandardCharsets.UTF_8))));

    }

}

