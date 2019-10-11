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
    MapStrategy mapStrategy = new TestThetaCivMapStrategy();
    game = new GameImpl(new ThetaCivFactory());
    mapStrategy.createWorld(game);
  }

  // Ensure, that cities can produce the new unit type B52 Bomber.
  @Test
  public void citiesCanProduceb52(){
    Position redCityPosition = new Position(1,1);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.LEGION));
    game.changeProductionInCityAt(redCityPosition, GameConstants.B52);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.B52));
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
    Position redB52Position = new Position(1,2);
    Position targetPosition = new Position(3,2);
    assertThat(game.getUnitAt(redB52Position).getOwner(), is(Player.RED));
    game.moveUnit(redB52Position, targetPosition);
    assertThat(game.getUnitAt(targetPosition).getTypeString(), is(GameConstants.B52));
  }

  // Ensure, that the B52 Bomber can be moved over oceans.
  @Test
  public void b52CanMoveOverOceans(){
    Position redB52Position = new Position(1,2);
    Position oceanPosition = new Position(1,0);
    assertThat(game.getTileAt(oceanPosition).getTypeString(), is(GameConstants.OCEANS));
    assertThat(game.getUnitAt(redB52Position).getTypeString(), is(GameConstants.B52));
    game.moveUnit(redB52Position, oceanPosition);
    assertThat(game.getUnitAt(oceanPosition).getTypeString(), is(GameConstants.B52));
  }

  // Ensure, that the B52 Bomber can be moved over mountains.
  @Test
  public void b52CanMoveOverMountains(){
    Position redB52Position = new Position(1,2);
    Position mountainPosition = new Position(1,1);
    assertThat(game.getTileAt(mountainPosition).getTypeString(), is(GameConstants.MOUNTAINS));
    assertThat(game.getUnitAt(redB52Position).getTypeString(), is(GameConstants.B52));
    game.moveUnit(redB52Position, mountainPosition);
    assertThat(game.getUnitAt(mountainPosition).getTypeString(), is(GameConstants.B52));
  }

  // Ensure, that land units cannot move over oceans.
  @Test
  public void landUnitsCannotMoveOverOceans(){
    Position blueLandUnitPosition = new Position(3,2);
    Position intermediatePosition = new Position(2,1);
    Position oceanPosition = new Position(1,0);
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    UnitImpl blueUnit = game.getUnitAt(blueLandUnitPosition);
    assertThat(game.getUnitAt(blueLandUnitPosition), is(blueUnit));
    game.moveUnit(blueLandUnitPosition, intermediatePosition);
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(intermediatePosition, oceanPosition);
    assertThat(game.getUnitAt(intermediatePosition), is(blueUnit));
    assertNull(game.getUnitAt(oceanPosition));
  }

  // Ensure, that land units cannot move over mountains.
  @Test
  public void landUnitsCannotMoveOverMountains(){
    Position blueUnitPosition = new Position(3,2);
    assertThat(game.getUnitAt(blueUnitPosition).getTypeString(), is(GameConstants.LEGION));
    Position mountainPosition = new Position(2,2);
    assertThat(game.getTileAt(mountainPosition).getTypeString(), is(GameConstants.MOUNTAINS));
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    Unit blueUnit = game.getUnitAt(blueUnitPosition);
    assertThat(game.getUnitAt(blueUnitPosition), is(blueUnit));
    game.moveUnit(blueUnitPosition, mountainPosition);
    assertThat(game.getUnitAt(blueUnitPosition), is(blueUnit));
    assertNull(game.getUnitAt(mountainPosition));
  }

  // Ensure, that B52 has 8 base defense.
  @Test
  public void b52Has8Defense(){
    Position redB52Position = new Position(1,2);
    assertThat(game.getUnitAt(redB52Position).getDefensiveStrength(), is(8));
  }

  // Ensure that B52 has 1 base attack.
  @Test
  public void b52Has1Attack(){
    Position redB52Position = new Position(1,2);
    assertThat(game.getUnitAt(redB52Position).getAttackingStrength(), is(1));
  }

  // Ensure, that player blue cannot do actions on player reds B52.
  @Test
  public void blueCannotDoActionsOnEnemyB52(){
    Position redB52Position = new Position(1, 2);
    Position redCityPosition = new Position(1,1);
    assertThat(game.getUnitAt(redB52Position).getTypeString(), is(GameConstants.B52));
    game.moveUnit(redB52Position, redCityPosition);
    game.endOfTurn();
    assertNotNull(game.getCityAt(redCityPosition));
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    game.performUnitActionAt(redCityPosition);
    assertNotNull(game.getCityAt(redCityPosition));
  }

  //Ensure, that settler unit action still works in this new actionstrategy.
  @Test
  public void settlerCanStillFoundCity(){
    Position settlerPosition = new Position(4,3);
    game.performUnitActionAt(settlerPosition);
    assertNotNull(game.getCityAt(settlerPosition));
  }

  //Ensure, that archer unit action still works in this new actionstrategy.
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

  // Ensure, that a B52 removes a city, when the city is bombed and has one population.
  @Test
  public void b52RemovesCityWhenBombedAt1Population(){
    Position redB52Position = new Position(1, 2);
    Position blueCityPosition = new Position(4,1);
    game.moveUnit(redB52Position, new Position(3,1));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(new Position(3,1), blueCityPosition);
    assertNotNull(game.getCityAt(blueCityPosition));
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    game.performUnitActionAt(blueCityPosition);
    assertNull(game.getCityAt(blueCityPosition));
  }

  // Ensure, that B52 converts Forest tiles to plains tiles.
  @Test
  public void b52ConvertsForestToPlains(){
    Position redB52Position = new Position(1, 2);
    Position forestPosition = new Position(3,1);
    assertThat(game.getUnitAt(redB52Position).getTypeString(), is(GameConstants.B52));
    game.moveUnit(redB52Position, new Position(2,1));
    assertThat(game.getUnitAt(new Position(2,1)).getTypeString(), is(GameConstants.B52));
    game.moveUnit(new Position(2,1), forestPosition);
    assertThat(game.getUnitAt(forestPosition).getTypeString(), is(GameConstants.B52));
    assertThat(game.getTileAt(forestPosition).getTypeString(), is(GameConstants.FOREST));
    game.performUnitActionAt(forestPosition);
    assertThat(game.getTileAt(forestPosition).getTypeString(), is(GameConstants.PLAINS));
  }

  //Ensure, that B52 can overfly a city without conquering it.
  @Test
  public void b52DoesntCaptureEmptyCities(){
    Position redB52Position = new Position(1, 2);
    Position blueCityPosition = new Position(4,1);
    assertNull(game.getUnitAt(blueCityPosition));
    assertThat(game.getCityAt(blueCityPosition).getOwner(), is(Player.BLUE));
    game.moveUnit(redB52Position, new Position(3,1));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(new Position(3,1), blueCityPosition);
    assertThat(game.getCityAt(blueCityPosition).getOwner(), is(Player.BLUE));
  }

  //Ensure, that B52 can still conquer cities normally.
  @Test
  public void b52CapturesOccupiedCities(){
    Position redB52Position = new Position(1, 2);
    Position blueCityPosition = new Position(4,1);
    Position blueSettlerPosition = new Position(3,1);

    assertNotNull(game.getUnitAt(blueSettlerPosition));
    assertNull(game.getUnitAt(blueCityPosition));
    game.endOfTurn();
    game.moveUnit(blueSettlerPosition, blueCityPosition);
    assertThat(game.getUnitAt(blueCityPosition).getTypeString(), is(GameConstants.SETTLER));
    assertThat(game.getCityAt(blueCityPosition).getOwner(), is(Player.BLUE));
    game.endOfTurn();
    game.moveUnit(redB52Position, new Position(3,1));
    assertThat(game.getUnitAt(new Position(3,1)).getTypeString(), is(GameConstants.B52));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(new Position(3,1), blueCityPosition);
    assertThat(game.getUnitAt(blueCityPosition).getOwner(), is(Player.RED));
    assertThat(game.getCityAt(blueCityPosition).getOwner(), is(Player.RED));
  }
}

// ================================== TEST STUBS ===
class TestThetaCivMapStrategy implements MapStrategy {
  public void createWorld(GameImpl game) {
    for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
      for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
        game.createTile(new Position(i, j), new TileImpl(GameConstants.PLAINS));
      }
    }
    game.createTile(new Position(1, 0), new TileImpl(GameConstants.OCEANS));
    game.createTile(new Position(0, 1), new TileImpl(GameConstants.HILLS));
    game.createTile(new Position(1, 1), new TileImpl(GameConstants.MOUNTAINS));
    game.createTile(new Position(2, 2), new TileImpl(GameConstants.MOUNTAINS));
    game.createTile(new Position(3,1), new TileImpl(GameConstants.FOREST));
    game.createCity(new Position(1, 1), new CityImpl(Player.RED));
    game.createCity(new Position(4, 1), new CityImpl(Player.BLUE));
    game.createUnit(new Position(2, 0), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createUnit(new Position(3, 2), new UnitImpl(Player.BLUE, GameConstants.LEGION, 1));
    game.createUnit(new Position(3,1), new UnitImpl(Player.BLUE, GameConstants.SETTLER, 1));
    game.createUnit(new Position(4, 3), new UnitImpl(Player.RED, GameConstants.SETTLER, 1));
    game.createUnit(new Position(1, 2), new UnitImpl(Player.RED, GameConstants.B52, 2));
  }
}