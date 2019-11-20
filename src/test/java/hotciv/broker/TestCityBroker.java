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

public class TestCityBroker {
  private City city;

  /** Fixture for broker testing. */
  @Before
  public void setUp() {
    // Server side implementation.
    City servant = new StubCity2(Player.GREEN);

    // Server side broker implementations
    Invoker invoker = new HotCivCityInvoker(servant);

    // Create client side broker implementations, using the local
    // method client request handler to avoid any real IPC layer.
    ClientRequestHandler crh =
            new LocalMethodClientRequestHandler(invoker);
    Requestor requestor =
            new StandardJSONRequestor(crh);
    city = new CityProxy(requestor);
  }

  // Get City Owner
  @Test
  public void shouldGetGreenAsOwner(){
    Player owner = city.getOwner();
    assertThat(owner, is(Player.GREEN));
  }

  // Get City size
  @Test
  public void shouldGet4AsSize(){
    int population = city.getSize();
    assertThat(population, is(4));
  }

  // Get City treasury
  @Test
  public void shouldGet10AsTreasury(){
    int treasury = city.getTreasury();
    assertThat(treasury, is(10));
  }

  // Get city production
  @Test
  public void shouldGetArcherAsProducedForCity(){
    String production = city.getProduction();
    assertThat(production, is(GameConstants.ARCHER));
  }

  // Get city workforce focus
  @Test
  public void shouldGetFoodFocusAsFocusForCity(){
    String focus = city.getWorkforceFocus();
    assertThat(focus, is(GameConstants.foodFocus));
  }
}
