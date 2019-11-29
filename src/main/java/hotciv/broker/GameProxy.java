package hotciv.broker;

import frds.broker.ClientProxy;
import frds.broker.Requestor;
import hotciv.framework.*;
import hotciv.standard.GameObserverImpl;
import hotciv.standard.TileImpl;
import hotciv.standard.UnitImpl;

import java.util.ArrayList;

public class GameProxy implements Game, ClientProxy {

  private final Requestor requestor;
  private ArrayList<GameObserver> observers;
  private GameObserver gameObserver;

  public GameProxy(Requestor crh) {
    this.requestor = crh;
    observers = new ArrayList<>();
    this.gameObserver = new GameObserverImpl();
    observers.add(gameObserver);
  }

  @Override
  public Tile getTileAt(Position p) {
      String id =
              requestor.sendRequestAndAwaitReply("operation_get_tile_at", OperationConstants.GET_TILE_AT, String.class, p);
      Tile proxy = new TileProxy(id, requestor);
      return proxy;
  }

  @Override
  public Unit getUnitAt(Position p) {
    String id =
            requestor.sendRequestAndAwaitReply("operation_get_unit_at", OperationConstants.GET_UNIT_AT, String.class, p);
    System.out.println(id);
    if(!id.equals("No unit found!")) {
      Unit proxy = new UnitProxy(id, requestor);
      return proxy;
    } else {
      return null;
    }
  }

  @Override
  public City getCityAt(Position p) {
    String id =
            requestor.sendRequestAndAwaitReply("operation_get_city_at", OperationConstants.GET_CITY_AT, String.class, p);
    System.out.println(id);
    if(!id.equals("No city found!")) {
      City proxy = new CityProxy(id, requestor);
      return proxy;
    } else {
      return null;
    }
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
    gameObserver.worldChangedAt(from);
    gameObserver.tileFocusChangedAt(from);
    gameObserver.worldChangedAt(to);
    gameObserver.tileFocusChangedAt(to);
    return moved;
  }

  @Override
  public void endOfTurn() {
    requestor.sendRequestAndAwaitReply("operation_end_of_turn", OperationConstants.END_OF_TURN, Player.class);
    gameObserver.turnEnds(getPlayerInTurn(), getAge());
  }

  @Override
  public void changeWorkForceFocusInCityAt(Position p, String balance) {
  }

  @Override
  public void changeProductionInCityAt(Position p, String unitType) {
    System.out.println("Sending request to change production");
    requestor.sendRequestAndAwaitReply("operation_city_change_production", OperationConstants.CITY_CHANGE_PRODUCTION, null, p, unitType);
    gameObserver.worldChangedAt(p);
  }

  @Override
  public void performUnitActionAt(Position p) {
    requestor.sendRequestAndAwaitReply(
            "operation_unit_action",
            OperationConstants.UNIT_ACTION, String.class, p);
    gameObserver.worldChangedAt(p);
  }

  @Override
  public void addObserver(GameObserver observer) {
    observers.add(observer);
    gameObserver = observer;
  }

  @Override
  public void setTileFocus(Position position) {
    boolean hasUnit = getUnitAt(position) != null;
    boolean hasCity = getCityAt(position) != null;
    if(hasUnit || hasCity){
      gameObserver.tileFocusChangedAt(position);
    }
  }
}
