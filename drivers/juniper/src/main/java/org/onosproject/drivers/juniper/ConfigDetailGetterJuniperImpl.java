package org.onosproject.drivers.juniper;

import com.google.common.base.Preconditions;
import org.onosproject.net.behaviour.ConfigDetailGetter;
import org.onosproject.net.driver.AbstractHandlerBehaviour;
import org.onosproject.drivers.juniper.tools.GetXmlCreateJuniper;
import org.onosproject.drivers.juniper.tools.XmlCreateJuniper;
import org.onosproject.drivers.juniper.tools.XmlParserJuniper;
import org.onosproject.drivers.utilities.XmlConfigParser;
import org.onosproject.net.driver.DriverHandler;
import org.onosproject.netconf.NetconfController;
import org.onosproject.netconf.NetconfSession;
import org.slf4j.Logger;
import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import static org.slf4j.LoggerFactory.getLogger;

/**
 * Created by zhou on 16-11-5.
 */
public class ConfigDetailGetterJuniperImpl extends AbstractHandlerBehaviour
        implements ConfigDetailGetter {
    private final Logger log = getLogger(getClass());

    @Override
    public String getConfigurationDetail(String cfgType, String path, String par) {
        DriverHandler handler = handler();
        NetconfController controller = handler.get(NetconfController.class);
        Preconditions.checkNotNull(controller, "Netconf controller is null");
        NetconfSession session = controller.getDevicesMap().get(handler()
                .data().deviceId()).getSession();
        XmlCreateJuniper create = new GetXmlCreateJuniper();
        String reply;
        try {
            reply = session.requestSync(create.createGetXml(cfgType, path, par));
        } catch (Exception e) {
            log.error("Failed to retrieve configuration from device {}.",
                    handler().data().deviceId(), e);
            return null;
        }

        return XmlParserJuniper.getReplyXml(XmlConfigParser.loadXml(
                new ByteArrayInputStream(reply.getBytes(StandardCharsets.UTF_8))));

    }

}
