
package org.onosproject.net.behaviour;

import org.onosproject.net.driver.HandlerBehaviour;

/**
 * Behaviour that gets the detail configuration from the device.
 *
 * This is a temporary development tool for use until yang integration is complete.
 * This is not a properly specified behavior. DO NOT USE AS AN EXAMPLE.
 */
//FIXME this should eventually be removed.
public interface ConfigDetailGetter extends HandlerBehaviour {

    /**
     * Returns the string representation of a device configuration, returns a
     * failure string if the configuration cannot be retrieved.
     * @param cfgType type of configuration to get (i.e. running).
     * @param path the path of which level at the configuration
     * @param par the parameter which the path point to
     */
    String getConfigurationDetail(String cfgType, String path, String par);
}
