package hotciv.stub;

import hotciv.framework.*;

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

  private City red_city;
  private City blue_city;

  private HashMap<Position, City> cities;
  private HashMap<Position, Unit> units;

  // === Constructor handling ===
  public StubGame2() {
    defineWorld(1);
    defineCities();
    // AlphaCiv configuration
    pos_archer_red = new Position( 2, 0);
    pos_legion_blue = new Position( 3, 2);
    pos_settler_red = new Position( 4, 3);
    pos_bomb_red = new Position( 6, 4);
    pos_red_city = new Position(1,1);
    pos_blue_city = new Position(4,1);

    // the only one I need to store for this stub
    red_archer = new StubUnit( GameConstants.ARCHER, Player.RED, 1);

    inTurn = Player.RED;
    units = new HashMap<>();
    cities = new HashMap<>();
    unitMap = new HashMap<>();
  }

  private void updateUnitMap(Position from, Position to){
    createUnit(to, getUnitAt(from));
    removeUnit(from);
    StubUnit u = (StubUnit) getUnitAt(to);
    u.decreaseMoveCount();
  }

  protected void createUnit(Position p, Unit unit){unitMap.put(p, unit);}

  protected void removeUnit(Position p){unitMap.remove(p);}

  public Unit getUnitAt(Position p) {
    if ( p.equals(pos_archer_red) ) {
      return red_archer;
    }
    if ( p.equals(pos_settler_red) ) {
      return new StubUnit( GameConstants.SETTLER, Player.RED, 1);
    }
    if ( p.equals(pos_legion_blue) ) {
      return new StubUnit( GameConstants.LEGION, Player.BLUE,1 );
    }
    if ( p.equals(pos_bomb_red) ) {
      return new StubUnit( ThetaConstants.B52, Player.RED,1 );
    }
    return null;
  }

  public boolean legalTile(Position to){
    // Handle illegal tiles
    boolean isOcean = getTileAt(to).getTypeString().equals(GameConstants.OCEANS);
    if(isOcean) return false;
    boolean isMountain = getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS);
    if(isMountain) return false;
    return true;
  }

  // Stub only allows moving red archer
  public boolean moveUnit( Position from, Position to ) {
    System.out.println("-- StubGame2 / moveUnit called: " + from + "->" + to);
    Unit unit = getUnitAt(from);
    boolean isOwnUnit = getUnitAt(from) != null && getUnitAt(from).getOwner().equals(inTurn);
    if(!isOwnUnit) return false;

    boolean hasMoves = getUnitAt(from).getMoveCount() < 1;
    if(!hasMoves) return false;

    boolean legalTile = legalTile(to);
    if(!legalTile) return false;

    units.remove(from);
    units.put(to, unit);
    // notify our observer(s) about the changes on the tiles
    gameObserver.worldChangedAt(from);
    gameObserver.worldChangedAt(to);
    updateUnitMap(from, to);
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
  }

  /**
   * define units
   * @return
   */
  protected void defineUnitMap(){
    units = new HashMap<>();
    units.put(new Position(2,0), new StubUnit(GameConstants.ARCHER, Player.RED, 1));
    units.put(new Position(3,2), new StubUnit(GameConstants.LEGION, Player.BLUE, 1));
    units.put(new Position(4,3), new StubUnit(GameConstants.SETTLER, Player.RED,1 ));
  }

  // TODO: Add more stub behaviour to test MiniDraw updating
  public City getCityAt( Position p ) {
          if(p.equals(pos_blue_city)) return new StubCity(Player.BLUE);
          if(p.equals(pos_red_city)) return new StubCity(Player.RED);
          return null;
  }
  public Player getWinner() { return Player.RED; }
  public int getAge() { return age; }
  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}
  public void changeProductionInCityAt( Position p, String unitType ) {}
  public void performUnitActionAt( Position p ) {}

  public void setTileFocus(Position position) {
    gameObserver.tileFocusChangedAt(position);
    // TODO: setTileFocus implementation pending.
    System.out.println("-- StubGame2 / setTileFocus called.");
    System.out.println(" It was called at the Position: " + position);
  }

}

class StubUnit implements  Unit {
  private String type;
  private Player owner;
  private int moveCount;
  public StubUnit(String type, Player owner, int moveCount) {
    this.type = type;
    this.owner = owner;
    this.moveCount = moveCount;
  }
  public String getTypeString() { return type; }
  public Player getOwner() { return owner; }
  public int getMoveCount() { return moveCount; }
  public int getDefensiveStrength() { return 0; }
  public int getAttackingStrength() { return 0; }
  public void decreaseMoveCount() {
    moveCount--;
  }
}

class StubCity implements City {
  private Player owner;

  public StubCity(Player p) {
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
    return null;
  }

  @Override
  public String getWorkforceFocus() {
    return null;
  }
}