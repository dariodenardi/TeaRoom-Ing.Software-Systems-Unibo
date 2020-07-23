%====================================================================================
% client description   
%====================================================================================
context(ctxclient, "localhost",  "TCP", "8060").
context(ctxtearoom, "127.0.0.1",  "TCP", "8040").
 qactor( waitermind, ctxtearoom, "external").
  qactor( smartbell, ctxtearoom, "external").
  qactor( client, ctxclient, "it.unibo.client.Client").
