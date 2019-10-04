package hotciv.standard;

import hotciv.factory.TestEpsilonCivFactory;
import hotciv.framework.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestEpsilonCiv {
  private GameImpl game;

  /**
   * Fixture for EpsilonCiv testing.
   */
  @Before
  public void setUp() {
    MapStrategy mapStrategy = new AlphaCivMapStrategy();
    game = new GameImpl(new TestEpsilonCivFactory());
    mapStrategy.createWorld(game);
    game.createUnit(new Position(3,3), new UnitImpl(Player.BLUE, GameConstants.LEGION, 1));
  }
  @Test
  public void blueLegionsAttackWinIsCounted(){
    Position blueLegionPosition = new Position(3,2);
    Position redSettlerPosition = new Position(2,1);
    game.createUnit(redSettlerPosition, new UnitImpl(Player.RED, GameConstants.SETTLER, 0));
    game.endOfTurn();
    game.moveUnit(blueLegionPosition, redSettlerPosition);
    assertThat(game.getUnitAt(redSettlerPosition).getTypeString(), is(GameConstants.LEGION));
    int attacksWonByPlayer = game.getAttacksWonByPlayer(Player.BLUE);
    assertThat(attacksWonByPlayer, is(1));
  }
  @Test
  public void blueAttacks3SettlersThatAreOnPlainsWhileLegionIsOnMountainAndWins(){
    Position blueLegionPosition = new Position(8,1);
    Position redSettler1Position = new Position(7,1);
    Position redSettler2Position = new Position(6,1);
    Position redSettler3Position = new Position(5,1);
    game.createUnit(blueLegionPosition, new UnitImpl(Player.BLUE, GameConstants.LEGION, 1));
    game.createUnit(redSettler1Position, new UnitImpl(Player.RED, GameConstants.SETTLER, 0));
    game.createTile(redSettler1Position, new TileImpl(GameConstants.PLAINS));
    game.createUnit(redSettler2Position, new UnitImpl(Player.RED, GameConstants.SETTLER, 0));
    game.createTile(redSettler2Position, new TileImpl(GameConstants.PLAINS));
    game.createUnit(redSettler3Position, new UnitImpl(Player.RED, GameConstants.SETTLER, 0));
    game.createTile(redSettler3Position, new TileImpl(GameConstants.PLAINS));
    game.endOfTurn();

    game.createTile(blueLegionPosition, new TileImpl(GameConstants.MOUNTAINS));
    game.moveUnit(blueLegionPosition, redSettler1Position);
    assertThat(game.getUnitAt(redSettler1Position).getTypeString(), is(GameConstants.LEGION));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(redSettler1Position, redSettler2Position);
    assertThat(game.getUnitAt(redSettler2Position).getTypeString(), is(GameConstants.LEGION));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(redSettler2Position, redSettler3Position);
    int attacksWonByPlayer = game.getAttacksWonByPlayer(Player.BLUE);
    assertThat(attacksWonByPlayer, is(3));
    assertThat(game.getWinner(), is(Player.BLUE));
  }
  @Test
  public void noWinnerBefore3Wins(){
    assertNull(game.getWinner());
  }
  @Test
  public void defensesDontAddToVictory(){
    Position blueLegionPosition = new Position(3,2);
    Position redSettlerPosition = new Position(3,1);
    game.createUnit(redSettlerPosition, new UnitImpl(Player.RED, GameConstants.SETTLER, 0));
    game.moveUnit(redSettlerPosition, blueLegionPosition);
    int attacksWonByBlue = game.getAttacksWonByPlayer(Player.BLUE);
    assertThat(attacksWonByBlue, is(0));
  }
  @Test
  public void ensureDefensiveStrengthIs12ForArcherAtCityOnForestWithARollOf6(){
    EpsilonCivAttackStrategy as = new EpsilonCivAttackStrategy(new DieStub(6));
    game.createUnit(new Position(9,9), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createTile(new Position(9,9), new TileImpl(GameConstants.FOREST));
    int createdUnitsBaseDefense = 3;
    int alliedCityFactor = 1;
    int defenderTerrainFactor = 2;
    int allyUnitFactor = 0;
    int dieValue = 6;
    int correctDefense = (createdUnitsBaseDefense*alliedCityFactor*defenderTerrainFactor+allyUnitFactor)*dieValue;
    assertEquals(correctDefense, as.calculateDefensiveStrength(game, new Position(9,9))*dieValue);
  }
  @Test
  public void ensureDefensiveStrengthIs40ForArcherAtCityOnForestWithARollOf4withAllyUnitFactorOf10(){
    game.createCity(new Position(2,1), new CityImpl(Player.RED));
    EpsilonCivAttackStrategy as = new EpsilonCivAttackStrategy(new DieStub(4));
    game.createUnit(new Position(2,1), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createTile(new Position(2,1), new TileImpl(GameConstants.FOREST));
    int createdUnitsBaseDefense = 3;
    int alliedCityFactor = 3;
    int defenderTerrainFactor = 2;
    int allyUnitFactor = 10;
    int dieValue = 4;
    int correctDefense = (createdUnitsBaseDefense*alliedCityFactor*defenderTerrainFactor+allyUnitFactor)*dieValue;
    assertEquals(correctDefense, as.calculateDefensiveStrength(game, new Position(2,1))*dieValue);
  }
  @Test
  public void ensureStrengthIs30ForArcherAtCityWith3NeighborsWithARollOf6(){
    EpsilonCivAttackStrategy as = new EpsilonCivAttackStrategy(new DieStub(6));
    Position cityPosition = new Position(2,1);
    game.createCity(cityPosition, new CityImpl(Player.RED));
    game.createUnit(cityPosition, new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createUnit(new Position(2,2), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createUnit(new Position(2,0), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createUnit(new Position(3,1), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    assertThat((3+1+1)*3*2, is(as.calculateDefensiveStrength(game, cityPosition)));
  }
  @Test
  public void attackFailsIfSettlerAttacksLegion(){
    EpsilonCivAttackStrategy as = new EpsilonCivAttackStrategy(new DieStub(6));
    Position settlerPosition = new Position(9,9);
    Position legionPosition = new Position(8,8);
    game.createUnit(new Position(9,9), new UnitImpl(Player.RED, GameConstants.SETTLER, 1));
    game.createUnit(new Position(8,8), new UnitImpl(Player.BLUE, GameConstants.LEGION, 1));
    game.moveUnit(settlerPosition, legionPosition);

  }
}
// ================================== TEST STUBS ===
class DieStub implements DieStrategy {
  private int eyes;

  public DieStub(int eyes){ this.eyes = eyes;}
  public int rollDie(){
    return eyes;
  }
}