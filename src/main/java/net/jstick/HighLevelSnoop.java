package net.jstick;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import net.jstick.api.Controller;
import net.jstick.api.DeviceChangeEvent;
import net.jstick.api.DeviceChangeEventListener;
import net.jstick.api.DeviceEvent;
import net.jstick.api.DeviceEventListener;
import net.jstick.api.SensorEvent;
import net.jstick.api.SensorEventListener;
import net.jstick.api.Tellstick;
import net.jstick.api.TellstickException;

public class HighLevelSnoop {

	private Tellstick ts = new Tellstick();
	
	private DeviceEventListener deviceEventListener;
	private DeviceChangeEventListener deviceChangeEventListener;
	private SensorEventListener sensorEventListener;
	
	
	public static void main(String[] args) {
		
		HighLevelSnoop app = new HighLevelSnoop();
		app.attachShutDownHook();
		app.startSnoop();
		
		
	}
	
	public void startSnoop(){	
		System.out.println("Snooping for raw commands on the following controllers:");
		ArrayList<Controller> list =   ts.getConnectedControllers();
		 for (Controller c: (ArrayList<Controller>) list){
			 System.out.println("ID: " + c.getId() +  "\tName: " + c.getName() + "\tType: " + c.getTypeString() + "\tSerial: "  + c.getSerial() + "\n");
		 }

		 deviceEventListener = new DeviceEventListener() {
			DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			 
			//@Override
			public void eventReceived(DeviceEvent event) {
				System.out.println(df.format(Calendar.getInstance().getTime()) 
						+ "\tDeviceEvent\tDevice:" + event.getDeviceId() 
						+ "\tMethod: " + event.getMethod()
						+ "\tData: " + event.getData());
			}
		};
		
		deviceChangeEventListener = new DeviceChangeEventListener() {
			DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			
			//@Override
			public void eventReceived(DeviceChangeEvent event) {
				System.out.println(df.format(Calendar.getInstance().getTime()) 
						+ "\tDeviceChangeEvent\tDevice:" + event.getDeviceId() 
						+ "\tChangeEvent: " + event.getChangeEvent()
						+ "\tChangeType: " + event.getChangeType());
				
			}
		};
		
		sensorEventListener = new SensorEventListener() {
			DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			
			//@Override
			public void eventReceived(SensorEvent event) {
				// String protocol, String model, int id, int dataType,
				// String value, int timestamp
				System.out.println(df.format(Calendar.getInstance().getTime()) 
						+ "\tSensorEvent\tSensor:" + event.getId() 
						+ "\tProtocol: " + event.getProtocol()
						+ "\tModel: " + event.getModel()
						+ "\tDataType: " + event.getDataType()
						+ "\tValue: " + event.getValue()
						+ "\tTimestamp: " + df.format(event.getTimestamp())
						);
				
			}
		}; 

		ts.addDeviceEventListener(deviceEventListener);
		ts.addDeviceChangeEventListener(deviceChangeEventListener);
		ts.addSensorEventListener(sensorEventListener);
			
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
		   try {
			   if ( deviceEventListener != null ) {
				   ts.removeDeviceEventListener(deviceEventListener);
			   }
			   if ( deviceChangeEventListener != null ) {
				   ts.removeDeviceChangeEventListener(deviceChangeEventListener);
			   }
			   if ( sensorEventListener != null ) {
				   ts.removeSensorEventListener(sensorEventListener);
			   }
		   } catch (TellstickException e ) {
			   System.err.println("Error removing listeners " + e.getErrorCode() + " : " + e.getMessage());
		   }
		   ts.close();
	   }
	  });
	  
}
}