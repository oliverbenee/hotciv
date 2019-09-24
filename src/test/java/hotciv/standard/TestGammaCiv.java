package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/* BetaCiv test class */
public class TestGammaCiv {
  private GameImpl game;

  /**
   * Fixture for betaciv testing.
   */
  @Before
  public void setUp() {
    MapStrategy mapStrategy = new AlphaCivMapStrategy();
    game = new GameImpl(new AlphaCivWinnerStrategy(), new AlphaCivAgeStrategy(), new GammaCivActionStrategy(), mapStrategy);
    mapStrategy.createWorld(game);
  }

  // Ensure, that Settler can found city, and the owner is correct.
  @Test
  public void settlerCanFoundCity() {
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
    game.performUnitActionAt(new Position(4,3));
    assertThat(game.getCityAt(new Position(4,3)).getOwner(), is(Player.RED));
  }

  // Ensure, that the new City's population is 1.
  @Test
  public void newCityPopulationSizeIsOne() {
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
    game.performUnitActionAt(new Position(4,3));
    assertThat(game.getCityAt(new Position(4,3)).getSize(), is(1));
  }

  // Ensure, that settler is removed from the world after creating a new city.
  @Test
  public void settlerIsRemovedAfterFoundingACity(){
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.getUnitAt(new Position(4,3)).getTypeString(), is(GameConstants.SETTLER));
    game.performUnitActionAt(new Position(4,3));
    assertNull(game.getUnitAt(new Position(4,3)));
  }

  // Ensure, that archers can fortify and double their defensive strength.
  @Test
  public void archersDoubleTheirDefensiveStrengthWhenFortifying(){
    Position archPos = new Position(2,0);
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.getUnitAt(archPos).getTypeString(), is(GameConstants.ARCHER));
    int inidef = game.getUnitAt(archPos).getDefensiveStrength();
    game.performUnitActionAt(archPos);
    int newdef = game.getUnitAt(archPos).getDefensiveStrength();
    assertEquals(newdef, inidef*2);
  }

  // Ensure, that units lose movecount when moving.
  @Test
  public void unitsLoseMoveCountWhenMoving(){
    Position archPos = new Position(2,0);
    assertThat(game.getUnitAt(archPos).getMoveCount(), is(1));
    game.moveUnit(archPos, new Position(2,1));
    assertThat(game.getUnitAt(new Position(2,1)).getMoveCount(), is(0));
  }

  // Ensure, that when archers fortify, they cannot move.
  @Test
  public void archersCannotMoveAfterFortifying(){
    Position archPos = new Position(2,0);
    Position targetPos = new Position(2,1);
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.getUnitAt(archPos).getTypeString(), is(GameConstants.ARCHER));
    game.performUnitActionAt(archPos);
    assertThat(game.getUnitAt(archPos).getMoveCount(), is(0));
    game.moveUnit(archPos, targetPos);
    assertNull(game.getUnitAt(targetPos));
  }
}