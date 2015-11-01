package net.jstick;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import net.jstick.api.Controller;
import net.jstick.api.RawEvent;
import net.jstick.api.RawEventListener;
import net.jstick.api.Tellstick;
import net.jstick.api.TellstickException;

public class Snoop {

	private Tellstick ts = new Tellstick();
	
	private RawEventListener rawEventListener;
	
	
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

		rawEventListener = new RawEventListener() {
			DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");

			//@Override
			public void eventReceived(RawEvent event) {
			    System.out.println(df.format(Calendar.getInstance().getTime()) + "\tCtrl:" + event.getControllerId() + "\tData: " + event.getData());
			}
		};
		
		ts.addRawEventListener(rawEventListener);
			
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
		   if ( rawEventListener != null ) {
			   try {
				ts.removeRawEventListener(rawEventListener);
			} catch (TellstickException e) {
				System.err.println("Error removing rawEventListener" + e.getErrorCode() + " : " + e.getMessage());
			}
		   }
		   ts.close();
	   }
	  });
	  
}
}