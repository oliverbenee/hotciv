package hotciv.standard;

import hotciv.factory.SemiCivFactory;
import hotciv.factory.TestEpsilonCivFactory;
import hotciv.factory.TestSemiCivFactory;
import hotciv.factory.ThetaCivFactory;
import hotciv.framework.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestThetaCiv {
  private GameImpl game;

  /**
   * Fixture for ThetaCiv testing.
   */
  @Before
  public void setUp() {
    MapStrategy mapStrategy = new AlphaCivMapStrategy();
    game = new GameImpl(new ThetaCivFactory());
    mapStrategy.createWorld(game);
  }

  // Ensure, that cities can produce the new unit type B52 Bomber.
  @Test
  public void citiesCanProduceb52(){
    Position redCityPosition = new Position(1,1);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.LEGION));
    game.changeProductionInCityAt(redCityPosition, GameConstants.B52);
  }

  // Ensure, that cities take 12 rounds to produce the B52 Bomber.
  @Test
  public void b52Takes12RoundsToProduce(){
    Position redCityPosition = new Position(1,1);
    assertThat(game.getCityAt(redCityPosition).getOwner(), is(Player.RED));
    game.changeProductionInCityAt(redCityPosition, GameConstants.B52);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getUnitAt(redCityPosition).getTypeString(), is(GameConstants.B52));
  }

  // Ensure, that the B52 Bomber can be moved twice.
  @Test
  public void b52CanMoveTwice(){
    Position redCityPosition = new Position(1,1);
    Position targetPosition = new Position(3,1);
    assertThat(game.getCityAt(redCityPosition).getOwner(), is(Player.RED));
    game.changeProductionInCityAt(redCityPosition, GameConstants.B52);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(redCityPosition, targetPosition);
    assertThat(game.getUnitAt(targetPosition).getTypeString(), is(GameConstants.B52));

  }

  // Ensure, that the B52 Bomber can be moved over oceans.
  @Test
  public void b52CanMoveOverOceans(){
    Position redCityPosition = new Position(1,1);
    Position oceanPosition = new Position(1,0);
    assertThat(game.getTileAt(oceanPosition).getTypeString(), is(GameConstants.OCEANS));
    assertThat(game.getCityAt(redCityPosition).getOwner(), is(Player.RED));
    game.changeProductionInCityAt(redCityPosition, GameConstants.B52);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getUnitAt(redCityPosition).getTypeString(), is(GameConstants.B52));
    game.moveUnit(redCityPosition, oceanPosition);
    assertThat(game.getUnitAt(oceanPosition).getTypeString(), is(GameConstants.B52));
  }

  // Ensure, that the B52 Bomber can be moved over mountains.
  @Test
  public void b52CanMoveOverMountains(){
    Position redCityPosition = new Position(1,1);
    Position mountainPosition = new Position(1,1);
    assertThat(game.getTileAt(mountainPosition).getTypeString(), is(GameConstants.MOUNTAINS));
    assertThat(game.getCityAt(redCityPosition).getOwner(), is(Player.RED));
    game.changeProductionInCityAt(redCityPosition, GameConstants.B52);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getUnitAt(redCityPosition).getTypeString(), is(GameConstants.B52));
    game.moveUnit(redCityPosition, mountainPosition);
    assertThat(game.getUnitAt(mountainPosition).getTypeString(), is(GameConstants.B52));
  }

  // Ensure, that land units cannot move over oceans.
  @Test
  public void landUnitsCannotMoveOverOceans(){
    Position blueUnitPosition = new Position(3,2);
    Position oceanPosition = new Position(1,0);
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    Unit blueUnit = game.getUnitAt(blueUnitPosition);
    assertThat(game.getUnitAt(blueUnitPosition), is(blueUnit));
    game.moveUnit(blueUnitPosition, oceanPosition);
    assertThat(game.getUnitAt(blueUnitPosition), is(blueUnit));
  }

  // Ensure, that land units cannot move over mountains.
  @Test
  public void landUnitsCannotMoveOverMountains(){
    Position blueUnitPosition = new Position(3,2);
    assertThat(game.getUnitAt(blueUnitPosition).getTypeString(), is(GameConstants.LEGION));
    Position mountainPosition = new Position(2,2);
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    Unit blueUnit = game.getUnitAt(blueUnitPosition);
    assertThat(game.getUnitAt(blueUnitPosition), is(blueUnit));
    game.moveUnit(blueUnitPosition, mountainPosition);
    assertThat(game.getUnitAt(blueUnitPosition), is(blueUnit));
  }

  @Test
  public void b52Has8Defense(){
    Position redCityPosition = new Position(1,1);
    assertThat(game.getCityAt(redCityPosition).getOwner(), is(Player.RED));
    game.changeProductionInCityAt(redCityPosition, GameConstants.B52);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getUnitAt(redCityPosition).getDefensiveStrength(), is(8));
  }

  @Test
  public void b52Has1Attack(){
    Position redCityPosition = new Position(1,1);
    assertThat(game.getCityAt(redCityPosition).getOwner(), is(Player.RED));
    game.changeProductionInCityAt(redCityPosition, GameConstants.B52);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getUnitAt(redCityPosition).getAttackingStrength(), is(1));
  }
}

// TODO: Kan man lave unitActions på modstanderens bombefly?

// TODO: HVORFOR VIRKER B52 FLYING NÅR MOVEUNIT ALDRIG CHECKER AT B52 KAN FLYVE?