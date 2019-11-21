package hotciv.broker;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;

public class GameProxy implements Game, ClientProxy {

  private final Requestor requestor;

  public GameProxy(Requestor crh) {
    this.requestor = crh;
  }

  @Override
  public Tile getTileAt(Position p) {
    //Først næste iteration.
    return null;
  }

  @Override
  public Unit getUnitAt(Position p) {
    //Først næste iteration.
    return null;
  }

  @Override
  public City getCityAt(Position p) {
    //Først næste iteration.
    return null;
  }

  @Override
  public Player getPlayerInTurn() {
    Player inTurn = requestor.sendRequestAndAwaitReply("operation_player_in_turn", OperationConstants.PLAYER_IN_TURN, Player.class);
    return inTurn;
  }

  @Override
  public Player getWinner() {
    Player winner = requestor.sendRequestAndAwaitReply("operation_winner", OperationConstants.OPERATION_WINNER, Player.class);
    return winner;
  }

  @Override
  public int getAge() {
    int age = requestor.sendRequestAndAwaitReply("operation_current_world_age", OperationConstants.CURRENT_WORLD_AGE, int.class);
    return age;
  }

  @Override
  public boolean moveUnit(Position from, Position to) {
    boolean moved = requestor.sendRequestAndAwaitReply("operation_move_unit", OperationConstants.MOVE_UNIT, boolean.class, from, to);
    return moved;
  }

  @Override
  public void endOfTurn() {
    requestor.sendRequestAndAwaitReply("operation_end_of_turn", OperationConstants.END_OF_TURN, Player.class);
  }

  @Override
  public void changeWorkForceFocusInCityAt(Position p, String balance) {
  }

  @Override
  public void changeProductionInCityAt(Position p, String unitType) {
    requestor.sendRequestAndAwaitReply("operation_city_change_production", OperationConstants.CITY_CHANGE_PRODUCTION, String.class);
    // TODO: HVORDAN TESTER VI DET HER?
  }

  @Override
  public void performUnitActionAt(Position p) {
    requestor.sendRequestAndAwaitReply("operation_unit_action", OperationConstants.UNIT_ACTION, boolean.class);
    // TODO: IMPLEMENT ?
  }

  @Override
  public void addObserver(GameObserver observer) {

  }

  @Override
  public void setTileFocus(Position position) {

  }
}
