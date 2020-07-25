package connQak

import org.json.JSONObject
import java.io.File
//import java.nio.charset.Charset
//import org.apache.commons.io.Charsets
  

object configurator{
	// Page
	@JvmStatic public var pageTemplate		= "client"

	// MQTT broker
//	@JvmStatic var mqtthostAddr    	= "broker.hivemq.com"
	@JvmStatic var mqtthostAddr    	= "localhost"
	@JvmStatic var mqttport    		= "1883"
	
	// Domains application
	@JvmStatic var hostAddr   	    = "127.0.0.1";  //"192.168.1.5";		
	@JvmStatic var port    			= "8040";
	@JvmStatic var qakdest	     	= "waitermind";
	@JvmStatic var ctxqadest 		= "ctxtearoom";
	
	// Domains application
	@JvmStatic var hostAddrBasiRobot   	    = "localhost";  //"192.168.1.5";		
	@JvmStatic var portBasiRobot    		= "8020";
	@JvmStatic var qakdestBasiRobot	     	= "basicrobot";
	@JvmStatic var ctxqadestBasiRobot 		= "ctxbasicrobot";
	
	@JvmStatic	//to be used by Java
	fun configure(){
		try{
			val configfile =   File("pageConfig.json")
			val config     =   configfile.readText()	//charset: Charset = Charsets.UTF_8
			//println( "		--- configurator | config=$config" )
			val jsonObject	=  JSONObject( config )			
			pageTemplate 	=  jsonObject.getString("page") 
			hostAddr    	=  jsonObject.getString("host") 
			port    		=  jsonObject.getString("port")
			qakdest         =  jsonObject.getString("qakdest")
			ctxqadest		=  jsonObject.getString("ctxqadest")
			
			hostAddrBasiRobot   	= jsonObject.getString("hostAddrBasiRobot");
			portBasiRobot    		= jsonObject.getString("portBasiRobot");
			qakdestBasiRobot	    = jsonObject.getString("qakdestBasiRobot");
			ctxqadestBasiRobot 		= jsonObject.getString("ctxqadestBasiRobot");
		}catch(e:Exception){
			System.out.println( " &&& SORRY pageConfig.json NOT FOUND ")
			pageTemplate 	=  "manager"  //jsonObject.getString("page") 
			hostAddr    	=  "127.0.0.1"    //jsonObject.getString("host") 
			port    		= "8040"             //jsonObject.getString("port")
			qakdest         = "waitermind"       //jsonObject.getString("qakdest")
			ctxqadest		= "ctxtearoom"    //jsonObject.getString("ctxqadest")
			
			hostAddrBasiRobot   	= "localhost";  //"192.168.1.5";		
			portBasiRobot    		= "8020";
			qakdestBasiRobot	    = "basicrobot";
			ctxqadestBasiRobot 		= "ctxbasicrobot"; 
		}
		
		System.out.println( "configurator 	| pageTemplate=$pageTemplate hostAddr=$hostAddr port=$port" )
		
	}
}

