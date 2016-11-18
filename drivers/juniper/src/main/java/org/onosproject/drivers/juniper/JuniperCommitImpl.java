package org.onosproject.drivers.juniper;

import org.onosproject.drivers.juniper.tools.EditXmlCreateJuniper;
import org.onosproject.drivers.juniper.tools.XmlParserJuniper;
import org.onosproject.net.behaviour.CommitRouter;
import org.onosproject.net.driver.AbstractHandlerBehaviour;
import org.onosproject.drivers.utilities.XmlConfigParser;
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
public class JuniperCommitImpl extends AbstractHandlerBehaviour
        implements CommitRouter {
    private final Logger log = getLogger(getClass());

    public String sendCommit(String path) {
        NetconfController controller =
                checkNotNull(handler().get(NetconfController.class));
        NetconfSession session = controller.getDevicesMap().get(handler()
                .data().deviceId()).getSession();
        String reply;
        try {
            reply = session.requestSync(path);
        } catch (Exception e) {

            log.error("Failed to retrieve configuration from device {}.",
                    handler().data().deviceId(), e);
            return null;
        }

        return XmlParserJuniper.getReplyXml(XmlConfigParser.loadXml(
                new ByteArrayInputStream(reply.getBytes(StandardCharsets.UTF_8))));
    }

    @Override
    public String confirmedCommit() throws Exception {
        EditXmlCreateJuniper edit = new EditXmlCreateJuniper();
        return sendCommit(edit.getCommitXml());
    }

    @Override
    public String confirmedCommit(String timeout) throws Exception {
        EditXmlCreateJuniper edit = new EditXmlCreateJuniper();
        if (timeout.equals("confirmed")) {
            return sendCommit(edit.getConfirmedCommit());
        } else {
            return sendCommit(edit.getConfirmedCommit(timeout));
        }
    }

    @Override
    public String rollBack() throws Exception {
        EditXmlCreateJuniper edit = new EditXmlCreateJuniper();
        return sendCommit(edit.rollBack());
    }

    @Override
    public String rollBack(String versionNum) throws Exception {
        EditXmlCreateJuniper edit = new EditXmlCreateJuniper();
        return sendCommit(edit.rollBack(versionNum));
    }

}