package net.jstick.api;

public interface RawEventListener extends TellstickEventListener {

	public void eventReceived(RawEvent event);
}
