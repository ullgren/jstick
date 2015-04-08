package net.jstick.api;

public interface DeviceChangeEventListener extends TellstickEventListener {

	void eventReceived(DeviceChangeEvent event);

}
