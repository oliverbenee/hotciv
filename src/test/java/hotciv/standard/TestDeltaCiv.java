package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

public class TestDeltaCiv {
  private GameImpl game;

  /**
   * Fixture for deltaciv testing.
   */
  @Before
  public void setUp() {
    MapStrategy mapStrategy = new DeltaCivMapStrategy();
    game = new GameImpl(new AlphaCivWinnerStrategy(), new AlphaCivAgeStrategy(), new GammaCivActionStrategy(), mapStrategy);
    mapStrategy.createWorld(game);
  }

  //Ensure, that Red has a city at (8,12)
  @Test
  public void redHasACityAt8Point12(){
    Position redCityPosition = new Position(8,12);
    assertThat(game.getCityAt(redCityPosition).getOwner(), is(Player.RED));
  }
}
