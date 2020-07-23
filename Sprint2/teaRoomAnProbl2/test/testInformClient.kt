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


class TestInformClient {
	var waitermind          : ActorBasic? = null
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
			println(" --- check--- ${waitermind!!.geResourceRep()}")
			assertTrue( waitermind!!.geResourceRep().contains("$x"))
		}  
	}
	
@Test
	fun testInformClient(){
	 	runBlocking{
 			while( waitermind == null ){
				println("waits  ... ")
				delay(initDelayTime)  //time for robot to start
				waitermind = it.unibo.kactor.sysUtil.getActor("waitermind")
 			}
			delay(2000)
			//aggirando lo smartbell non vediamo l'incremendo dei contatori rif. accett.
			MsgUtil.sendMsg(MsgUtil.buildRequest("waitermind","smartbellEntryRequest","smartbellEntryRequest(1)","waitermind"),waitermind!!)
			MsgUtil.sendMsg(MsgUtil.buildRequest("waitermind","smartbellEntryRequest","smartbellEntryRequest(2)","waitermind"),waitermind!!)
			MsgUtil.sendMsg(MsgUtil.buildRequest("waitermind","smartbellEntryRequest","smartbellEntryRequest(3)","waitermind"),waitermind!!)
 			delay(40000)
			check("inform")
			println(waitermind!!.geResourceRep())
			
			
  		}
	 	println("BYE")  
	}
}