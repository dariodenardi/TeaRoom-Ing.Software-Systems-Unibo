/* Generated by AN DISI Unibo */ 
package it.unibo.smartbell

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Smartbell ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		 
				val Temp_max = 42
				var ID_client = 1
				var N_client_rejected = 0
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("smartbell || START")
						updateResourceRep( "START"  
						)
					}
					 transition( edgeName="goto",targetState="waitRing", cond=doswitch() )
				}	 
				state("waitRing") { //this:State
					action { //it:State
						println("smartbell || waitRing")
						updateResourceRep( "waitRing"  
						)
					}
					 transition(edgeName="t06",targetState="checkTempClient",cond=whenRequest("clientRingEntryRequest"))
					transition(edgeName="t07",targetState="endState",cond=whenDispatch("end"))
				}	 
				state("checkTempClient") { //this:State
					action { //it:State
						  var ClientTemp = (Math.random()*6+35) 
						println("smartbell || checkTempClient")
						updateResourceRep( "checkTempClient"  
						)
						if(  ClientTemp < Temp_max  
						 ){println("smartbell || clienteAccettatoDaSmartBell || temperatura = $ClientTemp || id_client = $ID_client")
						request("smartbellEntryRequest", "smartbellEntryRequest($ID_client)" ,"waiter" )  
						 ID_client++  
						}
						else
						 {println("smartbell || clienteRifiutatoDaSmartBell || temperatura = $ClientTemp")
						 forward("smartbellClientRejected", "smartbellClientRejected($ClientTemp)" ,"smartbell" ) 
						 }
					}
					 transition(edgeName="t18",targetState="checkWaiterReply",cond=whenReply("smartbellEntryReply"))
					transition(edgeName="t19",targetState="clientRejected",cond=whenDispatch("smartbellClientRejected"))
				}	 
				state("checkWaiterReply") { //this:State
					action { //it:State
						if( checkMsgContent( Term.createTerm("smartbellEntryReply(ID)"), Term.createTerm("smartbellEntryReply(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("smartbell || ricevuta reply ID : ${ payloadArg(0) }")
								answer("clientRingEntryRequest", "clientRingEntryReply", "clientRingEntryReply(${payloadArg(0)})"   )  
						}
					}
					 transition( edgeName="goto",targetState="waitRing", cond=doswitch() )
				}	 
				state("clientRejected") { //this:State
					action { //it:State
						 N_client_rejected++  
						if( checkMsgContent( Term.createTerm("smartbellClientRejected(TEMP)"), Term.createTerm("smartbellClientRejected(TEMP)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								answer("clientRingEntryRequest", "clientRingEntryReply", "clientRingEntryReply(payloadArg(0))"   )  
						}
					}
					 transition( edgeName="goto",targetState="waitRing", cond=doswitch() )
				}	 
				state("endState") { //this:State
					action { //it:State
						println("smartbell || END")
						terminate(0)
					}
				}	 
			}
		}
}
