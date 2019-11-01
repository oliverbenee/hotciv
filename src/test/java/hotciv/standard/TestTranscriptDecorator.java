package hotciv.standard;

import hotciv.factory.AlphaCivFactory;
import hotciv.factory.TestSemiCivFactory;
import hotciv.factory.ThetaCivFactory;
import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestTranscriptDecorator {
  private Game game;
  private Game decoratee;

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    MapStrategy mapStrategy = new TestTranscriptDecoratorMapStrategy();
    decoratee = new GameImpl(new ThetaCivFactory());
    mapStrategy.createWorld((GameImpl) decoratee);
    game = new TranscriptDecorator(decoratee);
  }

  private void toggleTranscript(){
    if(game == decoratee){
      //enable the logging by decorating the component
      decoratee = game ; // but remember the component
      game = new TranscriptDecorator(game);
    } else {
      //remove logging by making Game point to the component object once again
      game = decoratee;
    }
  }

  // Take Small Steps. Ensure, that something is transcripted from getWinner().
  @Test
  public void winnerIsTranscripted(){
    game.getWinner();
  }

  // Ensure, that when player RED wins, his name is transcribed.
  @Test
  public void transcriptRedAsWinnerInYear3000BC() {
    assertThat(game.getAge(), is(-4000));
    for(int i=0; i < 20; i++){
      game.endOfTurn();
    }
    assertThat(game.getWinner(), is(Player.RED));
  }

  // Ensure, that the game age can be transcribed.
  @Test
  public void ageIsTranscripted(){
    game.getAge();
  }

  // Ensure, that unit movement is transcribed.
  @Test
  public void movementIsTranscribed(){
    Position redB52Position = new Position(1,2);
    Position targetPosition = new Position(2,2);
    game.moveUnit(redB52Position, targetPosition);
  }

  // Ensure, that failed unit movement is transcribed.
  @Test
  public void failedMovementIsTranscribed(){
    Position redB52Position = new Position(1,2);
    Position targetPosition = new Position(2,0);
    game.moveUnit(redB52Position, targetPosition);
    assertNotEquals(game.getUnitAt(targetPosition).getTypeString(), GameConstants.B52);
  }

  // Ensure, that changing production in a city is transcribed.
  @Test
  public void changingProductionIsTranscribed(){
    Position redCityPosition = new Position(1,1);
    game.changeProductionInCityAt(redCityPosition, GameConstants.B52);
  }

  // Ensure, that end of turn is transcribed.
  @Test
  public void endOfTurnIsTranscribed(){
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    game.endOfTurn();
  }

  // Ensure, that unit actions are transcribed.
  @Test
  public void unitActionIsTranscribed(){
    Position redB52Position = new Position(1, 2);
    assertNotNull(game.getUnitAt(redB52Position));
    game.performUnitActionAt(redB52Position);
  }

  // Ensure, that nothing is printed, when the toggle command is called.
  @Test
  public void dontTranscribeAfterToggling(){
    toggleTranscript();
    System.out.println("NOTHING SHOULD BE IN THIS BOX: ");
    System.out.println("-------------------------------");
    Position redB52Position = new Position(1, 2);
    assertNotNull(game.getUnitAt(redB52Position));
    game.performUnitActionAt(redB52Position);
    System.out.println("-------------------------------");
    System.out.println("END OF NOTHING SHOULD BE IN THIS BOX");
  }

  // Ensure, that something is printed, when the toggle command is called a second time
  @Test
  public void doTranscribeAfterTogglingTwice(){
    toggleTranscript();
    toggleTranscript();
    System.out.println("SOMETHING SHOULD BE IN THIS BOX: ");
    System.out.println("-------------------------------");
    Position redB52Position = new Position(1, 2);
    assertNotNull(game.getUnitAt(redB52Position));
    game.performUnitActionAt(redB52Position);
    System.out.println("-------------------------------");
    System.out.println("END OF SOMETHING SHOULD BE IN THIS BOX");
  }



  // ================================== TEST STUBS ===
  class TestTranscriptDecoratorMapStrategy implements MapStrategy {
    public void createWorld(GameImpl game) {
      for (int i = 0; i < GameConstants.WORLDSIZE; i++) {
        for (int j = 0; j < GameConstants.WORLDSIZE; j++) {
          game.createTile(new Position(i, j), new TileImpl(GameConstants.PLAINS));
        }
      }
      game.createTile(new Position(1, 0), new TileImpl(GameConstants.OCEANS));
      game.createTile(new Position(0, 1), new TileImpl(GameConstants.HILLS));
      game.createTile(new Position(1, 1), new TileImpl(GameConstants.MOUNTAINS));
      game.createTile(new Position(2, 2), new TileImpl(GameConstants.MOUNTAINS));
      game.createTile(new Position(3, 1), new TileImpl(GameConstants.FOREST));
      game.createCity(new Position(1, 1), new CityImpl(Player.RED));
      game.createCity(new Position(4, 1), new CityImpl(Player.BLUE));
      game.createUnit(new Position(2, 0), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
      game.createUnit(new Position(3, 2), new UnitImpl(Player.BLUE, GameConstants.LEGION, 1));
      game.createUnit(new Position(3, 1), new UnitImpl(Player.BLUE, GameConstants.SETTLER, 1));
      game.createUnit(new Position(4, 3), new UnitImpl(Player.RED, GameConstants.SETTLER, 1));
      game.createUnit(new Position(1, 2), new UnitImpl(Player.RED, GameConstants.B52, 2));
    }
  }
}
