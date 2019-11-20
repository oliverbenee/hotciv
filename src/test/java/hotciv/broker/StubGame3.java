package hotciv.broker;

import frds.broker.Servant;
import hotciv.framework.*;

import java.util.ArrayList;
import java.util.Map;

public class StubGame3 implements Game, Servant {
  private ArrayList<GameObserver> observers;
  private GameObserver gameObserver;

  private Unit red_archer;
  private Player inTurn;

  public StubGame3() {
    observers = new ArrayList<>();

    // the only one I need to store for this stub
    red_archer = new StubUnit2( GameConstants.ARCHER, Player.RED, 1);

    inTurn = Player.RED;
  }

  @Override
  public Tile getTileAt(Position p) {
    return null;
  }

  // Only implement a single archer, assign it to red, and has 2 move.
  Position position_of_red_archer = new Position(2,1);
  @Override
  public Unit getUnitAt(Position p) {
    if(p.equals(position_of_red_archer)){
      return red_archer;
    }
    return null;
  }

  // Only implement a single city, assign it to green, which has size 4
  Position position_of_green_city = new Position(1,1);
  @Override
  public City getCityAt(Position p) {
    if(p.equals(position_of_green_city)){
      return new StubCity2(Player.GREEN);
    }
    return null;
  }

  @Override
  public Player getPlayerInTurn() {
    return inTurn;
  }

  @Override
  public Player getWinner() {
    return Player.YELLOW;
  }

  @Override
  public int getAge() {
    return 69;
  }

  @Override
  public boolean moveUnit(Position from, Position to) {
    return true;
  }

  @Override
  public void endOfTurn() {
    boolean redTurn = getPlayerInTurn().equals(Player.RED);
    if(redTurn) inTurn = Player.YELLOW;
    else inTurn = Player.RED;
  }

  @Override
  public void changeWorkForceFocusInCityAt(Position p, String balance) {

  }

  @Override
  public void changeProductionInCityAt(Position p, String unitType) {

  }

  @Override
  public void performUnitActionAt(Position p) {

  }

  @Override
  public void addObserver(GameObserver observer) {
    observers.add(observer);
    gameObserver = observer;
  }

  @Override
  public void setTileFocus(Position position) {

  }
}

class StubCity2 implements City {
  private Player owner;

  public StubCity2(Player p) {
    this.owner = p;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  @Override
  public int getSize() {
    return 4;
  }

  @Override
  public int getTreasury() {
    return 1;
  }

  @Override
  public String getProduction() {
    if(getOwner().equals(Player.RED)){
      return GameConstants.B52;
    } else {
      return GameConstants.ARCHER;
    }
  }

  @Override
  public String getWorkforceFocus() {
    return GameConstants.productionFocus;
  }
}

class StubUnit2 implements  Unit {
  private String type;
  private Player owner;
  private int moveCount;
  private boolean fortify;
  public StubUnit2(String type, Player owner, int moveCount) {
    this.type = type;
    this.owner = owner;
    this.moveCount = moveCount;
    this.fortify = false;
  }
  public String getTypeString() { return type; }
  public Player getOwner() { return owner; }
  public int getMoveCount() {
    if(!fortify){return moveCount;
    } else {
      return 0;
    }
  }
  public int getDefensiveStrength() { return 0; }
  public int getAttackingStrength() { return 0; }
  public void decreaseMoveCount() {
    moveCount--;
  }
  public void setFortify() {
    boolean isFortified = fortify;
    if(!isFortified) {
      fortify = true;
      System.out.println("fortified archer!");
    } else {
      fortify = false;
      System.out.println("unfortified archer!");
    }
  }
}