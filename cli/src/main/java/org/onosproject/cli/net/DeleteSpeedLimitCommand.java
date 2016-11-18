package org.onosproject.cli.net;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.cli.XmlStructureParserEdit;
import org.onosproject.net.DeviceId;
import org.onosproject.net.behaviour.SpeedLimit;
import org.onosproject.net.driver.DriverHandler;
import org.onosproject.net.driver.DriverService;

/**
 * Created by zhou on 16-11-10.
 *
 * Command that set the speed limit term to a device
 *
 * This is a temporary development tool for use until yang integration is complete.
 * This uses a not properly specified behavior. DO NOT USE AS AN EXAMPLE.
 */

@Command(scope = "onos", name = "delete-speed-limit",
        description = "Set the speed limit term to a special device" +
                "specified device." +
                "Example:" +
                "delete-speed-limit netconf:159.226.101.32:830 policer testNewFilter" +
                " delete-speed-limit netconf:159.226.101.32:830 term testNewFilter:testNewFilter" +
                "delete-speed-limit netconf:159.226.101.32:830 filter myTestZhou")
public class DeleteSpeedLimitCommand extends AbstractShellCommand {
    @Argument(index = 0, name = "uri", description = "Device ID",
            required = true, multiValued = false)
    String uri = null;
    @Argument(index = 1, name = "type", description = "Name of the type that want to set, filter/policer/term",
            required = true, multiValued = false)
    String type = null;
    @Argument(index = 2, name = "name", description = "Name of the special type you set",
            required = true, multiValued = false)
    String name = null;
    private DeviceId deviceId;

    @Override
    protected void execute() {
        DriverService service = get(DriverService.class);
        deviceId = DeviceId.deviceId(uri);
        DriverHandler h = service.createHandler(deviceId);
        SpeedLimit config = h.behaviour(SpeedLimit.class);
        XmlStructureParserEdit parser = new XmlStructureParserEdit();
        String path = null, typeAndValue = null, action = null;
        if (type.equals("filter")) {
            path = parser.configFilterPath(name);
        } else if (type.equals("policer")) {
            path = parser.configPolicerPath(name);
        } else if (type.equals("term")) {
            String[] nameSplit = name.split(":");
            String termName = nameSplit[0], filterName = nameSplit[1];
            path = parser.configTermPath(filterName, termName);
        }
        action = parser.configAction("delete");
        print(config.deleteSpeedLimit(path, typeAndValue, action));
    }

}
