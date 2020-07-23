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


class TestMaxStayTime {
	var maxstaytime1          : ActorBasic? = null
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
		if( maxstaytime1 != null ){
			println(" --- check--- ${maxstaytime1!!.geResourceRep()}")
			assertTrue( maxstaytime1!!.geResourceRep() == "$x")
		}  
	}
	
@Test
	fun testPosition(){
	 	runBlocking{
 			while( maxstaytime1 == null ){
				println("waits  ... ")
				delay(initDelayTime)  //time for robot to start
				maxstaytime1 = it.unibo.kactor.sysUtil.getActor("maxstaytime1")
 			}
			println(maxstaytime1!!.geResourceRep())
			MsgUtil.sendMsg(MsgUtil.buildDispatch("maxstaytime1","startTimer","startTimer","maxstaytime1"),maxstaytime1!!)
 			delay(2000)
			println(maxstaytime1!!.geResourceRep())
			MsgUtil.sendMsg(MsgUtil.buildDispatch("maxstaytime1","stopTimer","stopTimer","maxstaytime1"),maxstaytime1!!)
 			delay(2000)
			println(maxstaytime1!!.geResourceRep())
			MsgUtil.sendMsg(MsgUtil.buildRequest("maxstaytime1","getMaxStayTimeLeftReq","getMaxStayTimeLeftReq","maxstaytime1"),maxstaytime1!!)
			delay(2000)
			println(maxstaytime1!!.geResourceRep())
			MsgUtil.sendMsg(MsgUtil.buildDispatch("maxstaytime1","resumeTimer","resumeTimer","maxstaytime1"),maxstaytime1!!)
			delay(2000)
			println(maxstaytime1!!.geResourceRep())
			//sono in resume, difficile da beccare
			MsgUtil.sendMsg(MsgUtil.buildRequest("maxstaytime1","getMaxStayTimeLeftReq","getMaxStayTimeLeftReq","maxstaytime1"),maxstaytime1!!)
			delay(2000)
			println(maxstaytime1!!.geResourceRep())
			
			
  		}
	 	println("testWaiterPosition BYE  ")  
	}
}