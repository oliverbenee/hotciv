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
    game = new GameImpl(new AlphaCivWinnerStrategy(), new AlphaCivAgeStrategy(), new GammaCivActionStrategy(), mapStrategy, new AlphaCivAttackStrategy());
    mapStrategy.createWorld(game);
  }
}
