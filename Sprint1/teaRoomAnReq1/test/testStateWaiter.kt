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

class TestWaiterPosition {
	var waiter          : ActorBasic? = null
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
		println("%%%  TestState terminate ")
	}
	
	fun checkState(state: String){		
		if( waiter != null ){
			println(" --- stato letto --- ${waiter!!.geResourceRep()}")
			assertTrue( waiter!!.geResourceRep() == "$state")
		}
	}
	
	@Test
	fun testStateWaiter(){
	 	runBlocking{
 			while( waiter == null ){
				println("waits for waiter ... ")
				delay(initDelayTime)  //time for robot to start
				waiter = it.unibo.kactor.sysUtil.getActor("waiter")
 			}
			
			MsgUtil.sendMsg(MsgUtil.buildRequest("waiter","smartbellEntryRequest","smartbellEntryRequest","waiter"),waiter!!)
 			delay(10000)
			checkState("reachDoor")
			println("reachDoor Checked")
			println("clicca invio per continuare") 
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waiter","clientOrderReady","clientOrderReady","waiter"),waiter!!)
			delay(25000)
			checkState("waitOrderClient")
			println("waitOrderClient Checked")
			println("clicca invio per continuare") 
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waiter","clientOrder","clientOrder","waiter"),waiter!!)
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waiter","barmanOrderReady","barmanOrderReady","waiter"),waiter!!)
			delay(20000)
			checkState("reachBar")
			println("reachBar Checked")
			println("clicca invio per continuare") 
			MsgUtil.sendMsg(MsgUtil.buildDispatch("waiter","clientPaymentReady","clientPaymentReady","waiter"),waiter!!)
			delay(15000)
			checkState("reachTableCollect")
			println("reachTableCollect Checked")
			println("clicca invio per continuare") 
			delay(10000)	
			MsgUtil.sendMsg("end","end","end",waiter!!)
 			if( waiter != null ) waiter!!.waitTermination()
  		}
	 	println("testWaiter BYE  ")  
	}
}

