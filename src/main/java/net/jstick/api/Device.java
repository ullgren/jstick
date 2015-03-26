package net.jstick.api;

public class Device {
	private int id = -1;
	private String name = "NA";
	private String model = "NA";
	private String proto = "NA";
	private String type = "NA";
	private int methods = -1;		
	private String lastCmd = "UNKNOWN";
	private int lastValue = -1;
	
	public Device(int id, String name, String model, String proto,
			String type, int meth, String lastCmd, int lastValue) {
		super();
		this.id = id;
		this.name = name;
		this.model = model;
		this.proto = proto;
		this.type = type;
		this.methods = meth;
		this.lastCmd = lastCmd;
		this.lastValue = lastValue;
	}
	
	public Device() {
		super();
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getModel() {
		return model;
	}

	public String getProto() {
		return proto;
	}

	public String getType() {
		return type;
	}

	public String getLastCmd() {
		return lastCmd;
	}
	
	public int getMethods() {
		return methods;
	}
	
	public int getLastValue() {
		return lastValue;
	}
	

}
