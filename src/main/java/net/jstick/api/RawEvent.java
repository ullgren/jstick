package net.jstick.api;


public class RawEvent {

	private String data;
	private int controllerId;
	
	public RawEvent(String data, int controllerId) {
		this.data = data;
		this.controllerId = controllerId;
	}
	
	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public int getControllerId() {
		return controllerId;
	}
	
	public void setControllerId(int controllerId) {
		this.controllerId = controllerId;
	}
	

}
