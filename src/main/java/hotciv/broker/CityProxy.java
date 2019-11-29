package hotciv.broker;

import frds.broker.ClientProxy;
import hotciv.framework.City;
import hotciv.framework.Player;
import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;

public class CityProxy implements City, ClientProxy {

  private String objectId;
  private final Requestor requestor;

  public CityProxy(String objectId, Requestor crh) {
    this.objectId = objectId;
    this.requestor = crh;
  }

  @Override
  public Player getOwner() {
    return requestor.sendRequestAndAwaitReply(
            getId(),
            OperationConstants.CITY_GET_OWNER,
            Player.class);
  }

  @Override
  public int getSize() {
    int size = requestor.sendRequestAndAwaitReply(getId(), OperationConstants.CITY_GET_SIZE, int.class);
    return size;
  }

  @Override
  public int getTreasury() {
    int treasury = requestor.sendRequestAndAwaitReply(getId(), OperationConstants.CITY_GET_TREASURY, int.class);
    return treasury;
  }

  @Override
  public String getProduction() {
    String produced = requestor.sendRequestAndAwaitReply(getId(), OperationConstants.CITY_GET_PRODUCTION, String.class);
    return produced;
  }

  @Override
  public String getWorkforceFocus() {
    String workforcefocus = requestor.sendRequestAndAwaitReply(getId(), OperationConstants.CITY_GET_WORKFORCE_FOCUS, String.class);
    return workforcefocus;
  }

  public String getId(){
    return objectId;
  }
}
