package net.jstick;

import java.util.ArrayList;

import net.jstick.api.Controller;
import net.jstick.api.Tellstick;

public class Snoop {

	private Tellstick ts = new Tellstick();
	
	
	public static void main(String[] args) {
		
		Snoop app = new Snoop();
		app.attachShutDownHook();
		app.snoopRaw();
		
		
	}
	
	public void snoopRaw(){	
		System.out.println("Snooping for raw commands on the following controllers:");
		ArrayList<Controller> list =   ts.getConnectedControllers();
		 for (Controller c: (ArrayList<Controller>) list){
			 System.out.println("ID: " + c.getId() +  "\tName: " + c.getName() + "\tType: " + c.getTypeString() + "\tSerial: "  + c.getSerial() + "\n");
		 }

		ts.listenRaw();
			
		// Do not exit, keep listening
		while (true) {
			
			try {
				Thread.sleep(100); // 100 ms
			} catch (InterruptedException e) {
				e.printStackTrace();
				}
		}
	}

public void attachShutDownHook(){
	  Runtime.getRuntime().addShutdownHook(new Thread() {
	   @Override
	   public void run() {
		   System.out.println("\n Closing connection to controller...");
	    ts.close();
	   }
	  });
	  
}
}