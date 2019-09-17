package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/* BetaCiv test class */
public class TestBetaCiv {
  private GameImpl game;

  /** Fixture for betaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl(new BetaCivImpl());
  }

  // Ensure, that game age advances by 100 years when round ends, during the time the years are -4000 to -100
  @Test
  public void between4000BCand100BC100yearsPassPerRound(){
    assertThat(game.getAge(), is(-4000));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(-3900));
    for (int i = 0; i < 76; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(-100));
  }

  //Ensure, that Around birth of Christ the sequence is -100, -1, +1, +50.
  @Test
  public void ensureAgeSequenceWorks(){
    assertThat(game.getAge(), is(-4000));
    for (int i = 0; i < 78; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(-100));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(-1));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(1));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(50));
  }

  //Ensure, that Between 50AD and 1750 50 years pass per round.
  @Test
  public void ensureBetween50ADAnd1750AD50YearsPass(){
    for (int i = 0; i < 84; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(50));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(100));
    for (int i = 0; i < 66; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1750));
  }

  //Ensure, that Between 1750 and 1900 25 years pass per round.
  @Test
  public void ensureBetween1750ADAnd1900AD25YearsPass(){
    for (int i = 0; i < 152; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1750));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(1775));
    for(int i = 0; i < 10; i++){
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1900));
  }

  //Ensure, that Between 1900 and 1970 5 years pass per round.
  @Test
  public void ensureBetween1900ADAnd1970AD5YearsPass(){
    for (int i = 0; i < 164; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1900));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(1905));
    for (int i = 0; i < 26; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1970));
  }

  //Ensure, that After 1970 1 years pass per round.
  @Test
  public void ensureAfter1970AD1YearPass(){
    for (int i = 0; i < 192; i++) {
      game.endOfTurn();
    }
    assertThat(game.getAge(), is(1970));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(1971));
  }

  //If player Red conquers blue's city, he wins.
  @Test
  public void redConquersBlueCityAndWins(){
    assertNull(game.getWinner());
    assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.BLUE));
    game.moveUnit((new Position(4,3)), new Position(4,1));
    assertThat(game.getUnitAt(new Position(4,1)).getOwner(), is(Player.RED));
    assertThat(game.getCityAt(new Position(4,1)).getOwner(), is(Player.RED));
    assertThat(game.getWinner(), is(Player.RED));
  }
}
