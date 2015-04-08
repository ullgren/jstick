package net.jstick.api;

public interface SensorEventListener extends TellstickEventListener {

	void eventReceived(SensorEvent event);

}
