package org.onosproject.cli.net;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.cli.XmlStructureParserEdit;
import org.onosproject.net.DeviceId;
import org.onosproject.net.behaviour.ConfigDetailSetter;
import org.onosproject.net.driver.DriverHandler;
import org.onosproject.net.driver.DriverService;

/**
 * Command that set the detail configuration of the specified type from the specified
 * device.
 *
 * This is a temporary development tool for use until yang integration is complete.
 * This uses a not properly specified behavior. DO NOT USE AS AN EXAMPLE.
 */

//FIXME this should eventually be removed.

@Command(scope = "onos", name = "device-set-configuration-detail",
        description = "Gets the detail configuration of the specified type from the" +
                "specified device." +
                " Example:" +
                "device-set-configuration-detail netconf:159.226.101.32:830 user@qiuxinyi uid:1990" +
                "device-set-configuration-detail netconf:159.226.101.32:830 system domain-name:mySDN.net1")
public class DeviceConfigDetailSetterCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "uri", description = "Device ID",
            required = true, multiValued = false)
    String uri = null;
    @Argument(index = 1, name = "firstLevel", description = "The first lever of the configuration",
            required = true, multiValued = false)
    String firstLevel = null;
    @Argument(index = 2, name = "typeAndValue", description = "",
            required = true, multiValued = false)
    String typeAndValue = null;
    @Argument(index = 3, name = "action", description = "action",
            required = false, multiValued = false)
    String action = null;
    private DeviceId deviceId;

    @Override
    protected void execute() {
        DriverService service = get(DriverService.class);
        deviceId = DeviceId.deviceId(uri);
        DriverHandler h = service.createHandler(deviceId);
        ConfigDetailSetter config = h.behaviour(ConfigDetailSetter.class);
        String path = null;
        XmlStructureParserEdit parser = new XmlStructureParserEdit();
        path = parser.getXmlStructure(firstLevel);
        if (action != null) {
            action = parser.configAction(action);
        }
        print(config.setConfigurationDetail(path, typeAndValue, action));
    }
}

