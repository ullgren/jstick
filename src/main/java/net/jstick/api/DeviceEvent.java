package net.jstick.api;

public class DeviceEvent {

	private int deviceId;
	private int method;
	private String data;
	
	public DeviceEvent(int deviceId, int method, String data) {
		this.deviceId = deviceId;
		this.method = method;
		this.data = data;
	}

	public String getData() {
		return data;
	}
	
	public void setData(String data) {
		this.data = data;
	}
	
	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getMethod() {
		return method;
	}

	public void setMethod(int method) {
		this.method = method;
	}

}
