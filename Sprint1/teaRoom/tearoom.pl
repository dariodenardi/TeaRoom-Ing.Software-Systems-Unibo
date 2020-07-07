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
