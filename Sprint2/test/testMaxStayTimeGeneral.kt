package test

import org.junit.Before
import org.junit.After
import org.junit.Test
import org.junit.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.delay
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import it.unibo.kactor.ActorBasic
import it.unibo.kactor.MsgUtil
import it.unibo.kactor.MqttUtils
import it.unibo.kactor.ApplMessage
import it.unibo.kactor.ApplMessageType
import it.unibo.waiterengine.Waiterengine


class TestMaxStayTimeGeneral {
	var maxstaytime          : ActorBasic? = null
	var maxstaytime1          : ActorBasic? = null
	var maxstaytime2          : ActorBasic? = null
	//val mqttTest   	      = MqttUtils("test") 
	val initDelayTime     = 1000L
	
	@kotlinx.coroutines.ObsoleteCoroutinesApi
	@kotlinx.coroutines.ExperimentalCoroutinesApi
	@Before
	fun systemSetUp() {
   		kotlin.concurrent.thread(start = true) {
			it.unibo.ctxtearoom.main()
		}
	}

	@After
	fun terminate() {
		println("%%%  TestPosition terminate ")
	}
	
	fun check(x: String){		
		if( maxstaytime != null ){
			println(" --- check--- ${maxstaytime!!.geResourceRep()}")
			assertTrue( maxstaytime!!.geResourceRep().contains("$x"))
		}  
	}
	
@Test
	fun testMaxStayTime(){
	 	runBlocking{
 			while( maxstaytime == null ){
				println("waits maxstaytime  ... ")
				delay(initDelayTime)  //time for robot to start
				maxstaytime = it.unibo.kactor.sysUtil.getActor("maxstaytime")
 			}
			while( maxstaytime1 == null ){
				println("waits maxstaytime 1 ... ")
				delay(initDelayTime)  //time for robot to start
				maxstaytime1 = it.unibo.kactor.sysUtil.getActor("maxstaytime1")
 			}
			while( maxstaytime2 == null ){
				println("waits  maxstaytime 2... ")
				delay(initDelayTime)  //time for robot to start
				maxstaytime2 = it.unibo.kactor.sysUtil.getActor("maxstaytime2")
 			}
			
			delay(5000)
			//println(maxstaytime1!!.geResourceRep())
			check("waitCmd1")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("maxstaytime","startTimer","startTimer(1)","maxstaytime"),maxstaytime!!)
			//
			MsgUtil.sendMsg(MsgUtil.buildDispatch("maxstaytime","startTimer","startTimer(2)","maxstaytime"),maxstaytime!!)
			//
 			delay(2000)
			//println(maxstaytime1!!.geResourceRep())
			//println(maxstaytime2!!.geResourceRep())
			check("StartTimerCount1")
			check("StartTimerCount2")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("maxstaytime","stopTimer","stopTimer(1)","maxstaytime"),maxstaytime!!)
 			delay(2000)
			//println(maxstaytime1!!.geResourceRep())
			check("Stop1")
			MsgUtil.sendMsg(MsgUtil.buildRequest("maxstaytime","askMaxStayTimeLeftReq","askMaxStayTimeLeftReq(1)","maxstaytime"),maxstaytime!!)
			delay(2000)
			//println(maxstaytime1!!.geResourceRep())
			check("Return1")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("maxstaytime","resumeTimer","resumeTimer(1)","maxstaytime"),maxstaytime!!)
			delay(2000)
			//println(maxstaytime1!!.geResourceRep())
			check("Resume1")
			MsgUtil.sendMsg(MsgUtil.buildRequest("maxstaytime","askMaxStayTimeLeftReq","askMaxStayTimeLeftReq(1)","maxstaytime"),maxstaytime!!)
			delay(2000)
			//println(maxstaytime1!!.geResourceRep())
			check("Resume1")
			
			
			//println(maxstaytime2!!.geResourceRep())
			check("StartTimerCount2")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("maxstaytime","stopTimer","stopTimer(2)","maxstaytime"),maxstaytime!!)
 			delay(2000)
			//println(maxstaytime2!!.geResourceRep())
			check("Stop2")
			MsgUtil.sendMsg(MsgUtil.buildRequest("maxstaytime","askMaxStayTimeLeftReq","askMaxStayTimeLeftReq(2)","maxstaytime"),maxstaytime!!)
			delay(2000)
			//println(maxstaytime2!!.geResourceRep())
			check("Return2")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("maxstaytime","resumeTimer","resumeTimer(2)","maxstaytime"),maxstaytime!!)
			delay(2000)
			//println(maxstaytime2!!.geResourceRep())
			check("Resume2")
			MsgUtil.sendMsg(MsgUtil.buildRequest("maxstaytime","askMaxStayTimeLeftReq","askMaxStayTimeLeftReq(2)","maxstaytime"),maxstaytime!!)
			delay(2000)
			//println(maxstaytime2!!.geResourceRep())
			check("Resume2")
			
			
			
			//TIMER MESSI A 90, non più a 40
			delay(40000)
			println(maxstaytime!!.geResourceRep())
			delay(2000)
			println(maxstaytime1!!.geResourceRep())
			println(maxstaytime2!!.geResourceRep())
			MsgUtil.sendMsg(MsgUtil.buildRequest("maxstaytime","askMaxStayTimeLeftReq","askMaxStayTimeLeftReq(1)","maxstaytime"),maxstaytime!!)
			MsgUtil.sendMsg(MsgUtil.buildRequest("maxstaytime","askMaxStayTimeLeftReq","askMaxStayTimeLeftReq(2)","maxstaytime"),maxstaytime!!)
			delay(2000)
  		}
	 	println("testWaiterPosition BYE")  
	}
}