package net.jstick.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.sun.jna.Pointer;

import net.jstick.api.TellstickLibrary.TDRawDeviceEvent;

public class RawDeviceEvent implements TDRawDeviceEvent{

	public void apply(Pointer data, int controllerId, int callbackId, Pointer context) {
		DateFormat df = new SimpleDateFormat("yy-MM-dd HH:mm:ss");
	    System.out.println(df.format(Calendar.getInstance().getTime()) + "\tCtrl:" + controllerId + "\tData: " + data.getString(0));	    
		
	}

}
