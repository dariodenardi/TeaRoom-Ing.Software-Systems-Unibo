%====================================================================================
% tearoom2 description   
%====================================================================================
context(ctxtearoom2, "localhost",  "TCP", "8060").
context(ctxtearoom, "127.0.0.1",  "TCP", "8040").
 qactor( waitermind, ctxtearoom, "external").
  qactor( smartbell, ctxtearoom, "external").
  qactor( client, ctxtearoom2, "it.unibo.client.Client").
