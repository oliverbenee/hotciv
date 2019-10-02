package hotciv.standard;

import hotciv.factory.AlphaCivFactory;
import hotciv.factory.EpsilonCivFactory;
import hotciv.framework.*;
import org.junit.*;

import java.util.HashMap;

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
    game = new GameImpl(new EpsilonCivFactory());
    mapStrategy.createWorld(game);
    game.createUnit(new Position(3,3), new UnitImpl(Player.BLUE, GameConstants.LEGION, 1));
  }

  @Test
  public void blueLegionsAttackWinIsCounted(){
    Position blueLegionPosition = new Position(3,2);
    Position redVillagerPosition = new Position(2,1);
    game.createUnit(redVillagerPosition, new UnitImpl(Player.RED, "Villager", 0));
    game.endOfTurn();
    game.moveUnit(blueLegionPosition, redVillagerPosition);
    assertThat(game.getUnitAt(redVillagerPosition).getTypeString(), is(GameConstants.LEGION));
    int attacksWonByPlayer = game.getAttacksWonByPlayer(Player.BLUE);
    assertThat(attacksWonByPlayer, is(1));
  }

  @Test
  public void blueAttacks3VillagersAndWins(){
    Position blueLegionPosition = new Position(3,2);
    Position redVillager1Position = new Position(2,0);
    Position redVillager2Position = new Position(2,1);
    Position redVillager3Position = new Position(3,1);
    game.createUnit(redVillager1Position, new UnitImpl(Player.RED, "Villager", 0));
    game.createUnit(redVillager2Position, new UnitImpl(Player.RED, "Villager", 0));
    game.createUnit(redVillager3Position, new UnitImpl(Player.RED, "Villager", 0));
    game.endOfTurn();

    game.moveUnit(blueLegionPosition, redVillager1Position);
    assertThat(game.getUnitAt(redVillager1Position).getTypeString(), is(GameConstants.LEGION));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(redVillager1Position, redVillager2Position);
    assertThat(game.getUnitAt(redVillager2Position).getTypeString(), is(GameConstants.LEGION));
    game.endOfTurn();
    game.endOfTurn();
    game.moveUnit(redVillager2Position, redVillager3Position);
    assertThat(game.getUnitAt(redVillager3Position).getTypeString(), is(GameConstants.LEGION));
    int attacksWonByPlayer = game.getAttacksWonByPlayer(Player.BLUE);
    assertThat(attacksWonByPlayer, is(3));
    assertThat(game.getWinner(), is(Player.BLUE));
  }

  @Test
  public void defensesDontAddToVictory(){
    Position blueLegionPosition = new Position(3,2);
    Position redVillagerPosition = new Position(3,1);
    game.createUnit(redVillagerPosition, new UnitImpl(Player.RED, "Villager", 0));
    game.moveUnit(redVillagerPosition, blueLegionPosition);
    int attacksWonByBlue = game.getAttacksWonByPlayer(Player.BLUE);
    assertThat(attacksWonByBlue, is(0));
  }

  @Test
  public void ensureStrengthIs28ForArcherAtCity(){
    game.createCity(new Position(2,1), new CityImpl(Player.RED));
    EpsilonCivAttackStrategy as = new EpsilonCivAttackStrategy();
    game.createUnit(new Position(2,1), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createTile(new Position(2,1), new TileImpl(GameConstants.FOREST));
    int createdUnitsBaseDefense = 3;
    int alliedCityFactor = 3;
    int defenderTerrainFactor = 2;
    //TODO: This estimate of allied units is most likely wrong, and needs double checking...
    int allyUnitFactor = 10;
    int correctDefense = createdUnitsBaseDefense*alliedCityFactor*defenderTerrainFactor+allyUnitFactor;
    assertEquals(correctDefense, as.calculateDefensiveStrength(game, new Position(2,1)));
  }
}