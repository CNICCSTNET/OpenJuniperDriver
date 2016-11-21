This is a ONOS driver for Juniper router.

The code can be divided into three part:

1 The driver. The most important part which compose and parse the XML, and send the XML
to router.

2 The cli. Build the command which let the users pass parameters to driver.

3 New interfaces. Build some new interfaces in the core. In driver, we implement the interfaces.

Just put the code in corresponding folder of ONOS, and then execute “mvn clean install” you can use it.

The version of ONOS we use is 1.8.0, and the juniper router is M10i, Junos 15.1.
