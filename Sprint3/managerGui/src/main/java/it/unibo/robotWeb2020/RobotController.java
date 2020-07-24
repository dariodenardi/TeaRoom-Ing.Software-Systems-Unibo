package it.unibo.robotWeb2020;
//https://www.baeldung.com/websockets-spring
//https://www.toptal.com/java/stomp-spring-boot-websocket
	
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapHandler;
import org.eclipse.californium.core.CoapResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value; 
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import connQak.configurator;
import connQak.connQakCoap;
import it.unibo.kactor.ApplMessage;
import it.unibo.kactor.MsgUtil;


@Controller 
public class RobotController { 
    String appName      = "ClientGUI";
    String viewModelRep = "startup";
    String robotHost = ""; //ConnConfig.hostAddr;		
    String robotPort = ""; //ConnConfig.port;
     
    //String htmlPage  = "robotGuiPost"; 
    //String htmlPage  = "robotGuiSocket";
    String htmlPage = "client";
    //String htmlPage  = "robotGuiPostBoundary"; 
    
    Set<String> robotMoves = new HashSet<String>(); 
    
    connQakCoap connQakSupport ;   
    
    public RobotController() {
        connQak.configurator.configure();
        htmlPage  = connQak.configurator.getPageTemplate();
        robotHost =	connQak.configurator.getHostAddr();	
        robotPort = connQak.configurator.getPort();

        robotMoves.addAll( Arrays.asList(new String[] {"w","s","h","r","l","z","x","p"}) );       
        //connQakSupport = new connQakMqtt("mqtt.eclipse.org", "1883", "waitermind"  );
        connQakSupport = new connQakCoap();
        connQakSupport.createConnection();
          
     }

    /*
     * Update the page vie socket.io when the application-resource changes.
     * Thanks to Eugenio Cerulo
     */
    	@Autowired
    	SimpMessagingTemplate simpMessagingTemplate;

  @GetMapping("/") 		 
  public String entry(Model viewmodel) {
 	 viewmodel.addAttribute("arg", "Entry page loaded. Please use the buttons ");
 	 peparePageUpdating();
 	 return htmlPage;
  } 
   
  @GetMapping("/applmodel")
  @ResponseBody
  public String getApplicationModel(Model viewmodel) {
  	  ResourceRep rep = getWebPageRep();
  	  viewmodel.addAttribute("state",rep.getContent());
	  return rep.getContent();
  }     
  
    
	@PostMapping( path = "/move" ) 
	public String doMove( 
		@RequestParam(name="move", required=false, defaultValue="enter") 
		//binds the value of the query string parameter name into the moveName parameter of the  method
		String moveName, Model viewmodel) {
		System.out.println("------------------- RobotController doMove move=" + moveName  );
		//if( robotMoves.contains(moveName) ) {
			doBusinessJob(moveName, viewmodel);
 		//}else {
		//	viewmodel.addAttribute("arg", "Sorry: move unknown - Current Robot State:"+viewModelRep );
		//}		
		return htmlPage;
		//return "robotGuiSocket";
	}
 
	private void peparePageUpdating() {
		CoapClient client = new CoapClient( );
		String url =  "coap://" + configurator.getHostAddr() + ":" + configurator.getPort() + 
				"/" + configurator.getCtxqadest() + "/smartbell";
		System.out.println("CoapObserver | url=" + "url.toString()");
	    client.setURI(url.toString());
		client.setTimeout( 1000L );
	    CoapResponse respGet  = client.get( ); //CoapResponse
		if( respGet != null )
			System.out.println("CoapObserver | createConnection doing  get | CODE=  ${respGet.code} content=${respGet.getResponseText()}");
		else
			System.out.println("CoapObserver | url= " + url.toString() + " FAILURE");
    	client.observe(new CoapHandler() {
			@Override
			public void onLoad(CoapResponse response) {
				System.out.println("TearoomState changed!" + response.getResponseText());
				simpMessagingTemplate.convertAndSend(WebSocketConfig.topicForClient, 
						new ResourceRep("" + HtmlUtils.htmlEscape(response.getResponseText())  ));
			}
			
			@Override
			public void onError() {
				System.out.println("TearoomState error!");
			}
		});
	}
	
	/*
	 * INTERACTION WITH THE BUSINESS LOGIC			
	 */
	protected void doBusinessJob( String moveName, Model viewmodel) {
		try {
			// messaggi che vengono inviati
			// cliente 1
			if( moveName.equalsIgnoreCase("request1")) {
				ApplMessage msg = MsgUtil.buildRequest("web", "clientRingEntryRequest", "clientRingEntryRequest(1)", "smartbell" );
				connQakSupport.forwardEnter(msg);		
			}
			else if (moveName.equalsIgnoreCase("clientready1")){
				ApplMessage msg = MsgUtil.buildDispatch("web", "clientOrderReady", "clientOrderReady(1)", configurator.getQakdest() );
				connQakSupport.forward( msg );
			}
			else if (moveName.equalsIgnoreCase("order1")) {
				ApplMessage msg = MsgUtil.buildDispatch("web", "clientOrder", "clientOrder(1,teapesca)", configurator.getQakdest() );
				connQakSupport.forward( msg );
			}
			else if (moveName.equalsIgnoreCase("payment1")){
				ApplMessage msg = MsgUtil.buildDispatch("web", "clientPaymentReady", "clientPaymentReady(1)", configurator.getQakdest() );
				connQakSupport.forward( msg );
			}
			// cliente 2
			else if( moveName.equalsIgnoreCase("request2")) {
				ApplMessage msg = MsgUtil.buildRequest("web", "clientRingEntryRequest", "clientRingEntryRequest(2)", "smartbell" );
				connQakSupport.forwardEnter(msg);
			}
			else if (moveName.equalsIgnoreCase("clientready2")){
				ApplMessage msg = MsgUtil.buildDispatch("web", "clientOrderReady", "clientOrderReady(2)", configurator.getQakdest() );
				connQakSupport.forward( msg );
			}
			else if (moveName.equalsIgnoreCase("order2")) {
				ApplMessage msg = MsgUtil.buildDispatch("web", "clientOrder", "clientOrder(2,tealimone)", configurator.getQakdest() );
				connQakSupport.forward( msg );
			}
			else if (moveName.equalsIgnoreCase("payment2")) {
				ApplMessage msg = MsgUtil.buildDispatch("web", "clientPaymentReady", "clientPaymentReady(2)", configurator.getQakdest() );
				connQakSupport.forward( msg );
			}
			// cliente 3
			else if( moveName.equalsIgnoreCase("request3")) {
				ApplMessage msg = MsgUtil.buildRequest("web", "clientRingEntryRequest", "clientRingEntryRequest(3)", "smartbell" );
				connQakSupport.forwardEnter(msg);		
			}
			else if (moveName.equalsIgnoreCase("clientready3")){
				ApplMessage msg = MsgUtil.buildDispatch("web", "clientOrderReady", "clientOrderReady(3)", configurator.getQakdest() );
				connQakSupport.forward( msg );
			}
			else if (moveName.equalsIgnoreCase("order3")) {
				ApplMessage msg = MsgUtil.buildDispatch("web", "clientOrder", "clientOrder(3,teadario)", configurator.getQakdest() );
				connQakSupport.forward( msg );
			}
			else if (moveName.equalsIgnoreCase("payment3")) {
				ApplMessage msg = MsgUtil.buildDispatch("web", "clientPaymentReady", "clientPaymentReady(3)", configurator.getQakdest() );
				connQakSupport.forward( msg );
			}
			//WAIT for command completion ...
			Thread.sleep(400);  //QUITE A LONG TIME ...
			if( viewmodel != null ) {
				ResourceRep rep = getWebPageRep();
				viewmodel.addAttribute("arg", "Current Robot State:  "+rep.getContent());
			}
		} catch (Exception e) {
			System.out.println("------------------- RobotController doBusinessJob ERROR=" + e.getMessage()  );
			//e.printStackTrace();
		}		
	}

    @ExceptionHandler 
    public ResponseEntity<String> handle(Exception ex) {
    	HttpHeaders responseHeaders = new HttpHeaders();
        return new ResponseEntity<String>(
        		"RobotController ERROR " + ex.getMessage(), responseHeaders, HttpStatus.CREATED);
    }

/* ----------------------------------------------------------
   Message-handling Controller
  ----------------------------------------------------------
 */
//	@MessageMapping("/hello")
//	@SendTo("/topic/display")
//	public ResourceRep greeting(RequestMessageOnSock message) throws Exception {
//		Thread.sleep(1000); // simulated delay
//		return new ResourceRep("Hello by AN, " + HtmlUtils.htmlEscape(message.getName()) + "!");
//	}
	
	@MessageMapping("/move")
 	@SendTo("/topic/display")
 	public ResourceRep backtoclient(RequestMessageOnSock message) throws Exception {
// 		ApplMessage msg = MsgUtil.buildDispatch("web", "cmd", "cmd("+message.getName()+")", "basicrobot" );
//		connQakSupport.forward( msg );
//		System.out.println("------------------- RobotController forward=" + msg  );
		doBusinessJob(message.getName(), null);
//		//WAIT for command completion ...
//		Thread.sleep(400);
		return getWebPageRep();
 	}
	
	@MessageMapping("/update")
	@SendTo("/topic/display")
	public ResourceRep updateTheMap(@Payload String message) {
		ResourceRep rep = getWebPageRep();
		return rep;
	}

	public ResourceRep getWebPageRep()   {
		String resourceRep = connQakSupport.readRep();
		System.out.println("------------------- RobotController resourceRep=" + resourceRep  );
		return new ResourceRep("" + HtmlUtils.htmlEscape(resourceRep)  );
	}
	
  
 
	
 
/*
 * The @MessageMapping annotation ensures that, 
 * if a message is sent to the /hello destination, the greeting() method is called.    
 * The payload of the message is bound to a HelloMessage object, which is passed into greeting().
 * 
 * Internally, the implementation of the method simulates a processing delay by causing 
 * the thread to sleep for one second. 
 * This is to demonstrate that, after the client sends a message, 
 * the server can take as long as it needs to asynchronously process the message. 
 * The client can continue with whatever work it needs to do without waiting for the response.
 * 
 * After the one-second delay, the greeting() method creates a Greeting object and returns it. 
 * The return value is broadcast to all subscribers of /topic/display, 
 * as specified in the @SendTo annotation. 
 * Note that the name from the input message is sanitized, since, in this case, 
 * it will be echoed back and re-rendered in the browser DOM on the client side.
 */
    
 
/*
 * curl --location --request POST 'http://localhost:8080/move' --header 'Content-Type: text/plain' --form 'move=l'	
 * curl -d move=r localhost:8080/move
 */
}

