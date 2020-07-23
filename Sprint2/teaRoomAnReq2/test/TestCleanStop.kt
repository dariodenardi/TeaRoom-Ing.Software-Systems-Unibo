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

class TestCleanStop {
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
	
	fun check(x: String){		
		if( waitermind != null ){
			println(" --- checkHome --- ${waitermind!!.geResourceRep()}")
			assertTrue( waitermind!!.geResourceRep() == "$x")
		}  
	}
	
	@Test
	fun testWaiter(){
	 	runBlocking{
 			while( waitermind == null ){
				println("waits for waiterengine ... ")
				delay(initDelayTime)  //time for robot to start
				waitermind = it.unibo.kactor.sysUtil.getActor("waitermind")
 			}
			while(waiterengine == null){
				println("waits for waiterengine...")
				delay(initDelayTime)
				waiterengine = it.unibo.kactor.sysUtil.getActor("waiterengine")
			}
			
			delay(15000)
			check("tableClearing")
			delay(15000)
			check("tableSanitizing")
			MsgUtil.sendMsg(MsgUtil.buildRequest("waitermind","smartbellEntryRequest","smartbellEntryRequest(1)","waitermind"),waitermind!!)
			delay(30000)
			check("tableSanitizing")
			
			MsgUtil.sendMsg("end","end","end",waitermind!!)
 			if( waitermind != null ) waitermind!!.waitTermination()
  		}
	 	println("testWaiterPosition BYE  ")  
	}
}

