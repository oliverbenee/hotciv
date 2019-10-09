package hotciv.standard;

import hotciv.factory.SemiCivFactory;
import hotciv.factory.TestEpsilonCivFactory;
import hotciv.factory.TestSemiCivFactory;
import hotciv.framework.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestSemiCiv {
  private GameImpl game;

  /**
   * Fixture for SemiCiv testing.
   */
  @Before
  public void setUp() {
    MapStrategy mapStrategy = new DeltaCivMapStrategy();
    game = new GameImpl(new TestSemiCivFactory());
    mapStrategy.createWorld(game);
  }

  /**
   * Tests for ensuring BetaCivAgeStrategy works in SemiCiv.
   */

  // Ensure, that game age advances by 100 years when round ends, during the time the years are -4000 to -100
  @Test
  public void between4000BCand100BC100yearsPassPerRound() {
    assertThat(game.getAge(), is(-4000));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(-3900));
    for (int i = 0; i < 76; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(-100));
  }

  //Ensure, that Around birth of Christ the sequence is -100, -1, +1, +50.
  @Test
  public void ensureAgeSequenceWorks() {
    assertThat(game.getAge(), is(-4000));
    for (int i = 0; i < 78; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(-100));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(-1));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(1));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(50));
  }

  //Ensure, that Between 50AD and 1750 50 years pass per round.
  @Test
  public void ensureBetween50ADAnd1750AD50YearsPass() {
    for (int i = 0; i < 84; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(50));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(100));
    for (int i = 0; i < 66; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1750));
  }

  //Ensure, that Between 1750 and 1900 25 years pass per round.
  @Test
  public void ensureBetween1750ADAnd1900AD25YearsPass() {
    for (int i = 0; i < 152; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1750));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(1775));
    for (int i = 0; i < 10; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1900));
  }

  //Ensure, that Between 1900 and 1970 5 years pass per round.
  @Test
  public void ensureBetween1900ADAnd1970AD5YearsPass() {
    for (int i = 0; i < 164; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1900));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(1905));
    for (int i = 0; i < 26; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1970));
  }

  //Ensure, that After 1970 1 years pass per round.
  @Test
  public void ensureAfter1970AD1YearPass() {
    for (int i = 0; i < 192; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1970));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(1971));
  }

  /**
   * Tests for ensuring GammaCivActionStrategy works in SemiCiv.
   */

  // Ensure, that Settler can found city, and the owner is correct.
  @Test
  public void settlerCanFoundCity() {
    Position cityPosition = new Position(8,12);
    Position newCityPosition = new Position(8,13);
    game.changeProductionInCityAt(cityPosition, GameConstants.SETTLER);
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
    assertThat(game.getUnitAt(cityPosition).getTypeString(), is(GameConstants.SETTLER));
    game.moveUnit(cityPosition, newCityPosition);
    game.performUnitActionAt(newCityPosition);
    assertThat(game.getCityAt(newCityPosition).getOwner(), is(Player.RED));
  }

  // Ensure, that the new City's population is 1.
  @Test
  public void newCityPopulationSizeIsOne() {
    Position cityPosition = new Position(8,12);
    Position newCityPosition = new Position(8,13);
    game.changeProductionInCityAt(cityPosition, GameConstants.SETTLER);
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
    assertThat(game.getUnitAt(cityPosition).getTypeString(), is(GameConstants.SETTLER));
    game.moveUnit(cityPosition, newCityPosition);
    game.performUnitActionAt(newCityPosition);
    assertThat(game.getCityAt(newCityPosition).getSize(), is(1));
  }

  // Ensure, that settler is removed from the world after creating a new city.
  @Test
  public void settlerIsRemovedAfterFoundingACity() {
    Position cityPosition = new Position(8,12);
    Position newCityPosition = new Position(8,13);
    game.changeProductionInCityAt(cityPosition, GameConstants.SETTLER);
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
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    game.moveUnit(cityPosition, newCityPosition);
    game.performUnitActionAt(newCityPosition);
    assertNull(game.getUnitAt(new Position(4, 3)));
  }

  // Ensure, that archers can fortify and double their defensive strength.
  @Test
  public void archersDoubleTheirDefensiveStrengthWhenFortifying() {
    Position cityPosition = new Position(8,12);
    game.changeProductionInCityAt(cityPosition, GameConstants.ARCHER);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getUnitAt(cityPosition).getTypeString(), is(GameConstants.ARCHER));
    int inidef = game.getUnitAt(cityPosition).getDefensiveStrength();
    game.performUnitActionAt(cityPosition);
    int newdef = game.getUnitAt(cityPosition).getDefensiveStrength();
    assertEquals(newdef, inidef * 2);
  }

  // Ensure, that units lose movecount when moving.
  @Test
  public void unitsLoseMoveCountWhenMoving() {
    Position cityPosition = new Position(8,12);
    game.changeProductionInCityAt(cityPosition, GameConstants.ARCHER);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getUnitAt(cityPosition).getMoveCount(), is(1));
    game.moveUnit(cityPosition, new Position(2, 1));
    assertThat(game.getUnitAt(new Position(2, 1)).getMoveCount(), is(0));
  }

  // Ensure, that when archers fortify, they cannot move.
  @Test
  public void archersCannotMoveAfterFortifying() {
    Position cityPosition = new Position(8,12);
    Position targetPos = new Position(8,13);
    game.changeProductionInCityAt(cityPosition, GameConstants.ARCHER);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    assertThat(game.getUnitAt(cityPosition).getTypeString(), is(GameConstants.ARCHER));
    game.performUnitActionAt(cityPosition);
    assertThat(game.getUnitAt(cityPosition).getMoveCount(), is(0));
    game.moveUnit(cityPosition, targetPos);
    assertThat(game.getUnitAt(cityPosition).getTypeString(), is(GameConstants.ARCHER));
  }

  /**
   * Tests for ensuring DeltaCivMapStrategy works in SemiCiv.
   */

  //Ensure, that Red has a city at (8,12)
  @Test
  public void redHasACityAt8Point12() {
    Position redCityPosition = new Position(8, 12);
    assertThat(game.getCityAt(redCityPosition).getOwner(), is(Player.RED));
  }

  //Ensure, that Blue has a city at (4,5)
  @Test
  public void blueHasACityAt4Point5() {
    Position blueCityPosition = new Position(4, 5);
    assertThat(game.getCityAt(blueCityPosition).getOwner(), is(Player.BLUE));
  }

  /**
   * Tests for ensuring that EpsilonCivWinnerStrategy works in SemiCiv.
   */

  // Ensure, that the attack win counter for blue increases by 1 for each attack won.
  @Test
  public void blueLegionsAttackWinIsCounted() {
    Position blueLegionPosition = new Position(8, 1);
    Position redSettlerPosition = new Position(7, 1);
    game.createUnit(blueLegionPosition, new UnitImpl(Player.BLUE, GameConstants.LEGION, 1));
    game.createUnit(redSettlerPosition, new UnitImpl(Player.RED, GameConstants.SETTLER, 0));
    game.createTile(redSettlerPosition, new TileImpl(GameConstants.PLAINS));
    game.endOfTurn();
    game.createTile(blueLegionPosition, new TileImpl(GameConstants.MOUNTAINS));
    game.moveUnit(blueLegionPosition, redSettlerPosition);
    assertThat(game.getUnitAt(redSettlerPosition).getTypeString(), is(GameConstants.LEGION));
    int attacksWonByPlayer = game.getAttacksWonByPlayer(Player.BLUE);
    assertThat(attacksWonByPlayer, is(1));
  }

  // Ensure, that player blue wins when attacking 3 settlers.
  @Test
  public void blueAttacks3SettlersThatAreOnPlainsWhileLegionIsOnMountainAndWins() {
    Position blueLegionPosition = new Position(8, 1);
    Position redSettler1Position = new Position(7, 1);
    Position redSettler2Position = new Position(6, 1);
    Position redSettler3Position = new Position(5, 1);
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

  // Ensure, that there is no winner found before players have 3 wins.
  @Test
  public void noWinnerBefore3Wins() {
    assertNull(game.getWinner());
  }

  // Ensure, that defenses do not count towards victory.
  @Test
  public void defensesDontAddToVictory() {
    Position blueLegionPosition = new Position(3, 2);
    Position redSettlerPosition = new Position(3, 1);
    game.createUnit(redSettlerPosition, new UnitImpl(Player.RED, GameConstants.SETTLER, 0));
    game.moveUnit(redSettlerPosition, blueLegionPosition);
    int attacksWonByBlue = game.getAttacksWonByPlayer(Player.BLUE);
    assertThat(attacksWonByBlue, is(0));
  }

  /**
   * Tests for ensuring that EpsilonCivAttackStrategy works in SemiCiv.
   */

  // Ensure, that the defensive strength is 12 for an archer at a city on a forest with a roll of 6.
  @Test
  public void ensureDefensiveStrengthIs12ForArcherAtCityOnForestWithARollOf6() {
    EpsilonCivAttackStrategy as = new EpsilonCivAttackStrategy(new DieStub(6));
    game.createUnit(new Position(9, 9), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createTile(new Position(9, 9), new TileImpl(GameConstants.FOREST));
    int createdUnitsBaseDefense = 3;
    int alliedCityFactor = 1;
    int defenderTerrainFactor = 2;
    int allyUnitFactor = 0;
    int dieValue = 6;
    int correctDefense = (createdUnitsBaseDefense * alliedCityFactor * defenderTerrainFactor + allyUnitFactor) * dieValue;
    assertEquals(correctDefense, as.calculateDefensiveStrength(game, new Position(9, 9)) * dieValue);
  }

  // Ensure, that the defensive strength is 108 for an archer at a city on a forest with a roll of 4 with an ally unit factor of 10.
  @Test
  public void ensureDefensiveStrengthIs108ForArcherAtCityOnForestWithARollOf4withAllyUnitFactorOf10() {
    game.createCity(new Position(2, 1), new CityImpl(Player.RED));
    EpsilonCivAttackStrategy as = new EpsilonCivAttackStrategy(new DieStub(4));
    game.createUnit(new Position(2, 1), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createTile(new Position(2, 1), new TileImpl(GameConstants.FOREST));
    int createdUnitsBaseDefense = 3;
    int alliedCityFactor = 3;
    int defenderTerrainFactor = 2;
    int allyUnitFactor = 9;
    int dieValue = 4;
    int correctDefense = (createdUnitsBaseDefense * alliedCityFactor * defenderTerrainFactor + allyUnitFactor) * dieValue;
    assertEquals(correctDefense, as.calculateDefensiveStrength(game, new Position(2, 1)) * dieValue);
  }

  // Ensure, that the attacking strength is 30 for an acher at a city with 3 neighbors getting a roll of 6.
  @Test
  public void ensureStrengthIs30ForArcherAtCityWith3NeighborsWithARollOf6() {
    EpsilonCivAttackStrategy as = new EpsilonCivAttackStrategy(new DieStub(6));
    Position cityPosition = new Position(2, 1);
    game.createCity(cityPosition, new CityImpl(Player.RED));
    game.createUnit(cityPosition, new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createUnit(new Position(2, 2), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createUnit(new Position(2, 0), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createUnit(new Position(3, 1), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    assertThat((3 + 1 + 1) * 3 * 2, is(as.calculateDefensiveStrength(game, cityPosition)));
  }

  // Ensure, that the attack fails, if a settler attacks a legion.
  @Test
  public void attackFailsIfSettlerAttacksLegion() {
    EpsilonCivAttackStrategy as = new EpsilonCivAttackStrategy(new DieStub(6));
    Position settlerPosition = new Position(9, 9);
    Position legionPosition = new Position(8, 8);
    game.createUnit(new Position(9, 9), new UnitImpl(Player.RED, GameConstants.SETTLER, 1));
    game.createUnit(new Position(8, 8), new UnitImpl(Player.BLUE, GameConstants.LEGION, 1));
    game.moveUnit(settlerPosition, legionPosition);

  }
}