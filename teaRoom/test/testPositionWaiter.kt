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

class TestWaiterPosition {
	var waitermind            : ActorBasic? = null
	var waiterengine		  : ActorBasic? = null
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
	
	fun checkPosition(x: String, y: String){		
		if( waitermind != null ){
			println(" --- checkHome --- ${waitermind!!.geResourceRep()}")
			assertTrue( waitermind!!.geResourceRep() == "$x,$y")
		}  
	}
	
	@Test
	fun testRobotboundary(){
	 	runBlocking{
 			while( waitermind == null ){
				println("testWaiterPosition waits for waiterengine ... ")
				delay(initDelayTime)  //time for robot to start
				waitermind = it.unibo.kactor.sysUtil.getActor("waitermind")
 			}
			while(waiterengine == null){
				println("testWaiterPosition waits for waiterengine...")
				delay(initDelayTime)
				waiterengine = it.unibo.kactor.sysUtil.getActor("waiterengine")
			}
			
			MsgUtil.sendMsg(MsgUtil.buildRequest("waitermind","smartbellEntryRequest","smartbellEntryRequest","waitermind"),waitermind!!)
 			delay(15000)
			checkPosition("0","4")
			println("Position Entrance Checked")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","clientOrderReady","clientOrderReady","waitermind"),waitermind!!)
			delay(25000)
			checkPosition("2","2")
			println("Position Tavolo Checked")
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","clientOrder","clientOrder","waitermind"),waitermind!!)
			
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","barmanOrderReady","barmanOrderReady","waitermind"),waitermind!!)
			delay(20000)
			checkPosition("6","0")
			println("Position Barman Checked")
			
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waitermind","clientPaymentReady","clientPaymentReady","waitermind"),waitermind!!)
			delay(30000)
			checkPosition("2","2")
			println("Position Tavolo Checked")
			delay(20000)
			checkPosition("6","4")
			println("Position Exit Checked")
			delay(20000)
			checkPosition("2","2")
			println("Position Tavolo Checked")
			delay(10000)
			MsgUtil.sendMsg("end","end","end",waitermind!!)
 			if( waitermind != null ) waitermind!!.waitTermination()
  		}
	 	println("testWaiterPosition BYE  ")  
	}
}

