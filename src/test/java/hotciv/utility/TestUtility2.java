package hotciv.utility;

import hotciv.framework.*;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * @author Henrik BÃ¦rbak Christensen, Aarhus University
 */
public class TestUtility2 {
  private Iterator<Position> iter;
  private List<Position> neighborhood;
  private Position center;

  Game game;
  @Before
  public void setUp() {
    game = new GameStubForBattleTesting();
  }

  @Test public void shouldGiveCorrectTerrainFactors() {
    // plains have multiplier 1
    assertThat(Utility2.getTerrainFactor(game, new Position(0,1)), is(1));
    // hills have multiplier 2
    assertThat(Utility2.getTerrainFactor(game, new Position(1,0)), is(2));
    // forest have multiplier 2
    assertThat(Utility2.getTerrainFactor(game, new Position(0,0)), is(2));
    // cities have multiplier 3
    assertThat(Utility2.getTerrainFactor(game, new Position(1,1)), is(3));
  }

  @Test public void shouldGiveSum1ForBlueAtP5_5() {
    assertThat("Blue unit at (5,5) should get +1 support",
            Utility2.getFriendlySupport( game, new Position(5,5), Player.BLUE), is(+1));
  }

  @Test public void shouldGiveSum0ForBlueAtP2_4() {
    assertThat("Blue unit at (2,4) should get +0 support",
            Utility2.getFriendlySupport( game, new Position(2,4), Player.BLUE), is(+0));
  }
  @Test public void shouldGiveSum2ForRedAtP2_4() {
    assertThat("Red unit at (2,4) should get +2 support",
            Utility2.getFriendlySupport( game, new Position(2,4), Player.RED), is(+2));
  }
  @Test public void shouldGiveSum3ForRedAtP2_2() {
    assertThat("Red unit at (2,2) should get +3 support",
            Utility2.getFriendlySupport( game, new Position(2,2), Player.RED), is(+3));
  }
}

// ================================== TEST STUBS ===
class StubTile implements Tile {
  private String type;
  private String id;
  public StubTile(String type, int r, int c) {
    this.type = type;
    this.id = UUID.randomUUID().toString();
  }
  public String getTypeString() { return type; }

  @Override
  public String getId() {
    return id;
  }
}

class StubUnit implements Unit {
  private String type; private Player owner;
  public StubUnit(String type, Player owner) {
    this.type = type; this.owner = owner;
  }
  public String getTypeString() { return type; }
  public Player getOwner() { return owner; }
  public int getMoveCount() { return 0; }
  public int getDefensiveStrength() { return 0; }
  public int getAttackingStrength() { return 0; }

  @Override
  public String getId() {
    System.out.println("StubUnit getId called. This is bad.");
    return null;
  }
}


/** A test stub for testing the battle calculation methods in
 * Utility. The terrains are
 * 0,0 - forest;
 * 1,0 - hill;
 * 0,1 - plain;
 * 1,1 - city.
 *
 * Red has units on 2,3; 3,2; 3,3; blue one on 4,4
 */
class GameStubForBattleTesting implements Game {
  public Tile getTileAt(Position p) {
    if ( p.getRow() == 0 && p.getColumn() == 0 ) {
      return new StubTile(GameConstants.FOREST, 0, 0);
    }
    if ( p.getRow() == 1 && p.getColumn() == 0 ) {
      return new StubTile(GameConstants.HILLS, 1, 0);
    }
    return new StubTile(GameConstants.PLAINS, 0, 1);
  }
  public Unit getUnitAt(Position p) {
    if ( p.getRow() == 2 && p.getColumn() == 3 ||
            p.getRow() == 3 && p.getColumn() == 2 ||
            p.getRow() == 3 && p.getColumn() == 3 ) {
      return new StubUnit(GameConstants.ARCHER, Player.RED);
    }
    if ( p.getRow() == 4 && p.getColumn() == 4 ) {
      return new StubUnit(GameConstants.ARCHER, Player.BLUE);
    }
    return null;
  }
  public City getCityAt(Position p) {
    if ( p.getRow() == 1 && p.getColumn() == 1 ) {
      return new City() {
        public Player getOwner() { return Player.RED; }
        public int getSize() { return 1; }
        public int getTreasury() {
          return 0;
        }
        public String getProduction() {
          return null;
        }
        public String getWorkforceFocus() {
          return null;
        }
        @Override
        public String getId() {
          return null;
        }
      };
    }
    return null;
  }

  // the rest is unused test stub methods...
  public void changeProductionInCityAt(Position p, String unitType) {}
  public void changeWorkForceFocusInCityAt(Position p, String balance) {}
  public void endOfTurn() {}
  public Player getPlayerInTurn() {return null;}
  public Player getWinner() {return null;}
  public int getAge() { return 0; }
  public boolean moveUnit(Position from, Position to) {return false;}
  public void performUnitActionAt( Position p ) {}

  @Override
  public void addObserver(GameObserver observer) {

  }

  @Override
  public void setTileFocus(Position position) {

  }
}
