/* Generated by AN DISI Unibo */ 
package it.unibo.waiter

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Waiter ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "startState"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
				
				val DoClear = 1
				val DoSanitize = 2
				val DoClean = 3
				
				val CollectTime = 4000L
				val DelayTakeDrink = 2000L
				val DelayTakeClient = 2000L
				
				data class Table(var state: String ="tableClean") { }
				val table1 = Table()
				val table2 = Table()
				var stateTable1 = "tableClean"
				var stateTable2 = "tableClean"
		return { //this:ActionBasciFsm
				state("startState") { //this:State
					action { //it:State
						println("waiter 	|| START")
						updateResourceRep( "startState"  
						)
					}
					 transition( edgeName="goto",targetState="rest", cond=doswitch() )
				}	 
				state("rest") { //this:State
					action { //it:State
						println("waiter		|| rest")
						updateResourceRep( "rest" 
						)
					}
					 transition(edgeName="t10",targetState="accept",cond=whenRequest("smartbellEntryRequest"))
					transition(edgeName="t11",targetState="reachTableOrder",cond=whenDispatch("clientOrderReady"))
					transition(edgeName="t12",targetState="reachBar",cond=whenDispatch("barmanOrderReady"))
					transition(edgeName="t13",targetState="endState",cond=whenDispatch("end"))
					transition(edgeName="t14",targetState="reachTableCollect",cond=whenDispatch("clientPaymentReady"))
				}	 
				state("reachHome") { //this:State
					action { //it:State
						println("waiter		|| reachHome")
						updateResourceRep( "reachHome"  
						)
					}
					 transition( edgeName="goto",targetState="rest", cond=doswitch() )
				}	 
				state("accept") { //this:State
					action { //it:State
						println("waiter		|| accept")
						updateResourceRep( "accept"  
						)
						if( checkMsgContent( Term.createTerm("smartbellEntryRequest(ID)"), Term.createTerm("smartbellEntryRequest(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("waiter 	|| Ricevuta richiesta da ID: ${ payloadArg(0) }")
								answer("smartbellEntryRequest", "smartbellEntryReply", "smartbellEntryReply(${payloadArg(0)})"   )  
						}
					}
					 transition( edgeName="goto",targetState="reachDoor", cond=doswitch() )
				}	 
				state("reachDoor") { //this:State
					action { //it:State
						println("waiter 	|| reachDoor")
						updateResourceRep( "reachDoor"  
						)
					}
					 transition( edgeName="goto",targetState="convoyTable", cond=doswitch() )
				}	 
				state("convoyTable") { //this:State
					action { //it:State
						println("waiter 	|| convoyTable")
						updateResourceRep( "convoyTable" 
						)
						delay(DelayTakeClient)
						 table1.state="tableoccupied"  
					}
					 transition( edgeName="goto",targetState="reachHome", cond=doswitch() )
				}	 
				state("reachTableOrder") { //this:State
					action { //it:State
						println("waiter 	|| reachTableOrder")
						updateResourceRep( "reachTableOrder" 
						)
						if( checkMsgContent( Term.createTerm("clientOrderReady(ID)"), Term.createTerm("clientOrderReady(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("waitermind 	|| client pronto per ordinare ID : ${payloadArg(0)}")
						}
					}
					 transition( edgeName="goto",targetState="waitOrderClient", cond=doswitch() )
				}	 
				state("waitOrderClient") { //this:State
					action { //it:State
						println("waiter 	|| waitOrderClient")
						updateResourceRep( "waitOrderClient"  
						)
					}
					 transition(edgeName="t65",targetState="trasmit",cond=whenDispatch("clientOrder"))
				}	 
				state("trasmit") { //this:State
					action { //it:State
						println("waiter 	|| trasmit")
						updateResourceRep( "trasmit" 
						)
						if( checkMsgContent( Term.createTerm("clientOrder(ID,ORDER)"), Term.createTerm("clientOrder(ID,ORDER)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("waiter 	|| ricevuto ordine ID,ORDER: ${payloadArg(0)},${payloadArg(1)} ")
								forward("waiterOrderForward", "waiterOrderForward(${payloadArg(0)},${payloadArg(1)})" ,"barman" ) 
						}
					}
					 transition( edgeName="goto",targetState="reachHome", cond=doswitch() )
				}	 
				state("reachBar") { //this:State
					action { //it:State
						println("waiter 	|| reachBar")
						updateResourceRep( "reachBar" 
						)
						if( checkMsgContent( Term.createTerm("barmanOrderReady(ID)"), Term.createTerm("barmanOrderReady(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("waiter 	|| order ready client ID: ${payloadArg(0)}")
						}
					}
					 transition( edgeName="goto",targetState="serveTable", cond=doswitch() )
				}	 
				state("serveTable") { //this:State
					action { //it:State
						println("waiter 	|| serveTable")
						updateResourceRep( "serveTable" 
						)
						delay(DelayTakeDrink)
					}
					 transition( edgeName="goto",targetState="reachHome", cond=doswitch() )
				}	 
				state("reachTableCollect") { //this:State
					action { //it:State
						println("waiter 	|| reachTableCollect")
						updateResourceRep( "reachTableCollect" 
						)
						if( checkMsgContent( Term.createTerm("clientPaymentReady(ID)"), Term.createTerm("clientPaymentReady(ID)"), 
						                        currentMsg.msgContent()) ) { //set msgArgList
								println("waiter 	|| client wants to pay ID : ${payloadArg(0)}")
						}
					}
					 transition( edgeName="goto",targetState="collect", cond=doswitch() )
				}	 
				state("collect") { //this:State
					action { //it:State
						println("waiter 	|| collect")
						updateResourceRep( "collect" 
						)
						delay(CollectTime)
					}
					 transition( edgeName="goto",targetState="convoyExit", cond=doswitch() )
				}	 
				state("convoyExit") { //this:State
					action { //it:State
						println("waiter		|| convoyExit")
						updateResourceRep( "convoyExit" 
						)
						 table1.state="tableDirty"  
					}
					 transition( edgeName="goto",targetState="reachTableClean", cond=doswitch() )
				}	 
				state("reachTableClean") { //this:State
					action { //it:State
						println("waiter		|| reachTableClean")
						updateResourceRep( "reachTableClean" 
						)
						delay(1000) 
					}
					 transition( edgeName="goto",targetState="tableClearing", cond=doswitch() )
				}	 
				state("tableClearing") { //this:State
					action { //it:State
						println("waiter		|| tableClearing")
						updateResourceRep( "tableClearing" 
						)
						 table1.state = "tableClearing"  
					}
					 transition( edgeName="goto",targetState="tableSanitizing", cond=doswitch() )
				}	 
				state("tableSanitizing") { //this:State
					action { //it:State
						println("waiter		|| tableSanitizing")
						updateResourceRep( "tableSanitizing" 
						)
						 table1.state = "tableSanitizing"  
					}
					 transition( edgeName="goto",targetState="tableCleaning", cond=doswitch() )
				}	 
				state("tableCleaning") { //this:State
					action { //it:State
						println("waiter		|| tableCleaning")
						updateResourceRep( "tableCleaning" 
						)
						 table1.state = "tableCleaning"  
					}
					 transition( edgeName="goto",targetState="updateTableCleaned", cond=doswitch() )
				}	 
				state("updateTableCleaned") { //this:State
					action { //it:State
						println("waiter		|| updateTableCleaned")
						updateResourceRep( "updateTableCleaned"  
						)
						 table1.state = "tableCleaned"  
					}
					 transition( edgeName="goto",targetState="reachHome", cond=doswitch() )
				}	 
				state("endState") { //this:State
					action { //it:State
						println("waiter || TERMINATES")
						terminate(0)
					}
				}	 
			}
		}
}
