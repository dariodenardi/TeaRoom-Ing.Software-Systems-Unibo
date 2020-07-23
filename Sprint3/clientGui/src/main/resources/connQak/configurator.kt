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
		}catch(e:Exception){
			System.out.println( " &&& SORRY pageConfig.json NOT FOUND ")
			pageTemplate 	=  "manager"  //jsonObject.getString("page") 
			hostAddr    	=  "127.0.0.1"    //jsonObject.getString("host") 
			port    		= "8040"             //jsonObject.getString("port")
			qakdest         = "waitermind"       //jsonObject.getString("qakdest")
			ctxqadest		= "ctxtearoom"    //jsonObject.getString("ctxqadest")
		}
		
		System.out.println( "configurator 	| pageTemplate=$pageTemplate hostAddr=$hostAddr port=$port" )
		
	}
}

