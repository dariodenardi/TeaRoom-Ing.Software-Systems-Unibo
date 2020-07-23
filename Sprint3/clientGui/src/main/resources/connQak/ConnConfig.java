package connQak;

public class ConnConfig {
	
	// MQTT broker
	public static final String mqtthostAddr    	= configurator.getMqtthostAddr();
	public static final String mqttport    		= configurator.getMqttport();
	
	// Domains application
	public static final String hostAddr   	    = configurator.getHostAddr();		
	public static final String port    			= configurator.getPort();
	public static final String qakdestination	= configurator.getQakdest();
	public static final String ctxqadest 		= configurator.getCtxqadest();

}
