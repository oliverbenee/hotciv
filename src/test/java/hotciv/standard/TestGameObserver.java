package hotciv.standard;

import hotciv.factory.ThetaCivFactory;
import hotciv.framework.Game;
import hotciv.framework.GameObserver;
import hotciv.framework.MapStrategy;
import org.junit.Before;
import org.junit.Test;
import hotciv.factory.AlphaCivFactory;
import hotciv.factory.GammaCivFactory;
import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;


public class TestGameObserver {
  private Game game;
  private GameObserverImpl observer;

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    MapStrategy mapStrategy = new AlphaCivMapStrategy();
    game = new GameImpl(new ThetaCivFactory());
    mapStrategy.createWorld((GameImpl) game);
    observer = new GameObserverImpl();
    game.addObserver(observer);
  }

  @Test
  public void shouldSavePlayerMovingUnit(){
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    Position startPosition = new Position(2,0);
    Position endPosition = new Position(3,0);
    game.moveUnit(startPosition, endPosition);
    assertNotNull(observer.getList(0));
    System.out.println(observer.getList(0));
    assertNotNull(observer.getList(1));
    System.out.println(observer.getList(1));
  }

  @Test
  public void shouldSavePlayerEndingTurnWhenEndOfTurnIsCalled(){
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    game.endOfTurn();
    assertNotNull(observer.getList(0));
    System.out.println(observer.getList(0));
  }

  @Test
  public void shouldSavePlayerConqueringCity(){
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    Position startPosition = new Position(2,0);
    Position middlePosition = new Position(3,1);
    Position cityPosition = new Position(4,1);
    game.moveUnit(startPosition, middlePosition);
    String firstPositionUpdate = observer.getList(0);
    assertNotNull(firstPositionUpdate);
    System.out.println(firstPositionUpdate);
    String secondPositionUpdate = observer.getList(1);
    assertNotNull(secondPositionUpdate);
    System.out.println(secondPositionUpdate);
    game.endOfTurn();
    String firstTurnUpdate = observer.getList(2);
    assertNotNull(firstTurnUpdate);
    System.out.println(firstTurnUpdate);
    game.endOfTurn();
    String secondTurnUpdate = observer.getList(3);
    assertNotNull(secondTurnUpdate);
    System.out.println(secondTurnUpdate);
    game.moveUnit(middlePosition, cityPosition);
    assertThat(game.getCityAt(cityPosition).getOwner(), is(Player.RED));
    String thirdPositionUpdate = observer.getList(4);
    assertNotNull(thirdPositionUpdate);
    System.out.println(thirdPositionUpdate);
  }

  @Test
  public void shouldSavePlayerCreatingACity(){
    Position settlerPosition = new Position(4,3);
    assertThat(game.getUnitAt(settlerPosition).getOwner(), is(game.getPlayerInTurn()));
    assertThat(game.getUnitAt(settlerPosition).getTypeString(), is(GameConstants.SETTLER));
    game.performUnitActionAt(settlerPosition);
    String afterAction = observer.getList(0);
    assertNotNull(afterAction);
    System.out.println(afterAction);
  }

  @Test
  public void shouldSaveWorldChangeWhenProductionIsChanged(){
    Position redCityPosition = new Position(1,1);
    assertNotNull(game.getCityAt(redCityPosition));
    game.changeProductionInCityAt(redCityPosition, GameConstants.SETTLER);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.SETTLER));
    String action = observer.getList(0);
    assertNotNull(action);
    System.out.println(action);
  }

  @Test
  public void shouldSavePlayerPerformingUnitAction(){
    Position redSettlerPosition = new Position(4,3);
    assertThat(observer.getSize(), is(0));
    game.performUnitActionAt(redSettlerPosition);
    assertNotNull(observer.getList(0));
    System.out.println(observer.getList(0));
  }
}
