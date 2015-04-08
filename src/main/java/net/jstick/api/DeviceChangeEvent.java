package net.jstick.api;

public class DeviceChangeEvent {

	private int deviceId;
	private int changeEvent;
	private int changeType;
	
	public DeviceChangeEvent(int deviceId, int changeEvent, int changeType) {
		this.deviceId = deviceId;
		this.changeEvent = changeEvent;
		this.changeType = changeType;
	}

	public int getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(int deviceId) {
		this.deviceId = deviceId;
	}

	public int getChangeEvent() {
		return changeEvent;
	}

	public void setChangeEvent(int changeEvent) {
		this.changeEvent = changeEvent;
	}

	public int getChangeType() {
		return changeType;
	}

	public void setChangeType(int changeType) {
		this.changeType = changeType;
	}
	
	

}
