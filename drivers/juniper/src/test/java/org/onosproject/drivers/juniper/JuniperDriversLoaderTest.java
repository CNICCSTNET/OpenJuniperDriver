package org.onosproject.drivers.juniper;

import org.junit.Before;
import org.onosproject.net.driver.AbstractDriverLoaderTest;


/**
 * Cisco drivers loader test.
 */
public class JuniperDriversLoaderTest extends AbstractDriverLoaderTest {

    @Before
    public void setUp() {
        loader = new JuniperDriversLoader();
    }
}
