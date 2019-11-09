package hotciv.stub;

import hotciv.framework.*;
import hotciv.standard.CityImpl;
import hotciv.standard.GameObserverImpl;
import hotciv.standard.UnitImpl;

import java.util.*;

/** Test stub for game for visual testing of
 * minidraw based graphics.
 *
 * SWEA support code.
 *
   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

public class StubGame2 implements Game {

  // === Unit handling ===
  private Position pos_archer_red;
  private Position pos_legion_blue;
  private Position pos_settler_red;
  private Position pos_bomb_red;

  protected Map<Position, Unit> unitMap;

  private Unit red_archer;

  // === City handling ===
  private Position pos_red_city;
  private Position pos_blue_city;
  private Position pos_blue_city_2;

  private City red_city;
  private City blue_city;
  private City blue_city_2;

  private HashMap<Position, City> cities;
  private ArrayList<GameObserver> observers;

  // === Constructor handling ===
  public StubGame2() {
    // AlphaCiv configuration
    pos_archer_red = new Position( 2, 0);
    pos_legion_blue = new Position( 3, 2);
    pos_settler_red = new Position( 4, 3);
    pos_bomb_red = new Position( 6, 5);
    pos_red_city = new Position(1,1);
    pos_blue_city = new Position(4,1);
    pos_blue_city_2 = new Position(6,4);

    // the only one I need to store for this stub
    red_archer = new StubUnit( GameConstants.ARCHER, Player.RED, 1);

    inTurn = Player.RED;
    cities = new HashMap<>();
    defineCities();
    unitMap = new HashMap<>();

    createUnit(pos_archer_red, new StubUnit(GameConstants.ARCHER, Player.RED, 1));
    createUnit(pos_legion_blue, new StubUnit(GameConstants.LEGION, Player.BLUE, 1));
    createUnit(pos_settler_red, new StubUnit(GameConstants.SETTLER, Player.RED, 1));
    createUnit(pos_bomb_red, new StubUnit(GameConstants.B52, Player.RED, 2));

    defineWorld(1);
    defineCities();
    defineUnitMap();

    observers = new ArrayList<>();
    gameObserver = new GameObserverImpl();
    observers.add(gameObserver);

  }

  protected void createCity(Position p, StubCity stubCity) {
    cities.put(p, stubCity);
  }

  protected void createUnit(Position p, Unit unit){unitMap.put(p, unit);}

  protected void removeUnit(Position p){unitMap.remove(p);}

  public Unit getUnitAt(Position p) {
    return unitMap.get(p);
  }

  // Stub only allows moving red archer
  public boolean moveUnit( Position from, Position to ) {
    System.out.println("-- StubGame2 / moveUnit called: " + from + "->" + to);

    boolean hasNoMoves = getUnitAt(from).getMoveCount() < 1;
    if(hasNoMoves) {
      gameObserver.worldChangedAt(from);
      gameObserver.worldChangedAt(to);
      return false;
    }

    boolean isOwnUnit = getUnitAt(from) != null && getUnitAt(from).getOwner().equals(inTurn);
    if(!isOwnUnit) return false;

    boolean exists = getUnitAt(from) != null;
    if(!exists) return false;

    createUnit(to, getUnitAt(from));
    removeUnit(from);
    StubUnit u = (StubUnit) getUnitAt(to);
    u.decreaseMoveCount();

    // notify our observer(s) about the changes on the tiles
    gameObserver.worldChangedAt(from);
    gameObserver.worldChangedAt(to);
    return true;
  }

  // === Turn handling ===
  private Player inTurn;
  private int age = -4000;

  public void endOfTurn() {
    System.out.println( "-- StubGame2 / endOfTurn called." );
    if (inTurn == Player.RED) {
      inTurn = Player.BLUE;
      System.out.println("blue is now in turn.");
    } else {
      inTurn = Player.RED;
      age += 100;
      System.out.println("red is now in turn. Age is now: " + age);
    }
    gameObserver.turnEnds(inTurn, age);
  }

  public Player getPlayerInTurn() { return inTurn; }

  // === Observer handling ===
  protected GameObserver gameObserver;
  // observer list is only a single one...
  public void addObserver(GameObserver observer) {
    gameObserver = observer;
  }

  // A simple implementation to draw the map of DeltaCiv
  protected Map<Position,Tile> world; 
  public Tile getTileAt( Position p ) { return world.get(p); }


  /** define the world.
   * @param worldType 1 gives one layout while all other
   * values provide a second world layout.
   */
  protected void defineWorld(int worldType) {
    world = new HashMap<Position,Tile>();
    for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
      for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
        Position p = new Position(r,c);
        world.put( p, new StubTile(GameConstants.PLAINS));
      }
    }
  }

  /** define cities.
   *
   * @return
   */
  protected void defineCities(){
    cities = new HashMap<>();
    // Cities...
    cities.put(pos_red_city, new StubCity(Player.RED));
    cities.put(pos_blue_city, new StubCity(Player.BLUE));
    cities.put(pos_blue_city_2, new StubCity(Player.BLUE));
  }

  /**
   * define units
   * @return
   */
  protected void defineUnitMap(){
    unitMap = new HashMap<>();
    unitMap.put(new Position(2,0), new StubUnit(GameConstants.ARCHER, Player.RED, 1));
    unitMap.put(new Position(3,2), new StubUnit(GameConstants.LEGION, Player.BLUE, 1));
    unitMap.put(new Position(4,3), new StubUnit(GameConstants.SETTLER, Player.RED,1 ));
  }

  public City getCityAt( Position p ) {
          return cities.get(p);
  }
  public Player getWinner() { return Player.RED; }
  public int getAge() { return age; }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {
    StubUnit unit = (StubUnit) getUnitAt(p);
    // Settler unit action.
    if(unit.getTypeString().equals(GameConstants.SETTLER)){
      System.out.println("Settler found!");
      cities.put(p, new StubCity(getPlayerInTurn()));
      removeUnit(p);
    }
    // Archer unit action
    if(unit.getTypeString().equals(GameConstants.ARCHER)) {
      System.out.println("Archer found!");
      unit.setFortify();
    }
    // Bomber unit action
    if(unit.getTypeString().equals(GameConstants.B52)) {
      System.out.println("Bomber found!");
      cities.remove(p);
    }
    gameObserver.worldChangedAt(p);
  }

  public void setTileFocus(Position position) {
    gameObserver.tileFocusChangedAt(position);
    System.out.println("-- StubGame2 / setTileFocus called.");
    System.out.println(" It was called at the Position: " + position);
  }
}

class StubUnit implements  Unit {
  private String type;
  private Player owner;
  private int moveCount;
  private boolean fortify;
  public StubUnit(String type, Player owner, int moveCount) {
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

class StubCity implements City {
  private String nextUnit;
  private Player owner;

  public StubCity(Player p) {
    this.nextUnit = GameConstants.LEGION;
    this.owner = p;
  }

  @Override
  public Player getOwner() {
    return owner;
  }

  @Override
  public int getSize() {
    return 1;
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