/* Generated by AN DISI Unibo */ 
package it.unibo.maxstaytime2

import it.unibo.kactor.*
import alice.tuprolog.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
	
class Maxstaytime2 ( name: String, scope: CoroutineScope  ) : ActorBasicFsm( name, scope ){

	override fun getInitialState() : String{
		return "s0"
	}
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi			
	override fun getBody() : (ActorBasicFsm.() -> Unit){
		
				val TimeMaxStay 	= 90000L
				var StartTime 		= 0L	
				var TimerDone 		= 0L
				var TimerGlobalDone = 0L
				var TimeAfterResume = 0L
				var TimerToReturn   = 0L
		return { //this:ActionBasciFsm
				state("s0") { //this:State
					action { //it:State
						println("maxstaytime2		|| START")
						updateResourceRep( "start"  
						)
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
				state("waitCmd") { //this:State
					action { //it:State
						updateResourceRep( "waitCmd2"  
						)
					}
					 transition(edgeName="t0111",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t0112",targetState="waitCmd",cond=whenDispatch("stopTimer"))
					transition(edgeName="t0113",targetState="resume",cond=whenDispatch("resumeTimer"))
					transition(edgeName="t0114",targetState="returnTimerDone",cond=whenRequest("getMaxStayTimeLeftReq"))
				}	 
				state("newTimer") { //this:State
					action { //it:State
						StartTime = getCurrentTime()
						 
									TimerGlobalDone = 0 
						updateResourceRep( "StartTimerCount2"  
						)
						stateTimer = TimerActor("timer_newTimer", 
							scope, context!!, "local_tout_maxstaytime2_newTimer", TimeMaxStay )
					}
					 transition(edgeName="t1115",targetState="timerExpired",cond=whenTimeout("local_tout_maxstaytime2_newTimer"))   
					transition(edgeName="t1116",targetState="stop",cond=whenDispatch("stopTimer"))
					transition(edgeName="t1117",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t1118",targetState="returnTimerDone",cond=whenRequest("getMaxStayTimeLeftReq"))
				}	 
				state("returnTimerDone") { //this:State
					action { //it:State
						TimerDone = getDuration(StartTime)
						 
									TimerGlobalDone += TimerDone
									TimerToReturn = TimeMaxStay - TimerGlobalDone
						updateResourceRep( "TimerDone2 $TimerToReturn "  
						)
						answer("getMaxStayTimeLeftReq", "getMaxStayTimeLeftReply", "getMaxStayTimeLeftReply($TimerToReturn)"   )  
					}
					 transition( edgeName="goto",targetState="resume", cond=doswitch() )
				}	 
				state("stop") { //this:State
					action { //it:State
						TimerDone = getDuration(StartTime)
						 TimerGlobalDone += TimerDone  
						updateResourceRep( "Stop2 $TimerGlobalDone "  
						)
					}
					 transition(edgeName="t2119",targetState="resume",cond=whenDispatch("resumeTimer"))
					transition(edgeName="t2120",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t2121",targetState="returnTimerDoneStop",cond=whenRequest("getMaxStayTimeLeftReq"))
				}	 
				state("returnTimerDoneStop") { //this:State
					action { //it:State
						 
									TimerToReturn = TimeMaxStay - TimerGlobalDone
						updateResourceRep( "Return2 $TimerGlobalDone "  
						)
						answer("getMaxStayTimeLeftReq", "getMaxStayTimeLeftReply", "getMaxStayTimeLeftReply($TimerToReturn)"   )  
					}
					 transition(edgeName="t2122",targetState="resume",cond=whenDispatch("resumeTimer"))
					transition(edgeName="t2123",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t2124",targetState="returnTimerDoneStop",cond=whenRequest("getMaxStayTimeLeftReq"))
				}	 
				state("resume") { //this:State
					action { //it:State
						 TimeAfterResume = TimeMaxStay - TimerGlobalDone  
						updateResourceRep( "Resume2 $TimeAfterResume "  
						)
						StartTime = getCurrentTime()
						stateTimer = TimerActor("timer_resume", 
							scope, context!!, "local_tout_maxstaytime2_resume", TimeAfterResume )
					}
					 transition(edgeName="t3125",targetState="timerExpired",cond=whenTimeout("local_tout_maxstaytime2_resume"))   
					transition(edgeName="t3126",targetState="stop",cond=whenDispatch("stopTimer"))
					transition(edgeName="t3127",targetState="newTimer",cond=whenDispatch("startTimer"))
					transition(edgeName="t3128",targetState="returnTimerDone",cond=whenRequest("getMaxStayTimeLeftReq"))
				}	 
				state("timerExpired") { //this:State
					action { //it:State
						updateResourceRep( "TimerExpired2 "  
						)
						println("EXPIRED2")
						forward("maxStayTimerExpired", "maxStayTimerExpired(2)" ,"maxstaytime" ) 
					}
					 transition( edgeName="goto",targetState="waitCmd", cond=doswitch() )
				}	 
			}
		}
}
