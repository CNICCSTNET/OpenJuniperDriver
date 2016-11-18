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
package org.onosproject.cli.net;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.net.DeviceId;
import org.onosproject.net.behaviour.ConfigDetailGetter;
import org.onosproject.net.driver.DriverHandler;
import org.onosproject.net.driver.DriverService;
import org.onosproject.cli.XmlStructureParserGet;


/**
 * Command that gets the configuration of the specified type from the specified
 * device. If configuration cannot be retrieved it prints an error string.
 *
 * This is a temporary development tool for use until yang integration is complete.
 * This uses a not properly specified behavior. DO NOT USE AS AN EXAMPLE.
 */

//FIXME this should eventually be removed.

@Command(scope = "onos", name = "device-configuration-detail",
        description = "Gets the detail configuration of the specified type from the" +
                "specified device." +
                "Example:" +
                "device-configuration-detail netconf:159.226.101.32:830 running firewall:policer name" +
                "device-configuration-detail netconf:159.226.101.32:830 running interface name")
public class DeviceConfigDetailGetterCommand extends AbstractShellCommand {

    @Argument(index = 0, name = "uri", description = "Device ID",
            required = true, multiValued = false)
    String uri = null;
    @Argument(index = 1, name = "cfgType", description = "Configuration type",
            required = true, multiValued = false)
    String cfgType = null;
    @Argument(index = 2, name = "firstLevel", description = "The first lever of the configuration",
            required = true, multiValued = false)
    String firstLevel = null;
    @Argument(index = 3, name = "firstLevelType", description = "The value of the first lever",
            required = false, multiValued = false)
    String firstLevelType = null;
    private DeviceId deviceId;

    @Override
    protected void execute() {
        DriverService service = get(DriverService.class);
        deviceId = DeviceId.deviceId(uri);
        DriverHandler h = service.createHandler(deviceId);
        ConfigDetailGetter config = h.behaviour(ConfigDetailGetter.class);
        String path = null;
        XmlStructureParserGet parser = new XmlStructureParserGet();
        path = parser.getXmlStructure(firstLevel);
        print(config.getConfigurationDetail(cfgType, path, firstLevelType));
    }

}
