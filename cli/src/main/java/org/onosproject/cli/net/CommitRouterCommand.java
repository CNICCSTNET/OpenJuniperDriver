package org.onosproject.cli.net;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.onosproject.cli.AbstractShellCommand;
import org.onosproject.net.DeviceId;
import org.onosproject.net.behaviour.CommitRouter;
import org.onosproject.net.driver.DriverHandler;
import org.onosproject.net.driver.DriverService;

/**
 * Created by zhou on 16-11-11.
 */
@Command(scope = "onos", name = "commit-router",
        description = "Commit or rollback the router." +
                "Example:" +
                "commit-router netconf:159.226.101.32:830 rollback 5" +
                "commit-router netconf:159.226.101.32:830 commit 10")
public class CommitRouterCommand extends AbstractShellCommand {
    @Argument(index = 0, name = "uri", description = "Device ID",
            required = true, multiValued = false)
    String uri = null;
    @Argument(index = 1, name = "type", description = "commit or rollback",
            required = true, multiValued = false)
    String type = null;
    @Argument(index = 2, name = "par", description = "parameter to commit," +
            " now without rollback or rollback sometimes later",
            required = false, multiValued = false)
    String par = null;
    //@Option(name = "-h", aliases = "--hour", description = "when to make the commit action",
    //       required = false, multiValued = true)
    //String[] hour;
    //@Option(name = "-r", aliases = "--rollback", description = "when to make the commit action",
    //        required = false, multiValued = true)
    //String[] rollback;
    private DeviceId deviceId;

    @Override
    protected void execute() {
        DriverService service = get(DriverService.class);
        deviceId = DeviceId.deviceId(uri);
        DriverHandler h = service.createHandler(deviceId);
        CommitRouter config = h.behaviour(CommitRouter.class);
        if (type.equals("commit")) {
            if (par.equals("now")) {
                try {
                    print(config.confirmedCommit());
                } catch (Exception e) {
                    log.error("Failed to commit the router.", e);
                }
            } else {
                try {
                    print(config.confirmedCommit(par));
                } catch (Exception e) {
                    log.error("Failed to commit the router.", e);
                }
            }
        } else if (type.equals("rollback")) {
            if (par == null) {
                try {
                    print(config.rollBack());
                } catch (Exception e) {
                    log.error("Failed to roll back the router.", e);
                }
            } else {
                try {
                    print(config.rollBack(par));
                } catch (Exception e) {
                    log.error("Failed to roll back the router.", e);
                }
            }
        }
    }

}

