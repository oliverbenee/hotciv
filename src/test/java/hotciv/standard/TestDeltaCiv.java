package hotciv.standard;

import hotciv.factory.AlphaCivFactory;
import hotciv.factory.DeltaCivFactory;
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
    game = new GameImpl(new DeltaCivFactory());
    mapStrategy.createWorld(game);
  }

  //Ensure, that Red has a city at (8,12)
  @Test
  public void redHasACityAt8Point12(){
    Position redCityPosition = new Position(8,12);
    assertThat(game.getCityAt(redCityPosition).getOwner(), is(Player.RED));
  }

  //Ensure, that Blue has a city at (4,5)
  @Test
  public void blueHasACityAt4Point5(){
    Position blueCityPosition = new Position(4,5);
    assertThat(game.getCityAt(blueCityPosition).getOwner(), is(Player.BLUE));
  }
}
