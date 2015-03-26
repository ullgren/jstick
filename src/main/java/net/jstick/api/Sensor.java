package net.jstick.api;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Sensor extends BasicSensor {
	
	/*
	 * Only temp and hum implemented at the moment
	 * 
	 * TELLSTICK_TEMPERATURE   1
	 * TELLSTICK_HUMIDITY   2
	 * TELLSTICK_RAINRATE   4
	 * TELLSTICK_RAINTOTAL   8
	 * TELLSTICK_WINDDIRECTION   16
	 * TELLSTICK_WINDAVERAGE   32
	 * TELLSTICK_WINDGUST   64
	 */
	
	private double temperature = -99;
	private int humidity = -99;
	private int timeStamp = -99;
	
	
	public Sensor(String model, String protocol, int id, int dataTypes,
			double temperature, int humidity, int timeStamp) {
		super(model, protocol, id, dataTypes);
		this.temperature = temperature;
		this.humidity = humidity;
		this.timeStamp = timeStamp;
	}
	
	public Sensor(String model, String protocol, int id, int dataTypes,
			double temperature, int timeStamp) {
		super(model, protocol, id, dataTypes);
		this.temperature = temperature;
		this.timeStamp = timeStamp;
	}
	
	public Sensor(String model, String protocol, int id, int dataTypes,
			 int humidity, int timeStamp) {
		super(model, protocol, id, dataTypes);
		this.humidity = humidity;
		this.timeStamp = timeStamp;
	}	


	public double getTemperature() {
		return temperature;
	}


	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}


	public int getHumidity() {
		return humidity;
	}


	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}


	public int getTimeStamp() {
		return timeStamp;
	}


	public void setTimeStamp(int timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public String getTimeStampString(){
		SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yyyy-MM-dd");
		Calendar ca = new GregorianCalendar();
		
		long newTime = (long) (this.getTimeStamp()*1000L);
		ca.setTimeInMillis(newTime);
		return sdf.format(ca.getTime());
	}
	
	public int getTimeStampAge(){	
		long age = System.currentTimeMillis() - (long) (this.getTimeStamp()*1000L);
		int minutes = (int) (age/1000L)/60;
		return minutes;
	}

}
