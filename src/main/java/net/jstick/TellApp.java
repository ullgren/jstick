package net.jstick;

import java.util.ArrayList;

import net.jstick.api.Controller;
import net.jstick.api.Device;
import net.jstick.api.Sensor;
import net.jstick.api.Tellstick;


public class TellApp {

	private Tellstick ts = new Tellstick();

	
	
	public static void main(String[] args) {
		TellApp app = new TellApp();
		app.printVersion();
		//app.printDeviceStatus();
		app.printExtendedDeviceStatus();
		app.printSensorStatus();
		app.printControllerStatus();
		app.ts.close();
	}

	public void printSensorStatus(){
		System.out.println("\nSensors:");
		ArrayList<Sensor> lista =   ts.getSensors();
		 for (Sensor s: (ArrayList<Sensor>) lista){
			 System.out.println(s.getId() +  "\t" + s.getProtocol() + "\t\t" + s.getModel() +"\t\t" + s.getDataTypes() +
					 "\t" + s.getTemperature() + "\t" + s.getHumidity() + "\t\t" + s.getTimeStampString() + "\t" + s.getTimeStampAge());
		 }
	}
	
	public void printDeviceStatus(){
		System.out.println("\nDevices:");
		 int intNumberOfDevices = ts.getNumberOfDevices();
		 for (int i = 0; i < intNumberOfDevices; i++) {
		   int id = ts.getDeviceId( i );
		   String name = ts.getName(id);
		   System.out.println("ID: " + id + "\tName: " + name + "\tStatus: " + ts.getLastCmd(id));
		 }
	}
	
	public void printExtendedDeviceStatus(){
		System.out.println("\nDevices:");
		ArrayList<Device> list = ts.getDevices();
		for (Device dev : list){
			 System.out.println("ID: " + dev.getId() + "\tName: " + dev.getName() + "\t\tModel: " + dev.getModel() +"\tProtocol: " +dev.getProto() + "\tType: " + dev.getType() + "\tLastRecievedCommand: " + dev.getLastCmd() + "\tLastValue: " + dev.getLastValue() );
			
		}
	}
	
	public void printControllerStatus(){
		System.out.println("\nControllers:");
		ArrayList<Controller> list =   ts.getControllers();
		 for (Controller c: (ArrayList<Controller>) list){
			 System.out.println("ID: " + c.getId() +  "\tName: " + c.getName() + "\tType: " + c.getTypeString() + "\tSerial: "  + c.getSerial() + "\tFirmware version: " + c.getFirmware() + "\tStatus: " + c.getStatus() );
		 }
	}
	
	public void printVersion(){
		System.out.println("\n#### Jstick version: " + ts.getJstickVersion());
	}
	
}
