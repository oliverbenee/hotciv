package hotciv.standard;

import hotciv.framework.*;

import org.junit.*;

import static hotciv.framework.GameConstants.PLAINS;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import java.util.*;

/** Skeleton class for AlphaCiv test cases

    Updated Oct 2015 for using Hamcrest matchers

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
public class TestAlphaCiv {
  private Game game;
  private City redCity;

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl();
  }

  // FRS p. 455 states that 'Red is the first player to take a turn'.
  @Test
  public void shouldBeRedAsStartingPlayer() {
    assertThat(game, is(notNullValue()));
    // TODO: reenable the assert below to get started...
    assertThat(game.getPlayerInTurn(), is(Player.RED));
  }

  // FRS p. 462 states that 'Red's city is at (1,1)
  @Test
  public void shouldReturn1Point1AsRedsStartingCity() {
    Position pcurrent = new Position(1,1);
    assertNotNull(game.getCityAt(pcurrent));
    assertThat(game.getCityAt(pcurrent).getOwner(), is(Player.RED));
  }

  // FRS p. 462 states, that 'after Red it is blue that is in turn
  @Test
  public void ensureBlueIsNext() {
    /*
     * First ensure, that test CAN work.
     */
    assertThat(game, is(notNullValue()));
    // TODO: reenable the assert below to get started...
    assertThat(game.getPlayerInTurn(), is(Player.RED));
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
  }

  // FRS p. 462 states, that 'Red wins in year 3000 BC'
  @Test
  public void redShouldWinInYear3000BC() {
    assertThat(game.getAge(), is(-4000));
    // IntelliJ hates for-loops
    for(int i=0; i < 20; i++){
      game.endOfTurn();
    }
    assertThat(game.getWinner(), is(Player.RED));
  }
  // Extra test - ensure, that no winner is found before year 3000 BC
  @Test
  public void noWinnerIsFoundBeforeYear3000BC() {
    assertThat(game.getAge(), is(-4000));
    // IntelliJ hates for-loops
    for(int i=0; i < 10; i++){
      game.endOfTurn();
    }
    assertTrue(game.getWinner() == null);
  }

  // Extra test - ensure, that the game starts in year 4000 BC
  @Test
  public void gameShouldStartAtYear4000BC(){
    assertThat(game.getAge(), is(-4000));
  }

  // Ensure, that population of cities is always 1.
  @Test
  public void populationOfCitiesIsAlways1(){
    assertThat(redCity.getSize(), is(1));
    Position pcurrent = new Position(1,1);
    assertThat(game.getCityAt(pcurrent).getSize(), is(1));
  }

  // Ensure, that cities produce 6 production per round
  @Test
  public void citiesShouldProduce6ProductionPerRound(){
    int firstTreasury = redCity.getTreasury();
    game.endOfTurn();
    assertTrue(redCity.getTreasury() == firstTreasury + 6);
  }

  @Test
  public void ensurePopulatedTiles(){
    for(int i=0; i< GameConstants.WORLDSIZE - 1; i++){
      for(int j=0; j<GameConstants.WORLDSIZE - 1; j++){
        assertThat(game.getTileAt(new Position(i,j)).getTypeString(), equals(gameConstants.PLAINS));
      }
    }
  }
}