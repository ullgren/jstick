package net.jstick.api;

import com.sun.jna.Callback;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;


/**
 * 
 * Based on telldus-core.h bundeled with telldus-core-2.1.2
 * ref http://developer.telldus.se/doxygen/group__core.html
 * 
 */

public interface TellstickLibrary extends Library {

	/**
	 * Library config and setup
	 */
	public static final String JNA_LIBRARY_NAME = "telldus-core";
	public static final TellstickLibrary INSTANCE = (TellstickLibrary)Native.loadLibrary(TellstickLibrary.JNA_LIBRARY_NAME, TellstickLibrary.class);
	
	
	/**
	 * Static values
	 */
	public static final int TELLSTICK_CHANGE_METHOD = (int)4;
	public static final int TELLSTICK_TYPE_SCENE = (int)3;
	public static final int TELLSTICK_DEVICE_STATE_CHANGED = (int)4;
	public static final int TELLSTICK_DEVICE_ADDED = (int)1;
	public static final int TELLSTICK_CONTROLLER_TELLSTICK_DUO = (int)2;
	public static final int TELLSTICK_BELL = (int)4;
	public static final int TELLSTICK_CONTROLLER_TELLSTICK_NET = (int)3;
	public static final int TELLSTICK_ERROR_COMMUNICATION = (int)-5;
	public static final int TELLSTICK_DIM = (int)16;
	public static final int TELLSTICK_ERROR_UNKNOWN = (int)-99;
	public static final int TELLSTICK_ERROR_DEVICE_NOT_FOUND = (int)-3;
	public static final int TELLSTICK_ERROR_CONNECTING_SERVICE = (int)-6;
	public static final int TELLSTICK_TEMPERATURE = (int)1;
	public static final int TELLSTICK_SUCCESS = (int)0;
	public static final int TELLSTICK_ERROR_COMMUNICATING_SERVICE = (int)-10;
	public static final int TELLSTICK_ERROR_CONFIG_SYNTAX = (int)-11;
	public static final int TELLSTICK_CHANGE_NAME = (int)1;
	public static final int TELLSTICK_DEVICE_CHANGED = (int)2;
	public static final int TELLSTICK_WINDAVERAGE = (int)32;
	public static final int TELLSTICK_DEVICE_REMOVED = (int)3;
	public static final int TELLSTICK_TURNOFF = (int)2;
	public static final int TELLSTICK_ERROR_BROKEN_PIPE = (int)-9;
	public static final int TELLSTICK_DOWN = (int)256;
	public static final int TELLSTICK_TOGGLE = (int)8;
	public static final int TELLSTICK_TYPE_GROUP = (int)2;
	public static final int TELLSTICK_WINDGUST = (int)64;
	public static final int TELLSTICK_ERROR_NOT_FOUND = (int)-1;
	public static final int TELLSTICK_CONTROLLER_TELLSTICK = (int)1;
	public static final int TELLSTICK_RAINRATE = (int)4;
	public static final int TELLSTICK_TURNON = (int)1;
	public static final int TELLSTICK_TYPE_DEVICE = (int)1;
	public static final int TELLSTICK_LEARN = (int)32;
	public static final int TELLSTICK_ERROR_PERMISSION_DENIED = (int)-2;
	public static final int TELLSTICK_ERROR_SYNTAX = (int)-8;
	public static final int TELLSTICK_CHANGE_PROTOCOL = (int)2;
	public static final int TELLSTICK_RAINTOTAL = (int)8;
	public static final int TELLSTICK_ERROR_UNKNOWN_RESPONSE = (int)-7;
	public static final int TELLSTICK_HUMIDITY = (int)2;
	public static final int TELLSTICK_WINDDIRECTION = (int)16;
	public static final int TELLSTICK_CHANGE_FIRMWARE = (int)6;
	public static final int TELLSTICK_CHANGE_AVAILABLE = (int)5;
	public static final int TELLSTICK_CHANGE_MODEL = (int)3;
	public static final int TELLSTICK_ERROR_METHOD_NOT_SUPPORTED = (int)-4;
	public static final int TELLSTICK_EXECUTE = (int)64;
	public static final int TELLSTICK_UP = (int)128;
	public static final int TELLSTICK_STOP = (int)512;
	
	/**
	 * Callback Interfaces
	 */
	public interface TDDeviceEvent extends Callback {
		void apply(int deviceId, int method, String data, int callbackId, Pointer context);
	};
	
	public interface TDDeviceChangeEvent extends Callback {
		void apply(int deviceId, int changeEvent, int changeType, int callbackId, Pointer context);
	};
	

	public interface TDRawDeviceEvent extends Callback {
		void apply(Pointer data, int controllerId, int callbackId, Pointer context);
		//void apply(String data, int controllerId, int callbackId, Pointer context);
	};
	
	public interface TDSensorEvent extends Callback {
		void apply(Pointer protocol, Pointer model, int id, int dataType, Pointer value, int timestamp, int callbackId, Pointer context);
	};
	
	public interface TDControllerEvent extends Callback {
		void apply(int controllerId, int changeEvent, int changeType, Pointer newValue, int callbackId, Pointer context);
	};
	
	/**
	 * Library methods
	 */
	void tdInit();
	
	int tdRegisterDeviceEvent(TellstickLibrary.TDDeviceEvent eventFunction, Pointer context);
	
	int tdRegisterDeviceChangeEvent(TellstickLibrary.TDDeviceChangeEvent eventFunction, Pointer context);
	
	int tdRegisterRawDeviceEvent(TellstickLibrary.TDRawDeviceEvent eventFunction, Pointer context);
	
	int tdRegisterSensorEvent(TellstickLibrary.TDSensorEvent eventFunction, Pointer context);
	
	int tdRegisterControllerEvent(TellstickLibrary.TDControllerEvent eventFunction, Pointer context);
	
	int tdUnregisterCallback(int callbackId);
		
	void tdClose();	
	
	//void tdReleaseString(String thestring);
	void tdReleaseString(Pointer thestring);
	
	int tdTurnOn(int intDeviceId);
	
	int tdTurnOff(int intDeviceId);
	
	int tdBell(int intDeviceId);
	
	int tdDim(int intDeviceId, int level);
	
	int tdExecute(int intDeviceId);
	
	int tdUp(int intDeviceId);
	
	int tdDown(int intDeviceId);
	
	int tdStop(int intDeviceId);
	
	int tdLearn(int intDeviceId);
	
	int tdMethods(int id, int methodsSupported);
	
	int tdLastSentCommand(int intDeviceId, int methodsSupported);
	
	//String tdLastSentValue(int intDeviceId);
	Pointer tdLastSentValue(int intDeviceId);
	
	int tdGetNumberOfDevices();
	
	int tdGetDeviceId(int intDeviceIndex);
	
	int tdGetDeviceType(int intDeviceId);
	
	//String tdGetErrorString(int intErrorNo);
	Pointer tdGetErrorString(int intErrorNo);
	
	//public String tdGetName(int intDeviceId);
	Pointer tdGetName(int intDeviceId);
			
	boolean tdSetName(int intDeviceId, String NewName);
	
	//String tdGetProtocol(int intDeviceId);
	Pointer tdGetProtocol(int intDeviceId);
	
	boolean tdSetProtocol(int intDeviceId, String strProtocol);
	
	//String tdGetModel(int intDeviceId);
	Pointer tdGetModel(int intDeviceId);
	
	boolean tdSetModel(int intDeviceId, String intModel);
	
	//String tdGetDeviceParameter(int intDeviceId, String strName, String defaultValue);
	Pointer tdGetDeviceParameter(int intDeviceId, String strName, String defaultValue);
	
	boolean tdSetDeviceParameter(int intDeviceId, String strName, String strValue);
	
	int tdAddDevice();
	
	boolean tdRemoveDevice(int intDeviceId);
	
	int tdSendRawCommand(String command, int reserved);
	
	void tdConnectTellStickController(int vid, int pid, String serial);
	
	void tdDisconnectTellStickController(int vid, int pid, String serial);
	
	int tdSensor(byte[] protocol, int protocolLen, byte[] model, int modelLen, int[] id, int[] dataTypes);
	
	int tdSensorValue(String protocol, String model, int id, int dataType, byte[] value, int len, int[] timestamp);
		
	int tdController(int[] controllerId, int[] controllerType, byte[] name, int nameLen, int[] available);
	
	int tdControllerValue(int controllerId, String name, byte[] value, int valueLen);
	
	int tdSetControllerValue(int controllerId, String name, String value);
	
	int tdRemoveController(int controllerId);

}
