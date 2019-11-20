package hotciv.broker;

import frds.broker.ClientProxy;
import hotciv.framework.City;
import hotciv.framework.Player;
import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;

public class CityProxy implements City, ClientProxy {

  private final Requestor requestor;

  public CityProxy(Requestor crh) {
    this.requestor = crh;
  }

  @Override
  public Player getOwner() {
    Player owner = requestor.sendRequestAndAwaitReply("operation_city_get_owner", OperationConstants.CITY_GET_OWNER, Player.class);
    return owner;
  }

  @Override
  public int getSize() {
    int size = requestor.sendRequestAndAwaitReply("operation_city_get_size", OperationConstants.CITY_GET_SIZE, int.class);
    return size;
  }

  @Override
  public int getTreasury() {
    int treasury = requestor.sendRequestAndAwaitReply("operation_city_get_treasury", OperationConstants.CITY_GET_TREASURY, int.class);
    return treasury;
  }

  @Override
  public String getProduction() {
    String produced = requestor.sendRequestAndAwaitReply("operation_city_get_production", OperationConstants.CITY_GET_PRODUCTION, String.class);
    return produced;
  }

  @Override
  public String getWorkforceFocus() {
    String workforcefocus = requestor.sendRequestAndAwaitReply("operation_city_get_workforce_focus", OperationConstants.CITY_GET_WORKFORCE_FOCUS, String.class);
    return workforcefocus;
  }
}
