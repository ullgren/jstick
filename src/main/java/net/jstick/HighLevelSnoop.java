package net.jstick;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import net.jstick.api.Controller;
import net.jstick.api.DeviceChangeEvent;
import net.jstick.api.DeviceChangeEventListner;
import net.jstick.api.DeviceEvent;
import net.jstick.api.DeviceEventListner;
import net.jstick.api.SensorEvent;
import net.jstick.api.SensorEventListner;
import net.jstick.api.Tellstick;
import net.jstick.api.TellstickException;

public class HighLevelSnoop {

	private Tellstick ts = new Tellstick();
	
	private DeviceEventListner deviceEventListner;
	private DeviceChangeEventListner deviceChangeEventListner;
	private SensorEventListner sensorEventListner;
	
	
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

		 deviceEventListner = new DeviceEventListner() {
			DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			 
			@Override
			public void eventRecived(DeviceEvent event) {
				System.out.println(df.format(Calendar.getInstance().getTime()) 
						+ "\tDeviceEvent\tDevice:" + event.getDeviceId() 
						+ "\tMethod: " + event.getMethod()
						+ "\tData: " + event.getData());
			}
		};
		
		deviceChangeEventListner = new DeviceChangeEventListner() {
			DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			
			@Override
			public void eventRecived(DeviceChangeEvent event) {
				System.out.println(df.format(Calendar.getInstance().getTime()) 
						+ "\tDeviceChangeEvent\tDevice:" + event.getDeviceId() 
						+ "\tChangeEvent: " + event.getChangeEvent()
						+ "\tChangeType: " + event.getChangeType());
				
			}
		};
		
		sensorEventListner = new SensorEventListner() {
			DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
			
			@Override
			public void eventRecived(SensorEvent event) {
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

		ts.addDeviceEventListners(deviceEventListner);
		ts.addDeviceChangeEventListners(deviceChangeEventListner);
		ts.addSensorEventListners(sensorEventListner);
			
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
			   if ( deviceEventListner != null ) {
				   ts.removeDeviceEventListners(deviceEventListner);
			   }
			   if ( deviceChangeEventListner != null ) {
				   ts.removeDeviceChangeEventListners(deviceChangeEventListner);
			   }
			   if ( sensorEventListner != null ) {
				   ts.removeSensorEventListners(sensorEventListner);
			   }
		   } catch (TellstickException e ) {
			   System.err.println("Error removing listners " + e.getErrorCode() + " : " + e.getMessage());
		   }
		   ts.close();
	   }
	  });
	  
}
}