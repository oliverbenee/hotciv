package hotciv.broker;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.OperationConstants;
import hotciv.framework.Tile;

public class TileProxy implements Tile, ClientProxy {

  private String id;
  private final Requestor requestor;

  public TileProxy(String id, Requestor crh) {
    this.id = id;
    this.requestor = crh;
  }

  @Override
  public String getTypeString() {
    String type = requestor.sendRequestAndAwaitReply(getId(), OperationConstants.TILE_GET_TYPESTRING, String.class);
    return type;
  }

  public String getId(){
    return id;
  }
}
