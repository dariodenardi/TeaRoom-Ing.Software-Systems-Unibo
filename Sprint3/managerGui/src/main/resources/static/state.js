var state = ""
var waiter = ""
var tableState1 = ""
var tableState2 = ""
var barman = ""

function loadstate(trigger){
state = document.getElementById("applmsgs").value
localStorage.setItem("state",state)
let rows = state.split("\n")
waiter =rows[0].substring(7,rows[0].length-1)
var tableStates = rows[1].substring(17).split(",")
var table1 = tableStates[0].replace("[teatable1(","")
tableState1 = table1.substring(0,table1.length-1)
tableState2 = tableStates[1].replace("teatable2(","").replace(")])","")
barman = rows[2].substring(7,rows[2].length-1)

load()
}

function load(){
if(localStorage.getItem("state")){
	document.getElementById("applmsgs").value  = localStorage.getItem("state");
}

document.getElementById("waiterstate").value = waiter
document.getElementById("barman").value = tableState1
document.getElementById("table1").value = tableState2
document.getElementById("table2").value = barman
document.getElementById("applmsgs").value = state
}

