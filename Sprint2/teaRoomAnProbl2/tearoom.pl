%====================================================================================
% tearoom description   
%====================================================================================
context(ctxtearoom, "127.0.0.1",  "TCP", "8040").
context(ctxbasicrobot, "localhost",  "TCP", "8020").
 qactor( basicrobot, ctxbasicrobot, "external").
  qactor( waitermind, ctxtearoom, "it.unibo.waitermind.Waitermind").
  qactor( smartbell, ctxtearoom, "it.unibo.smartbell.Smartbell").
  qactor( waiterengine, ctxtearoom, "it.unibo.waiterengine.Waiterengine").
  qactor( barman, ctxtearoom, "it.unibo.barman.Barman").
  qactor( timercleaning, ctxtearoom, "it.unibo.timercleaning.Timercleaning").
  qactor( maxstaytime, ctxtearoom, "it.unibo.maxstaytime.Maxstaytime").
  qactor( maxstaytime1, ctxtearoom, "it.unibo.maxstaytime1.Maxstaytime1").
  qactor( maxstaytime2, ctxtearoom, "it.unibo.maxstaytime2.Maxstaytime2").
  qactor( tearoomglobalstate, ctxtearoom, "it.unibo.tearoomglobalstate.Tearoomglobalstate").
