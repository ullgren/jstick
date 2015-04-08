package net.jstick.api;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sun.jna.Native;
import com.sun.jna.Pointer;

/*
 * Implements logic/methods etc in TellstickLibrary
 * 
 */


public class Tellstick {
	private static String version = "1.4";
	static Log log = LogFactory.getLog(Tellstick.class.getName());
	boolean debug = false;
	RawDeviceEvent rde;
	int methods = 	TellstickLibrary.INSTANCE.TELLSTICK_TURNON |
					TellstickLibrary.INSTANCE.TELLSTICK_TURNOFF |
					TellstickLibrary.INSTANCE.TELLSTICK_BELL |
					TellstickLibrary.INSTANCE.TELLSTICK_TOGGLE |
					TellstickLibrary.INSTANCE.TELLSTICK_DIM |
					TellstickLibrary.INSTANCE.TELLSTICK_EXECUTE |
					TellstickLibrary.INSTANCE.TELLSTICK_UP |
					TellstickLibrary.INSTANCE.TELLSTICK_DOWN |
					TellstickLibrary.INSTANCE.TELLSTICK_STOP;
	

				
	/**
	 * Constructor calls init() on creation to open up communication
	 * 
	 */
	public Tellstick() {
		super();
		init();
	}

	/**
	 * Constructor calls init() on creation to open up communication
	 * Debug argument enables debuging in JNA
	 * @param debug
	 */
	public Tellstick(boolean debug) {
		super();
		this.debug = debug;
		init();
	}

	// Setup communication
	public void init(){
		if(debug)
		System.setProperty("jna.debug_load", "true");
		TellstickLibrary.INSTANCE.tdInit();
	}
	
	// Close communication
	public void close(){
		TellstickLibrary.INSTANCE.tdClose();	
	}
	
	
	public int getNumberOfDevices(){
		return TellstickLibrary.INSTANCE.tdGetNumberOfDevices(); 
	}
	
	public int getDeviceId( int i){
		return TellstickLibrary.INSTANCE.tdGetDeviceId( i ); 
	}
	
	/*
	 * Search by name and return first exact match
	 */
	public int getDeviceIdByName( String name){
		ArrayList<Device> list = this.getDevices();
		for (Device dev : list){
			if(dev.getName().equals(name))
				return dev.getId();
		}
		return -1; 
	}
	
	public String getName(int id){
		Pointer ptr = TellstickLibrary.INSTANCE.tdGetName(id);
		String name = ptr.getString(0);
		TellstickLibrary.INSTANCE.tdReleaseString(ptr);
		return name;
	}
	
	public boolean setName(int id, String name){
		return TellstickLibrary.INSTANCE.tdSetName(id, name);
	}
	
	public int getLastSentValue(int id){
		Pointer ptr = TellstickLibrary.INSTANCE.tdLastSentValue(id);
		String dimLevel = ptr.getString(0);
		TellstickLibrary.INSTANCE.tdReleaseString(ptr);
		if(dimLevel.length()>0)
			return Integer.parseInt(dimLevel);
		else
			return 0;
	}
	
	@SuppressWarnings("static-access")
	public String getLastCmd(int id){
		int devmethodsSupported = TellstickLibrary.INSTANCE.tdMethods(id, methods);
		int state = TellstickLibrary.INSTANCE.tdLastSentCommand(id, devmethodsSupported);
		
		if(state == TellstickLibrary.INSTANCE.TELLSTICK_TURNON){
			return "ON";
		} else if (state == TellstickLibrary.INSTANCE.TELLSTICK_TURNOFF){
			return "OFF";
		} else if (state == TellstickLibrary.INSTANCE.TELLSTICK_DIM){
			Pointer ptr = TellstickLibrary.INSTANCE.tdLastSentValue(id);
			String dimLevel = ptr.getString(0);
			TellstickLibrary.INSTANCE.tdReleaseString(ptr);
			return "DIM " + dimLevel;
		} else if (state == TellstickLibrary.INSTANCE.TELLSTICK_BELL){
			return "BELL";
		} else if (state == TellstickLibrary.INSTANCE.TELLSTICK_TOGGLE){
			return "TOGGLE";
		} else if (state == TellstickLibrary.INSTANCE.TELLSTICK_EXECUTE){
			return "EXECUTE";
		} else if (state == TellstickLibrary.INSTANCE.TELLSTICK_UP){
			return "UP";
		}  else if (state == TellstickLibrary.INSTANCE.TELLSTICK_DOWN){
			return "DOWN";
		} else if (state == TellstickLibrary.INSTANCE.TELLSTICK_STOP){
			return "STOP";
		}
		return "UNKNOWN " + state;
	}
	
	public void test(int id){	
		System.out.println(TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_TURNON));
		System.out.println(TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_TURNOFF));
		System.out.println(TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_BELL));
		System.out.println(TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_TOGGLE));
		System.out.println(TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_DIM));
		System.out.println(TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_EXECUTE));
		System.out.println(TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_UP));
		System.out.println(TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_DOWN));
		System.out.println(TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_STOP));
		
	}
	
	
	/**
	 * Send commands to Device
	 * @param id the device id
	 * @param cmd the command to send ON, OFF, DIM0 - DIM256, BELL, EXECUTE, UP, DOWN, STOP
	 * 
	 */
	@SuppressWarnings("static-access")
	public int sendCmd(int id, String cmd){
		int status = -99;
		int supme = -1;
		
		if(cmd.equals("ON")){
			supme = TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_TURNON);
			if(supme > 0){
				status = TellstickLibrary.INSTANCE.tdTurnOn(id);
			} else {
				status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_METHOD_NOT_SUPPORTED;
			}
		} else if (cmd.equals("OFF")){
			supme = TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_TURNOFF);
			if(supme > 0){
				status = TellstickLibrary.INSTANCE.tdTurnOff(id);
			} else {
				status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_METHOD_NOT_SUPPORTED;
			}
		} else if (cmd.startsWith("DIM")){
			int level = -1;
			try {
			level =  new Integer(cmd.substring(3)).intValue();
			} catch (NumberFormatException nfe){
				status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_SYNTAX;
			} catch (Exception e){
				status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_UNKNOWN;
			}
			
			if (level > 256)
				level = 256;
			
			if (level < 0)
				level = 0;
			
			supme = TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_DIM);
			if(supme > 0 ){
				status = TellstickLibrary.INSTANCE.tdDim(id, level);
			} else {
				status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_METHOD_NOT_SUPPORTED;
			}
		} else if (cmd.equals("BELL")){
			supme = TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_BELL);
			if(supme > 0){
				status = TellstickLibrary.INSTANCE.tdBell(id);
			} else {
				status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_METHOD_NOT_SUPPORTED;
			}
		} else if (cmd.equals("TOGGLE")){
			// Not implemented in lib
			status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_METHOD_NOT_SUPPORTED;			
		} else if (cmd.equals("EXECUTE")){
			supme = TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_EXECUTE);
			if(supme > 0){
				status = TellstickLibrary.INSTANCE.tdExecute(id);
			} else {
				status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_METHOD_NOT_SUPPORTED;
			}
		} else if (cmd.equals("UP")){
			supme = TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_UP);
			if(supme > 0){
				status = TellstickLibrary.INSTANCE.tdUp(id);
			} else {
				status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_METHOD_NOT_SUPPORTED;
			}
		}  else if (cmd.equals("DOWN")){
			supme = TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_DOWN);
			if(supme > 0){
				status = TellstickLibrary.INSTANCE.tdDown(id);
			} else {
				status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_METHOD_NOT_SUPPORTED;
			}
		} else if (cmd.equals("STOP")){
			supme = TellstickLibrary.INSTANCE.tdMethods(id, TellstickLibrary.INSTANCE.TELLSTICK_STOP);
			if(supme > 0){
				status = TellstickLibrary.INSTANCE.tdStop(id);
			} else {
				status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_METHOD_NOT_SUPPORTED;
			}
		}	else {
			log.info("Command: " + cmd + " Unknown");
			status = TellstickLibrary.INSTANCE.TELLSTICK_ERROR_SYNTAX;
		}
		return status;
	}
	
	/**
	 * Repete the command to make sure the command gets throug
	 * @param deviceId, command and number of repaets
	 */
	public int sendCmd(int id, String cmd, int repeat){
		int status = -1;
		for (int i =0; i<repeat;i++){
			status = sendCmd(id, cmd);
		}
		return status;
	}
	
	public int learn(int id){
		return TellstickLibrary.INSTANCE.tdLearn(id);
	}

	/**
	 * Get the type of device
	 * @param deviceId
	 */
	public String getDeviceType(int id){
		int type = TellstickLibrary.INSTANCE.tdGetDeviceType(id);
		if(type == TellstickLibrary.INSTANCE.TELLSTICK_TYPE_DEVICE){
			return "DEVICE";
		} else if(type == TellstickLibrary.INSTANCE.TELLSTICK_TYPE_GROUP){
			return "GROUP";
		} else if(type == TellstickLibrary.INSTANCE.TELLSTICK_TYPE_SCENE){
			return "SCENE";
		} else {
			return "UNKNOWN";
		}
	}

	public String getErrorString(int errorCode){
		Pointer ptr = TellstickLibrary.INSTANCE.tdGetErrorString(errorCode);
		String error = ptr.getString(0);
		TellstickLibrary.INSTANCE.tdReleaseString(ptr);
		return error;
	}
	
	public int sendRawCommand(String cmd){
		return TellstickLibrary.INSTANCE.tdSendRawCommand(cmd, 0);
	}

	public String getModel(int id){
		Pointer modPtr = TellstickLibrary.INSTANCE.tdGetModel(id);
		String model = modPtr.getString(0);
		TellstickLibrary.INSTANCE.tdReleaseString(modPtr);
		return model;
	}
	
	public boolean setModel(int id, String model){
		return TellstickLibrary.INSTANCE.tdSetModel(id, model);
	}
	
	public String getProtocoll(int id){
		Pointer ptr = TellstickLibrary.INSTANCE.tdGetProtocol(id);
		String protocol = ptr.getString(0);
		TellstickLibrary.INSTANCE.tdReleaseString(ptr);
		return protocol;
	}
	
	public boolean setProtocol(int id, String protocol){
		return TellstickLibrary.INSTANCE.tdSetProtocol(id, protocol);
	}
	
	public ArrayList<Controller> getControllers(){
		int[] cid = new int[1];
		int[] ctype = new int[1];	
		byte[] cname = new byte[25];
		int[] cavail = new int[1];
		ArrayList<Controller> ctrls = new ArrayList<Controller>();
		String serial = "UNKNOWN";
		String firmware = "UNKNOWN";
		String name = "UNSET";
		String avail = "UNKNOWN";
		
		while(TellstickLibrary.INSTANCE.tdController(cid, ctype, cname, 25, cavail) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS){

			byte[] value = new byte[20];
			
			if(TellstickLibrary.INSTANCE.tdControllerValue(cid[0],"serial", value, 20) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS){
				if(Native.toString(value).length() > 0)
					serial = Native.toString(value);
			}
			
			if(TellstickLibrary.INSTANCE.tdControllerValue(cid[0],"firmware", value, 20) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS){
				if(Native.toString(value).length() > 0)
					firmware = Native.toString(value);
				if(firmware.equals("-1"))
					firmware = "UNKNOWN";
			}
			
			if(Native.toString(cname).length() > 0)
				name = Native.toString(cname);
			
			if (cavail[0] == 1){
				avail = "CONNECTED";
			} else if(cavail[0] == 0){
				avail = "DISCONNECTED";
			}
			
			//log.info(cavail[0]);
			
			Controller ctrl = new Controller(cid[0],ctype[0],name,serial,firmware,avail);
			ctrls.add(ctrl);
				
			
		}
		return ctrls;
	}
	
	public ArrayList<Controller> getConnectedControllers(){
		int[] cid = new int[1];
		int[] ctype = new int[1];	
		byte[] cname = new byte[25];
		int[] cavail = new int[1];
		ArrayList<Controller> ctrls = new ArrayList<Controller>();
		String serial = "UNKNOWN";
		String firmware = "UNKNOWN";
		String name = "UNSET";
		String avail = "UNKNOWN";
		
		while(TellstickLibrary.INSTANCE.tdController(cid, ctype, cname, 25, cavail) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS){

			byte[] value = new byte[20];
			
			if(TellstickLibrary.INSTANCE.tdControllerValue(cid[0],"serial", value, 20) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS){
				if(Native.toString(value).length() > 0)
					serial = Native.toString(value);
			}
			
			if(TellstickLibrary.INSTANCE.tdControllerValue(cid[0],"firmware", value, 20) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS){
				if(Native.toString(value).length() > 0)
					firmware = Native.toString(value);
				if(firmware.equals("-1"))
					firmware = "UNKNOWN";
			}
			
			if(Native.toString(cname).length() > 0)
				name = Native.toString(cname);
			
			if (cavail[0] == 1){
				avail = "CONNECTED";
			} else if(cavail[0] == 0){
				avail = "DISCONNECTED";
			}
			
			
			
			Controller ctrl = new Controller(cid[0],ctype[0],name,serial,firmware,avail);
			if(avail.equals("CONNECTED"))
			ctrls.add(ctrl);
				
			
		}
		return ctrls;
	}
	
	/**
	 * Returns array of read only devices
	 */
	
	public ArrayList<Device> getDevices(){
		ArrayList<Device> devs = new ArrayList<Device>();

		int intNumberOfDevices = this.getNumberOfDevices();
		 for (int i = 0; i < intNumberOfDevices; i++) {
			 int id = this.getDeviceId(i);
			 String name = this.getName(id);
			 String model = this.getModel(id);
			 String proto = this.getProtocoll(id);
			 String type = this.getDeviceType(id);
			 String last = this.getLastCmd(id);
			 int meth = this.getSupportedMethods(id);
			 int lastValue = this.getLastSentValue(id);
			 Device dev = new Device(id,name,model,proto,type,meth,last,lastValue);
			 devs.add(dev);
		 }
		return devs;
	}
	
	public Device getDevice(int id){
		ArrayList<Device> devs = (ArrayList<Device>) this.getDevices();
		
		for(Device dev : devs){
			if(dev.getId() == id)
				return dev;
		}
		return null;
	}
	
	/**
	 * list available sensors (no values)
	 * TODO do we need this method??
	 * @param deviceId
	 */
	
	public List<BasicSensor> getSimpleSensors(){
		byte[] pro = new byte[25];
		byte[] mod = new byte[25];
		int[] sid = new int[1];
		int[] dt = new int[1];
		ArrayList<BasicSensor> sensors = new ArrayList<BasicSensor>();
		
		 while(TellstickLibrary.INSTANCE.tdSensor(pro, 25, mod, 25, sid, dt) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS) {
			 BasicSensor sens = new BasicSensor(Native.toString(mod),Native.toString(pro),sid[0],dt[0]);
			 sensors.add(sens);

			 }
		 return sensors;
		
	}
	
	/**
	 * Get sensor data only temp & humidity implemented atm
	 * TODO add more sensor types
	 * @param deviceId
	 */
	
	public ArrayList<Sensor> getSensors(){
		byte[] pro = new byte[25];
		byte[] mod = new byte[25];
		int[] sid = new int[1];
		int[] dt = new int[1];
		ArrayList<Sensor> sensors = new ArrayList<Sensor>();
		
		 while(TellstickLibrary.INSTANCE.tdSensor(pro, 25, mod, 25, sid, dt) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS) {
			 if(dt[0] == 1){
				 byte[] value = new byte[10];
				 int[] time = new int[1];
				 double temp = -99;
				 if (TellstickLibrary.INSTANCE.tdSensorValue(Native.toString(pro), Native.toString(mod), sid[0], TellstickLibrary.INSTANCE.TELLSTICK_TEMPERATURE, value, 10, time) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS){
					
					 temp = Double.parseDouble(Native.toString(value));
					 Sensor sens = new Sensor(Native.toString(mod),Native.toString(pro),sid[0],dt[0],temp,time[0]);
					 sensors.add(sens);
				 }
				 
				 
			 } else if(dt[0] == 3){
				 byte[] value = new byte[10];
				 int[] time = new int[1];
				 double temp = -99;
				 int hum = -99;
				 if (TellstickLibrary.INSTANCE.tdSensorValue(Native.toString(pro), Native.toString(mod), sid[0], TellstickLibrary.INSTANCE.TELLSTICK_TEMPERATURE, value, 10, time) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS){
					 temp = Double.parseDouble(Native.toString(value));
					 value = null;
				 }
					
				 if (TellstickLibrary.INSTANCE.tdSensorValue(Native.toString(pro), Native.toString(mod), sid[0], TellstickLibrary.INSTANCE.TELLSTICK_HUMIDITY, value, 10, time) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS){
					 hum = Integer.parseInt(Native.toString(value));
					 value=null;
				 }
				 
				 Sensor sens = new Sensor(Native.toString(mod),Native.toString(pro),sid[0],dt[0],temp,hum,time[0]);
				 sensors.add(sens);

			 } else if(dt[0] == 2){
				 byte[] value = new byte[10];
				 int[] time = new int[1];
				 double temp = -99;
				 int hum = -99;
					
				 if (TellstickLibrary.INSTANCE.tdSensorValue(Native.toString(pro), Native.toString(mod), sid[0], TellstickLibrary.INSTANCE.TELLSTICK_HUMIDITY, value, 10, time) == TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS){
					 hum = Integer.parseInt(Native.toString(value));
					 value=null;
				 }
				 
				 Sensor sens = new Sensor(Native.toString(mod),Native.toString(pro),sid[0],dt[0],temp,hum,time[0]);
				 sensors.add(sens);

			 } else {
				 // Not yet implemented - returning a blank sensor
				 double temp = -99;
				 int hum = -99;
				 Sensor sens = new Sensor(Native.toString(mod),Native.toString(pro),sid[0],dt[0],temp, hum);
				 sensors.add(sens);
			 }


			 }
		 mod = null;
		 pro = null;
		 return sensors;
		
	}
	
	public Sensor getSensor(int id){
		ArrayList<Sensor> sensors = this.getSensors();		
		for(Sensor sen : sensors){
			if(sen.getId() == id)
				return sen;
		}
		return null;
	}
	
	public String getDeviceParameter(int id, String name, String defaultValue){
		Pointer ptr = TellstickLibrary.INSTANCE.tdGetDeviceParameter(id, name, defaultValue);
		String paramValue = ptr.getString(0);
		TellstickLibrary.INSTANCE.tdReleaseString(ptr);
		return paramValue;
	} 
	
	public boolean setDeviceParameter(int id, String name, String value){
		return TellstickLibrary.INSTANCE.tdSetDeviceParameter(id, name, value);
	}
	
	
	 
	 public int addDevice(DeviceConfig cfgDev){
		 int newId = TellstickLibrary.INSTANCE.tdAddDevice();
		 if(newId <= -1)
		 	return newId;
		 log.debug("in addDevice - newId: "+ newId);
		 boolean bname = this.setName(newId, cfgDev.getName());
		 log.debug("in addDevice - setName returned: "+ bname);
		 boolean bmodel = this.setModel(newId, cfgDev.getModel());
		 log.debug("in addDevice - setModel returned: "+ bmodel);
		 boolean bproto = this.setProtocol(newId, cfgDev.getProtocol());
		 log.debug("in addDevice - setProto returned: "+ bproto);
		 boolean bparam = false;
		 
		 Hashtable<String, String> map = cfgDev.getParams();
		 Set<String> keys = map.keySet();
	        for(String key: keys){
	        	bparam = this.setDeviceParameter(newId, key, map.get(key));
	        	log.debug("key: "+ key + " value: " + map.get(key) + " ok?:" + bparam);
	        }
	        log.debug("in addDevice - last setDeviceParameter returned: "+ bparam);
	 if(bname && bmodel && bproto && bparam){
		 return TellstickLibrary.INSTANCE.TELLSTICK_SUCCESS;
	 }
	 else {
		 this.removeDevice(newId);
		 return -1;
	 }
	 }
	 
	public boolean removeDevice(int id){
		return TellstickLibrary.INSTANCE.tdRemoveDevice(id);
	}
	
	public void listenRaw(long time){		
		RawDeviceEvent rde = new RawDeviceEvent();			
		int callbackId = TellstickLibrary.INSTANCE.tdRegisterRawDeviceEvent(rde, null);
	
		try{
			Thread.sleep(time);
		}catch (Exception e){}
	}
	
	// We need to keep the returned RawDeviceEvent "live" to avoid GC that will stop the callback
	public RawDeviceEvent listenRaw(){
		RawDeviceEvent rde = new RawDeviceEvent();			
		int callbackId = TellstickLibrary.INSTANCE.tdRegisterRawDeviceEvent(rde, null);
		return rde;
	}
	
	public void listenDeviceEvents(long time){		
		TellstickLibrary.TDDeviceEvent de = new TellstickLibrary.TDDeviceEvent() {
			
			
			public void apply(int deviceId, int method, String data, int callbackId,Pointer context) {
				
				System.out.println("ID: " + deviceId + " - Name: " + getName(deviceId) + " - Cmd: " + cmdToString(method) + " - Data: " + data);
				
			}
		};
			
		int callbackId = TellstickLibrary.INSTANCE.tdRegisterDeviceEvent(de, null);
		
		try{
			Thread.sleep(time);
		}catch (Exception e){}
	}
	
	public int getSupportedMethods(int id){	
		int supported = TellstickLibrary.INSTANCE.tdMethods(id, methods);
		//log.info(supported);
		return supported;
	}
	
	public String cmdToString(int cmd){
		
		if(cmd == TellstickLibrary.INSTANCE.TELLSTICK_TURNON){
			return "ON";
		} else if (cmd == TellstickLibrary.INSTANCE.TELLSTICK_TURNOFF){
			return "OFF";
		} else if (cmd == TellstickLibrary.INSTANCE.TELLSTICK_DIM){
			return "DIM";
		} else if (cmd == TellstickLibrary.INSTANCE.TELLSTICK_BELL){
			return "BELL";
		} else if (cmd == TellstickLibrary.INSTANCE.TELLSTICK_TOGGLE){
			return "TOGGLE";
		} else if (cmd == TellstickLibrary.INSTANCE.TELLSTICK_EXECUTE){
			return "EXECUTE";
		} else if (cmd == TellstickLibrary.INSTANCE.TELLSTICK_UP){
			return "UP";
		}  else if (cmd == TellstickLibrary.INSTANCE.TELLSTICK_DOWN){
			return "DOWN";
		} else if (cmd == TellstickLibrary.INSTANCE.TELLSTICK_STOP){
			return "STOP";
		}
		return "UNKNOWN COMMAND";
		
	}
	
	public String getJstickVersion(){
		return version;
	}
}
