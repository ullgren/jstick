package net.jstick.api;

import java.util.Date;

public class SensorEvent {
	
	private String protocol;
	private String model;
	private int id;
	private int dataType;
	private Date timestamp;
	private String value;

	public SensorEvent(String protocol, String model, int id, int dataType,
			String value, int timestamp) {
		this.protocol = protocol;
		this.model = model;
		this.id = id;
		this.dataType = dataType;
		this.value = value;
		this.timestamp = new Date((timestamp*1000L));
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public int getId() {
		return id;
	}

	public String getValue() {
		return value;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public int getDataType() {
		return dataType;
	}

	public void setDataType(int dataType) {
		this.dataType = dataType;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
}
