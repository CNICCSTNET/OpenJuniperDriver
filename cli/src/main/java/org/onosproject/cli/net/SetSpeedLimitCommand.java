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

@Command(scope = "onos", name = "set-speed-limit",
        description = "Set the speed limit term to a special device" +
                "specified device." +
                "Example:" +
                "set-speed-limit netconf:159.226.101.32:830 testNewFilter 159.226.101.65/32 55 9")
public class SetSpeedLimitCommand extends AbstractShellCommand {
    @Argument(index = 0, name = "uri", description = "Device ID",
            required = true, multiValued = false)
    String uri = null;
    @Argument(index = 1, name = "name", description = "Name of the filter/policer/term",
            required = true, multiValued = false)
    String name = null;
    @Argument(index = 2, name = "ip", description = "ip",
            required = true, multiValued = false)
    String ip = null;
    @Argument(index = 3, name = "speed", description = "The speed for policer",
            required = false, multiValued = false)
    String speed = null;
    @Argument(index = 4, name = "speed-burst", description = "The speed for policer",
            required = false, multiValued = false)
    String speedburst = null;
    /*
    @Argument(index = 1, name = "type", description = "Name of the type that want to set, filter/policer/term",
            required = true, multiValued = false)
    String type = null;
    @Argument(index = 2, name = "name", description = "Name of the special type you set",
            required = true, multiValued = false)
    String name = null;
    @Argument(index = 3, name = "speed-or-ip-or-policer", description = "The speed for policer or ip for term",
            required = false, multiValued = false)
    String speedoriporpolicer = null;
    @Argument(index = 4, name = "burst-speed-or-action", description = "The burst ip for policer or action for term",
            required = false, multiValued = false)
     */
    String burstspeedoratction = null;
    private DeviceId deviceId;

    @Override
    protected void execute() {
        DriverService service = get(DriverService.class);
        deviceId = DeviceId.deviceId(uri);
        DriverHandler h = service.createHandler(deviceId);
        SpeedLimit config = h.behaviour(SpeedLimit.class);
        XmlStructureParserEdit parser = new XmlStructureParserEdit();
        String policerPath = null, policerTypeAndValue = null, policerAction = null;
        String filterPath = null, filterTypeAndValue = null, filterAction = null;
        String termPath = null, termTypeAndValue = null, termAction = null;


        //set the policer
        policerPath = parser.configPolicerPath(name);
        policerTypeAndValue = parser.configPolicerTypeAndvalue(speed, speedburst);
        print(config.setSpeedLimit(policerPath, policerTypeAndValue, policerAction));

        //set the term and filter, default means set the
        // IP for both source and dest address
        filterPath = parser.configFilterPath(name, name);
        filterTypeAndValue = parser.configFilterTypeAndvalue(ip, name, "default");
        print(config.setSpeedLimit(filterPath, filterTypeAndValue, filterAction));

        //add the default term
        termPath = parser.configTermPath(name, "default");
        termTypeAndValue = parser.configTermTypeAndvalue("accept");
        print(config.setSpeedLimit(termPath, termTypeAndValue, termAction));


        /*
        //set the term and filter
        filterPath = parser.configFilterPath(name + "output", name + "output");
        filterTypeAndValue = parser.configFilterTypeAndvalue(ip, name, "dest");
        print(config.setSpeedLimit(filterPath, filterTypeAndValue, filterAction));

        //add the default term
        termPath = parser.configTermPath(name + "output", "default");
        termTypeAndValue = parser.configTermTypeAndvalue("accept");
        print(config.setSpeedLimit(termPath, termTypeAndValue, termAction));
        /*
        if (type.equals("filter")) {
            path = parser.configFilterPath(name, name);
            typeAndValue = parser.configFilterTypeAndvalue(speedoriporpolicer, speedoriporpolicer);
        } else if (type.equals("policer")) {
            path = parser.configPolicerPath(name);
            typeAndValue = parser.configPolicerTypeAndvalue(speedoriporpolicer, burstspeedoratction);
        } else if (type.equals("term")) {
            String[] nameSplit = name.split(":");
            String termName = nameSplit[0], filterName = nameSplit[1], policerName = nameSplit[2];
            path = parser.configTermPath(filterName, termName);
            typeAndValue = parser.configTermTypeAndvalue(speedoriporpolicer, policerName);
            action = parser.configTermAction(burstspeedoratction);
        }*/
    }

}
