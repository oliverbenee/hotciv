package hotciv.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.Game;
import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.standard.AlphaCivMapStrategy;
import hotciv.standard.GameImpl;
import hotciv.standard.UnitImpl;
import hotciv.stub.StubGame2;
import hotciv.framework.*;
import org.junit.*;
import org.junit.Before;
import hotciv.factory.AlphaCivFactory;
import javax.rmi.CORBA.Stub;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGameBroker {
  private Game game;
  private GameObserver nullObserver;

  /** Fixture for broker testing. */
  @Before
  public void setUp() {
    // Server side implementation.
    Game servant = new StubGame3();
    GameObserver nullObserver = new NullObserver();
    servant.addObserver(nullObserver);

    // Server side broker implementations
    Invoker invoker = new HotCivGameInvoker(servant);

    // Create client side broker implementations, using the local
    // method client request handler to avoid any real IPC layer.
    ClientRequestHandler crh =
            new LocalMethodClientRequestHandler(invoker);
    Requestor requestor =
            new StandardJSONRequestor(crh);

    game = new GameProxy(requestor);
    game.addObserver(nullObserver);
  }

  // Tests of GameProxy, part 1
  @Test
  public void shouldGetGameWinner(){
    Player winner = game.getWinner();
    assertThat(winner, is(Player.YELLOW));
  }

  @Test
  public void shouldGetGameAge(){
    int age = game.getAge();
    assertThat(age, is(69));
  }

  @Test
  public void shouldGetPlayerInTurn(){
    Player inTurn = game.getPlayerInTurn();
    assertThat(inTurn, is(Player.RED));
  }

  @Test
  public void shouldEndTurn(){
    Player inTurn = game.getPlayerInTurn();
    assertThat(inTurn, is(Player.RED));
    game.endOfTurn();
    Player secondInTurn = game.getPlayerInTurn();
    assertThat(secondInTurn, is(Player.YELLOW));
  }
}