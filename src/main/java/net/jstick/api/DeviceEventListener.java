package net.jstick.api;

public interface DeviceEventListener extends TellstickEventListener {

	void eventReceived(DeviceEvent event);

}
