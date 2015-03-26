package net.jstick;

import java.util.ArrayList;

import net.jstick.api.Device;
import net.jstick.api.Tellstick;

public class TellDemo {
	
	
	public static void main(String[] args) {
		
		// Get a Tellstick object and pass boolean true as argument to enable debugging
		Tellstick ts = new Tellstick(true);
		
		// Get the number of configured devices
		int intNumberOfDevices = ts.getNumberOfDevices();
		System.out.println("There is currently " + intNumberOfDevices + " devices configured in tellstick.conf\n");
		
		// Let's get the name of all devices
		 for (int i = 0; i < intNumberOfDevices; i++) {
			   int id = ts.getDeviceId( i );
			   String name = ts.getName(id);
			   System.out.println("Name: " + name);
			 }
		 
		 // Add some spacing between the output
		 System.out.println();
		 
		 // Let's get the name of all devices but in a even simpler way
		 ArrayList<Device> list = ts.getDevices();
			for (Device dev : list){
				 System.out.println("Name: " + dev.getName());
				
			}

		// What's the latest command the Tellstick registered on device with id 1
		System.out.println("\n\nLast command sent to \"" + ts.getName(1) + "\" - " + ts.getLastCmd(1));
		
		// Lets rename device with id 1
		System.out.println("\nName before we start " + ts.getName(1));
		if(ts.setName(1, "Plug 1 - TV Lamp")){
			System.out.println("Change of name was successful, the name for device 1 is now " + ts.getName(1));
		} else {
			System.out.println("Change of name failed and returned false");
		}
		
		// What commands do the device support?
		int supportedCommands = ts.getSupportedMethods(14);
		System.out.println("\nSupport code: " + supportedCommands + "\n");
		
		// Let's sent OFF command to device with id 1
		int status = ts.sendCmd(1, "OFF");
		System.out.println("So did it work? The return message was \"" + ts.getErrorString(status) + "\"");
		
		

		// Close the communication to Tellstick to allow GC
		ts.close();
	}

}
