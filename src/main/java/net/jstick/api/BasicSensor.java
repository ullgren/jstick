package net.jstick.api;

public class BasicSensor {
	
	private int id;
	private String model;
	private String protocol;
	private int dataTypes;
	
	
	public BasicSensor(String model, String protocol, int id, int dataTypes) {
		super();
		this.model = model;
		this.protocol = protocol;
		this.id = id;
		this.dataTypes = dataTypes;
	}
	
	public BasicSensor() {
		super();
		this.model = "";
		this.protocol = "";
		this.id = -1;
		this.dataTypes = -1;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getProtocol() {
		return protocol;
	}

	public void setProtocol(String protocol) {
		this.protocol = protocol;
	}

	public int getDataTypes() {
		return dataTypes;
	}

	public void setDataTypes(int dataTypes) {
		this.dataTypes = dataTypes;
	}	
	
	

}
