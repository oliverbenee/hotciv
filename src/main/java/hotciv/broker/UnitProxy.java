package hotciv.broker;

import frds.broker.Requestor;
import hotciv.framework.OperationConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitProxy implements Unit {

  private String objectId;
  private final Requestor requestor;

  public UnitProxy(String objectId, Requestor crh) {
    this.objectId = objectId;
    this.requestor = crh;
  }

  @Override
  public String getTypeString() {
    return requestor.sendRequestAndAwaitReply(
            getId(),
            OperationConstants.UNIT_GET_TYPESTRING,
            String.class);
  }

  @Override
  public Player getOwner() {
    return requestor.sendRequestAndAwaitReply(
            getId(),
            OperationConstants.UNIT_GET_OWNER,
            Player.class);
  }

  @Override
  public int getMoveCount() {
    return requestor.sendRequestAndAwaitReply(
            getId(),
            OperationConstants.UNIT_GET_MOVECOUNT,
            int.class);
  }

  @Override
  public int getDefensiveStrength() {
    return requestor.sendRequestAndAwaitReply(
            getId(),
            OperationConstants.UNIT_GET_DEFENSE,
            int.class);
  }

  @Override
  public int getAttackingStrength() {
    return requestor.sendRequestAndAwaitReply(
            getId(),
            OperationConstants.UNIT_GET_OFFENSE,
            int.class);
  }

  public String getId() {
    return objectId;
  }
}
