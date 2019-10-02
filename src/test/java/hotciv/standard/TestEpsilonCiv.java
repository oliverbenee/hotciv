package hotciv.standard;

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
    game = new GameImpl(new EpsilonCivWinnerStrategy(),
            new AlphaCivAgeStrategy(),
            new AlphaCivActionStrategy(),
            mapStrategy,
            new EpsilonCivFixedAttackStrategy());
    mapStrategy.createWorld(game);
    game.createUnit(new Position(3,3), new UnitImpl(Player.BLUE, GameConstants.LEGION, 1, GameConstants.getDefensiveStrength(GameConstants.LEGION), GameConstants.getAttackingStrength(GameConstants.LEGION)));
  }

  @Test
  public void blueLegionsAttackWinIsCounted(){
    Position blueLegionPosition = new Position(3,2);
    Position redArcherPosition = new Position(2,0);
    game.endOfTurn();
    game.moveUnit(blueLegionPosition, redArcherPosition);
    assertThat(game.getUnitAt(redArcherPosition).getTypeString(), is(GameConstants.LEGION));
    int attacksWonByPlayer = game.getAttacksWonByPlayer(Player.BLUE);
    assertThat(attacksWonByPlayer, is(1));
  }

  @Test
  public void blueAttacks3VillagersAndWins(){
    Position blueLegionPosition = new Position(3,2);
    Position redVillager1Position = new Position(2,0);
    Position redVillager2Position = new Position(2,1);
    Position redVillager3Position = new Position(3,1);
    game.createUnit(redVillager1Position, new UnitImpl(Player.RED, "Villager", 0, 0,0));
    game.createUnit(redVillager2Position, new UnitImpl(Player.RED, "Villager", 0, 0,0));
    game.createUnit(redVillager3Position, new UnitImpl(Player.RED, "Villager", 0, -10,-10));
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
  public void strongestUnitWins(){
    Position blueUnitPosition = new Position(3,3);

  }
}
