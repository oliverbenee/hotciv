package hotciv.broker;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.OperationConstants;
import hotciv.framework.Tile;

public class TileProxy implements Tile, ClientProxy {

  private final Requestor requestor;

  public TileProxy(Requestor crh) {
    this.requestor = crh;
  }

  @Override
  public String getTypeString() {
    String type = requestor.sendRequestAndAwaitReply("operation_tile_get_typestring", OperationConstants.TILE_GET_TYPESTRING, String.class);
    return type;
  }
}
