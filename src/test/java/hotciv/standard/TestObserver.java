package hotciv.standard;
import hotciv.factory.SemiCivFactory;
import hotciv.factory.TestEpsilonCivFactory;
import hotciv.factory.TestSemiCivFactory;
import hotciv.factory.ThetaCivFactory;
import hotciv.framework.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestObserver {
  private GameImpl game;
  private GameObserver observer;

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    MapStrategy mapStrategy = new TestThetaCivMapStrategy();
    game = new GameImpl(new ThetaCivFactory());
    mapStrategy.createWorld(game);
    observer = new GameObserverImpl();
  }


  @Test
  public void endturn(){
    game.endOfTurn();
    String correct = "The player in turn is " + game.getPlayerInTurn() + " and the age is " + game.getAge();
    assertThat(((GameObserverImpl) observer).getList(1), is(correct));
    System.out.println(((GameObserverImpl) observer).getList(1));
  }
}
