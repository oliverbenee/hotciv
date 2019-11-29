package hotciv.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import frds.broker.RequestObject;
import hotciv.framework.Game;

public class LocalMethodClientRequestHandler implements ClientRequestHandler {
  private final Invoker invoker;
  // field used for test spy
  private ReplyObject lastreply;

  public LocalMethodClientRequestHandler(Invoker invoker) {
    this.invoker = invoker;
  }

  @Override
  public ReplyObject sendToServer(RequestObject requestObject) {
    System.out.println("--> " + requestObject);
    ReplyObject reply = invoker.handleRequest(requestObject.getObjectId(), requestObject.getOperationName()
            , requestObject.getPayload());
    System.out.println(" --< " + reply);
    // field used for test spy
    lastreply = reply;
    return reply;
  }

  @Override
  public void setServer(String hostname, int port) {

  }

  @Override
  public void close() {

  }

  public ReplyObject getLastReply() {return lastreply;}
}
