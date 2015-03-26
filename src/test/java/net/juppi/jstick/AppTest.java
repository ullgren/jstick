package net.juppi.jstick;

import net.jstick.api.DeviceConfig;
import net.jstick.api.Tellstick;
import net.jstick.api.TellstickLibrary;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;



/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Test cases
     */
    public void testApp()
    {
        //assertTrue( true );
        assertTrue(addDevice());
        assertTrue(delDevice());
    }
    
    boolean addDevice(){
    	
    	Tellstick ts = new Tellstick();
    	 DeviceConfig devCfg = new DeviceConfig();
		 devCfg.setModel("selflearning-switch");
		 devCfg.setName("TestDevice");
		 devCfg.setParam("house", "123456");
		 devCfg.setParam("unit", "1");
		 devCfg.setProtocol("arctech");
		 if(!(ts.addDevice(devCfg) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS)){
			 return false;
		 }
    	return true;
    }
    
    boolean delDevice(){
    	
    	Tellstick ts = new Tellstick();
    	int id = ts.getDeviceIdByName("TestDevice");
    	 if(!(ts.removeDevice(id))){
			 return false;
		 }
    	return true;
    }
}
