package hotciv.standard;

import hotciv.factory.AlphaCivFactory;
import hotciv.factory.ThetaCivFactory;
import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestTranscriptDecorator {
  private GameImpl game;
  private TranscriptDecorator td;

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    MapStrategy mapStrategy = new TestThetaCivMapStrategy();
    game = new GameImpl(new ThetaCivFactory());
    td = new TranscriptDecorator(game);
    mapStrategy.createWorld(game);
  }

  // Take Small Steps. Ensure, that something is transcripted from getWinner().
  @Test
  public void winnerIsTranscripted(){
    td.getWinner();
  }

  // Ensure, that when player RED wins, his name is transcribed.
  @Test
  public void transcriptRedAsWinnerInYear3000BC() {
    assertThat(td.getAge(), is(-4000));
    for(int i=0; i < 20; i++){
      td.endOfTurn();
    }
    assertThat(td.getWinner(), is(Player.RED));
  }

  // Ensure, that the game age can be transcribed.
  @Test
  public void ageIsTranscripted(){
    td.getAge();
  }

  // Ensure, that unit actions are transcribed.
  @Test
  public void unitActionIsTranscribed(){
    Position redB52Position = new Position(1, 2);
    td.performUnitActionAt(redB52Position);
  }

}
