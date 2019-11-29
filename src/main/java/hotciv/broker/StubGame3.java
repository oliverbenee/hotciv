package hotciv.broker;

import frds.broker.Servant;
import hotciv.framework.*;
import hotciv.stub.StubTile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class StubGame3 implements Game, Servant {
  private ArrayList<GameObserver> observers;
  private GameObserver gameObserver;

  private StubUnit2 red_archer;
  private Player inTurn;
  private Position position_of_red_archer;
  private HashMap<Position, Unit> unitMap;
  private HashMap<Position, City> cityMap;

  private StubCity2 green_city;

  private String lasttype;

  public StubGame3() {
    observers = new ArrayList<>();

    cityMap = new HashMap<>();
    // the only one i need to store for this stub
    Position position_of_green_city = new Position(1,1);
    cityMap.put(position_of_green_city, new StubCity2(Player.GREEN));

    // the only one I need to store for this stub
    red_archer = new StubUnit2(GameConstants.ARCHER, Player.RED, 1);

    // the only one i need to store for this stub
    green_city = new StubCity2(Player.GREEN);

    // Only implement a single archer, assign it to red.
    Position position_of_red_archer = new Position(2,1);
    unitMap = new HashMap<>();
    unitMap.put(position_of_red_archer, red_archer);

    inTurn = Player.RED;
    lasttype = "";
  }

  // Only implement 1 tile for each of the types in AlphaCiv
  Position oceanPosition = new Position(1,0);
  Position hillsPosition = new Position(0,1);
  Position mountain1Position = new Position(1,1);
  Position mountain2Position = new Position(2,2);

  @Override
  public Tile getTileAt(Position p) {
    boolean isOceanPosition = p.equals(oceanPosition);
    if(isOceanPosition){ return new StubTile(GameConstants.OCEANS); }
    boolean isHillsPosition = p.equals(hillsPosition);
    if (isHillsPosition){ return new StubTile(GameConstants.HILLS); }
    boolean isMountain1Position = p.equals(mountain1Position);
    if (isMountain1Position) {return new StubTile(GameConstants.MOUNTAINS);}
    boolean isMountain2Position = p.equals(mountain2Position);
    if(isMountain2Position){return new StubTile(GameConstants.MOUNTAINS);}
    return new StubTile(GameConstants.PLAINS);
  }

  @Override
  public Unit getUnitAt(Position p) {
    return unitMap.get(p);
  }

  // Only implement a single city, assign it to green, which has size 4
  Position position_of_green_city = new Position(1,1);
  @Override
  public City getCityAt(Position p) {
    return cityMap.get(p);
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

  // Implement extra Position for Red archer to move to.
  @Override
  public boolean moveUnit(Position from, Position to) {
    Position new_position_of_red_archer = new Position(3,1);
    unitMap.remove(from);
    unitMap.put(new_position_of_red_archer, red_archer);
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
    if(p.equals(position_of_green_city)){
      System.out.println("Changing city production");
      green_city.setProduction(unitType);
      lasttype = green_city.getProduction();
      System.out.println("to: " + unitType);
    }
  }

  public String getLastType(){
    return lasttype;
  }

  @Override
  public void performUnitActionAt(Position p) {
    if(p.equals(position_of_red_archer)){
      red_archer.setFortify();
    }
    lasttype = "performed unit action";
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
  private String produced;
  private String id;

  public StubCity2(Player owner) {
    this.owner = owner;
    this.produced = GameConstants.ARCHER;
    this.id = UUID.randomUUID().toString();
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
    return 10;
  }

  @Override
  public String getProduction() {
    return produced;
  }

  @Override
  public String getWorkforceFocus() {
    return GameConstants.foodFocus;
  }

  public void setProduction(String unitType){
    produced = unitType;
  }

  public String getId(){return id;}
}

class StubUnit2 implements  Unit {
  private String type;
  private Player owner;
  private int moveCount;
  private boolean fortify;
  private String id;

  public StubUnit2(String type, Player owner, int moveCount) {
    this.type = type;
    this.owner = owner;
    this.moveCount = moveCount;
    this.fortify = false;
    this.id = UUID.randomUUID().toString();
  }
  public String getTypeString() { return type; }
  public Player getOwner() { return owner; }
  public int getMoveCount() {
    if(!fortify){return moveCount;
    } else {
      return 0;
    }
  }
  public int getDefensiveStrength() { return 5; }
  public int getAttackingStrength() { return 6; }
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
  public String getId(){return id;}
}