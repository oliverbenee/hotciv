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
import javafx.geometry.Pos;
import org.junit.*;
import org.junit.Before;
import hotciv.factory.AlphaCivFactory;
import javax.rmi.CORBA.Stub;
import java.lang.reflect.Array;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestGameBroker {
  private Game game;
  private StubGame3 servant;
  private ClientRequestHandler crh, lmcrh;

  /** Fixture for broker testing. */
  @Before
  public void setUp() {
    // Server side implementation.
    servant = new StubGame3();

    // Server side broker implementations
    Invoker invoker = new HotCivGameRootInvoker(servant);

    // Create client side broker implementations, using the local
    // method client request handler to avoid any real IPC layer.
    lmcrh =
            new LocalMethodClientRequestHandler(invoker);
    crh = lmcrh;
    Requestor requestor =
            new StandardJSONRequestor(crh);

    game = new GameProxy(requestor);
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
    // 69 is set as unique value.
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

  @Test
  public void shouldMoveUnit() {
    Position position_of_red_archer = new Position(2,0);
    Position end_position = new Position(3,1);
    boolean moved = game.moveUnit(position_of_red_archer, end_position);
    assertThat(moved, is(true));
  }

 /*
  * BROKER 2.
  */

  /*
   * Get tile at methods and the associated getTypeString() method.
   */

  @Test
  public void shouldGetTileAt1Point1(){
    Position mountainPosition = new Position(1,1);
    assertNotNull(game.getTileAt(mountainPosition));
  }

  @Test
  public void shouldGetTileTypeMountainsAt1Point1(){
    Position mountainsPosition = new Position(1,1);
    assertThat(game.getTileAt(mountainsPosition).getTypeString(), is(GameConstants.MOUNTAINS));
  }

  @Test
  public void shouldGetTileTypeHillsAt0Point1(){
    Position hillsPosition = new Position(0,1);
    assertThat(game.getTileAt(hillsPosition).getTypeString(), is(GameConstants.HILLS));
  }

  @Test
  public void shouldGetTileTypeOceansAt1Point0(){
    Position oceanPosition = new Position(1,0);
    assertThat(game.getTileAt(oceanPosition).getTypeString(), is(GameConstants.OCEANS));
  }

  @Test
  public void shouldGetTileTypePlainsAt5Point5(){
    Position plainsPosition = new Position(5,5);
    assertThat(game.getTileAt(plainsPosition).getTypeString(), is(GameConstants.PLAINS));
  }

  /*
   * City testing methods.
   * Directly transferred from TestCityBroker to testGameBroker during Broker 2.
   */

  @Test
  public void shouldGetCityAt1Point1(){
    Position position_of_green_city = new Position(1,1);
    City city = game.getCityAt(position_of_green_city);
    assertNotNull(city);
  }

  // Get City Owner
  @Test
  public void shouldGetGreenAsOwner(){
    Player owner = game.getCityAt(new Position(1,1)).getOwner();
    assertThat(owner, is(Player.GREEN));
  }

  // Get City size
  @Test
  public void shouldGet4AsSize(){
    int population = game.getCityAt(new Position(1,1)).getSize();
    assertThat(population, is(4));
  }

  // Get City treasury
  @Test
  public void shouldGet10AsTreasury(){
    int treasury = game.getCityAt(new Position(1,1)).getTreasury();
    assertThat(treasury, is(10));
  }

  // Get city production
  @Test
  public void shouldGetArcherAsProducedForCity(){
    String production = game.getCityAt(new Position(1,1)).getProduction();
    assertThat(production, is(GameConstants.ARCHER));
  }

  // Get city workforce focus
  @Test
  public void shouldGetFoodFocusAsFocusForCity(){
    String focus = game.getCityAt(new Position(1,1)).getWorkforceFocus();
    assertThat(focus, is(GameConstants.foodFocus));
  }

  // Change city production
  @Test
  public void shouldChangeProduction(){
    Position green_city_position = new Position(1,1);
    assertNotNull(game.getCityAt(green_city_position));
    assertThat(game.getCityAt(green_city_position).getProduction(), is(GameConstants.ARCHER));
    game.changeProductionInCityAt(green_city_position, GameConstants.B52);
    String last = servant.getLastType();
    assertThat(last, is(GameConstants.B52));
  }

  //Get unit at method.

  @Test
  public void shouldGetUnitAt2Point1(){
    Position position_of_red_archer = new Position(2,1);
    Unit unit = game.getUnitAt(position_of_red_archer);
    assertNotNull(unit);
  }

  //Get unit typestring
  @Test
  public void shouldGetUnitTypeString(){
    String type = game.getUnitAt(new Position(2,1)).getTypeString();
    assertThat(type, is(GameConstants.ARCHER));
  }

  //Get unit owner
  @Test
  public void shouldGetUnitOwner(){
    Player owner = game.getUnitAt(new Position(2,1)).getOwner();
    assertThat(owner, is(Player.RED));
  }

  //Get unit move count
  @Test
  public void shouldGetUnitMoveCountAs1(){
    int movecount = game.getUnitAt(new Position(2,1)).getMoveCount();
    assertThat(movecount, is(1));
  }

  //Get unit defensive Strength
  @Test
  public void shouldGetDefensiveStrength(){
    int defensiveStrength = game.getUnitAt(new Position(2,1)).getDefensiveStrength();
    // Set as 5 in stubGame3, the one being tested on.
    assertThat(defensiveStrength, is(5));
  }

  //Get unit attacking Strength
  @Test
  public void shouldGetAttackingStrength(){
    int attackingStrength = game.getUnitAt(new Position(2,1)).getAttackingStrength();
    // Set as 6 in StubGame3, the one being tested on.
    assertThat(attackingStrength, is(6));
  }

  //Perform unit action
  @Test
  public void shouldPerformAction(){
    Position position_of_red_archer = new Position(2,1);
    game.performUnitActionAt(
            position_of_red_archer
    );
    String last = servant.getLastType();
    assertThat(last, is("performed unit action"));
  }
}