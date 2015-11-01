package net.jstick.api;

import java.util.Hashtable;

public class DeviceConfig {
	private int id = -1;
	private String name;
	private int ctrl = 0;
	private String protocol;
	private String model;
	private Hashtable<String, String> params = null;
	
	
	
	public DeviceConfig(int id, String name, int ctrl, String protocol,
			String model, Hashtable<String, String> params) {
		super();
		this.id = id;
		this.name = name;
		this.ctrl = ctrl;
		this.protocol = protocol;
		this.model = model;
		this.params = params;
	}
	
	public DeviceConfig() {
		super();
		this.id = 99;
		this.name = "No name";
		this.ctrl = 0;
		this.protocol = "protocol";
		this.model = "model";
		this.params = new Hashtable<String, String>();
	}	
	
	public boolean setParam(String param, String value){
		if(this.params != null){
			this.params.put(param, value);
		return true;	
		}
		return false;
	}
	
	public String getParam(String param){
		if(this.params != null){
			return this.params.get(param);	
		}
	return "NOT SET";
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCtrl() {
		return ctrl;
	}

	public void setCtrl(int ctrl) {
		this.ctrl = ctrl;
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
	
	public Hashtable<String,String> getParams(){
		return params;
	}
	
	
}