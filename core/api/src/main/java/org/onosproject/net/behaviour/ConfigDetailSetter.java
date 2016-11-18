package org.onosproject.net.behaviour;

import org.onosproject.net.driver.HandlerBehaviour;

/**
 * Behaviour that sets the detail configuration from the device.
 *
 * This is a temporary development tool for use until yang integration is complete.
 * This is not a properly specified behavior. DO NOT USE AS AN EXAMPLE.
 */
//FIXME this should eventually be removed.
public interface ConfigDetailSetter extends HandlerBehaviour {

    /**
     * Returns the string representation of a device configuration, returns a
     * failure string if the configuration cannot be retrieved.
     * @param path the path of which level at the configuration
     * @param par the parameter which the path point to
     */
    String setConfigurationDetail(String path, String par, String action);
}
