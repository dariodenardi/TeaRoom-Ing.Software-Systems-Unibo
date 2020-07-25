var stompClient = null;
var hostAddr = "http://localhost:7050/move";

//SIMULA UNA FORM che invia comandi POST
function sendRequestData( params, method) {
    method = method || "post"; // il metodo POST � usato di default
    //console.log(" sendRequestData  params=" + params + " method=" + method);
    var form = document.createElement("form");
    form.setAttribute("method", method);
    form.setAttribute("action", hostAddr);
    var hiddenField = document.createElement("input");
        hiddenField.setAttribute("type", "hidden");
        hiddenField.setAttribute("name", "move");
        hiddenField.setAttribute("value", params);
     	//console.log(" sendRequestData " + hiddenField.getAttribute("name") + " " + hiddenField.getAttribute("value"));
        form.appendChild(hiddenField);
    document.body.appendChild(form);
    console.log("body children num= "+document.body.children.length );
    form.submit();
    document.body.removeChild(form);
    console.log("body children num= "+document.body.children.length );
}

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/it-unibo-iss');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        //setConnected(true);
        stompClient.subscribe('/topic/display', function (msg) {
             showMsg(JSON.parse(msg.body).content);
             
        });
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

var curMsg = ""

function showMsg(message) {
	if(curMsg === message)
		return;
	//console.log(message );
	
	sessionStorage.setItem('message', message);
	
    if(message.toString().includes("welcome")){
    	
    	if (sessionStorage.getItem('button1') == "true") {
    		sessionStorage.setItem('client1', '1');
    		sessionStorage.setItem('button1', 'false');
    	} else if (sessionStorage.getItem('button2') == "true") {
    		sessionStorage.setItem('client2', '1');
    		sessionStorage.setItem('button2', 'false');
    	}  else if (sessionStorage.getItem('button3') == "true") {
    		sessionStorage.setItem('client3', '1');
    		sessionStorage.setItem('button3', 'false');
    	}
    	
    }
    else if(message.toString().includes("ci dispiace")){
    
    	if (sessionStorage.getItem('button1') == "true") {
    		sessionStorage.setItem('button1', 'false');
    	} else if (sessionStorage.getItem('button2') == "true") {
    		sessionStorage.setItem('button2', 'false');
    	}  else if (sessionStorage.getItem('button3') == "true") {
    		sessionStorage.setItem('button3', 'false');
    	}
    	
    }
    curMsg = message;
}

function load() {

	if(sessionStorage.getItem('message').includes("welcome") || sessionStorage.getItem('message').includes("ci dispiace")){
    	
    	document.getElementById("message").innerHTML = sessionStorage.getItem('message');
    	
    }
    
    var c1 = sessionStorage.getItem('client1');
    if (c1 == 1) {
        document.getElementById("request1").setAttribute("disabled", "disabled");
    }
    else if (c1 == 2) {
    	document.getElementById("request1").setAttribute("disabled", "disabled");
        document.getElementById("ready1").setAttribute("disabled", "disabled");
    }
    else if (c1 == 3) {
    	document.getElementById("request1").setAttribute("disabled", "disabled");
        document.getElementById("ready1").setAttribute("disabled", "disabled");
        document.getElementById("order1").setAttribute("disabled", "disabled");
    }
    else if (c1 == 4) {
    	document.getElementById("request1").setAttribute("disabled", "disabled");
        document.getElementById("ready1").setAttribute("disabled", "disabled");
        document.getElementById("order1").setAttribute("disabled", "disabled");
        document.getElementById("payment1").setAttribute("disabled", "disabled");
    }
    
    var c2 = sessionStorage.getItem('client2');
    if (c2 == 1) {
        document.getElementById("request2").setAttribute("disabled", "disabled");
    }
    else if (c2 == 2) {
    	document.getElementById("request2").setAttribute("disabled", "disabled");
        document.getElementById("ready2").setAttribute("disabled", "disabled");
    }
    else if (c2 == 3) {
    	document.getElementById("request2").setAttribute("disabled", "disabled");
        document.getElementById("ready2").setAttribute("disabled", "disabled");
        document.getElementById("order2").setAttribute("disabled", "disabled");
    }
    else if (c2 == 4) {
    	document.getElementById("request2").setAttribute("disabled", "disabled");
        document.getElementById("ready2").setAttribute("disabled", "disabled");
        document.getElementById("order2").setAttribute("disabled", "disabled");
        document.getElementById("payment2").setAttribute("disabled", "disabled");
    }
    
    var c3 = sessionStorage.getItem('client3');
    if (c3 == 1) {
        document.getElementById("request3").setAttribute("disabled", "disabled");
    }
    else if (c3 == 2) {
    	document.getElementById("request3").setAttribute("disabled", "disabled");
        document.getElementById("ready3").setAttribute("disabled", "disabled");
    }
    else if (c3 == 3) {
    	document.getElementById("request3").setAttribute("disabled", "disabled");
        document.getElementById("ready3").setAttribute("disabled", "disabled");
        document.getElementById("order3").setAttribute("disabled", "disabled");
    }
    else if (c3 == 4) {
    	document.getElementById("request3").setAttribute("disabled", "disabled");
        document.getElementById("ready3").setAttribute("disabled", "disabled");
        document.getElementById("order3").setAttribute("disabled", "disabled");
        document.getElementById("payment3").setAttribute("disabled", "disabled");
    }
}