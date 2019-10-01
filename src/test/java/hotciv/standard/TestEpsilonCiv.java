package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

public class TestEpsilonCiv {
  private GameImpl game;

  /**
   * Fixture for EpsilonCiv testing.
   */
  @Before
  public void setUp() {
    MapStrategy mapStrategy = new AlphaCivMapStrategy();
    game = new GameImpl(new EpsilonCivWinnerStrategy(), new AlphaCivAgeStrategy(), new AlphaCivActionStrategy(), mapStrategy, new EpsilonCivAttackStrategy());
    mapStrategy.createWorld(game);
  }

  @Test
  public void blueLegionsWinIsCounted(){
    Position blueLegionPosition = new Position(3,2);
    Position redArcherPosition = new Position(2,0);
    game.endOfTurn();
    game.moveUnit(blueLegionPosition, redArcherPosition);
    assertThat(game.getUnitAt(redArcherPosition).getTypeString(), is(GameConstants.LEGION));
    assertThat(game.getAttacksWonByPlayer(game.getPlayerInTurn()), is(1));
  }
}
