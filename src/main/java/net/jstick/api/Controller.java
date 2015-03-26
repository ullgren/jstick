package net.jstick.api;

public class Controller {
	private int id = -1;
	private int type = -1;
	private String name = "UNSET";
	private String serial = "N/A";
	private String firmware = "N/A";
	private String status = "N/A";

	
	public Controller(int id, int type, String name, String serial, String firmware, String status) {
		super();
		this.id = id;
		this.type = type;
		this.name = name;
		this.serial = serial;
		this.firmware = firmware;
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public int getType() {
		return type;
	}

	public int getId() {
		return id;
	}
	
	public String getSerial() {
		return serial;
	}

	public String getTypeString(){
		String ctype = "UNKONWN";
		if(type == 1)
			ctype = "TELLSTICK";
		if(type == 2)
			ctype = "TELLSTICK_DUO";
		if(type == 3)
			ctype = "TELLSTICK_NET";
		return ctype;
	}

	public String getFirmware() {
		return firmware;
	}

	public String getStatus() {
		return status;
	}
	
	

}
