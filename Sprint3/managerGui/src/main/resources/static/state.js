var stompClient = null;
var hostAddr = "http://localhost:7060/move";

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

function replaceAll(str, cerca, sostituisci) {
  return str.split(cerca).join(sostituisci);
}

function showMsg(message) {
	//if(curMsg === message)
	//	return;
	
	var str = replaceAll(message, "&quot;", '"');
	
	var obj = JSON.parse(str);
	
	console.log(obj.Waiter);

	console.log(obj.Barman);

	document.getElementById("waiterstate").innerHTML = obj.Waiter;
	document.getElementById("barman").innerHTML = obj.Barman;
    document.getElementById("table1").innerHTML = obj.TABLE1;
	document.getElementById("table2").innerHTML = obj.TABLE2;
	document.getElementById("accepted").innerHTML = obj.ClientAccepted;
	document.getElementById("rejected").innerHTML = obj.ClientRejected;
}

function sendTheMove(move){
	console.log("sendTheMove " + move);
    stompClient.send("/app/move", {}, JSON.stringify({'name': move }));
}

// SOCKET.IO

$( "#hh" ).click(function() {  sendTheMove("h") });
$( "#ww" ).click(function() {  sendTheMove("w") });
$( "#ss" ).click(function() {  sendTheMove("s") });
$( "#rr" ).click(function() {  sendTheMove("r") });
$( "#ll" ).click(function() {  sendTheMove("l") });
$( "#xx" ).click(function() {  sendTheMove("x") });
$( "#zz" ).click(function() {  sendTheMove("z") });