%====================================================================================
% tearoom description   
%====================================================================================
context(ctxtearoom, "127.0.0.1",  "TCP", "8040").
 qactor( waiter, ctxtearoom, "it.unibo.waiter.Waiter").
  qactor( smartbell, ctxtearoom, "it.unibo.smartbell.Smartbell").
  qactor( barman, ctxtearoom, "it.unibo.barman.Barman").
