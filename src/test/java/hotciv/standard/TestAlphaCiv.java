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
  public void playerRedHasACityAt1Point1(){
    assertEquals(game.getCityAt(new Position(1,1)).getOwner(), Player.RED);
  }

  @Test
  public void blueHasACityAt4Point1(){
    assertEquals(game.getCityAt(new Position(4,1)).getOwner(), Player.BLUE);
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

  // FRS p. 458 states, that 'The game starts at age 4000 BC'
  @Test
  public void gameShouldStartAtYear4000BC(){
    assertThat(game.getAge(), is(-4000));
  }

  // FRS p. 458 states, that 'each round advances the game age 100 years'
  @Test
  public void gameAdvances100YearsEachTurn(){
    assertThat(game.getAge(), is(-4000));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getAge(), is(-3900));
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

  // Ensure, that population of cities is always 1.
  @Test
  public void populationOfCitiesIsAlways1(){
    Position pcurrent = new Position(1,1);
    assertThat(game.getCityAt(pcurrent).getSize(), is(1));
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getCityAt(pcurrent).getSize(), is(1));
  }

  /**
  // Ensure, that cities produce 6 production per round - NOT IMPLEMENTED
  @Test
  public void citiesShouldProduce6ProductionPerRound(){
    int firstTreasury = game.getCityAt(new Position(1,1)).getTreasury();
    game.endOfTurn();
    game.endOfTurn();
    assertEquals(game.getCityAt(new Position(1,1)).getTreasury(), firstTreasury + 6);
  }
  */

  // FRS p. 458 states, that "the layout of terrain is fixed in every game, all tiles are of type “plains” except for tile(1,0) = Ocean, tile (0,1) = Hills, tile (2,2) = Mountains."
  @Test
  public void ensureThatThereIsOceanAt1Point0(){
    assertEquals(game.getTileAt(new Position(1,0)).getTypeString(), "ocean");
  }

  // FRS p. 458 states, that "the layout of terrain is fixed in every game, all tiles are of type “plains” except for tile(1,0) = Ocean, tile (0,1) = Hills, tile (2,2) = Mountains."
  @Test
  public void ensureThatThereIsAHillsTileAt0Point1(){
    assertEquals(game.getTileAt(new Position(0,1)).getTypeString(), "hills");
  }

  // FRS p. 458 states, that "the layout of terrain is fixed in every game, all tiles are of type “plains” except for tile(1,0) = Ocean, tile (0,1) = Hills, tile (2,2) = Mountains."
  @Test
  public void ensureThatThereIsAMountainsTileAt2Point2(){
    assertEquals(game.getTileAt(new Position(2,2)).getTypeString(), "mountain");
  }

  // FRS p. 458 states, that "the layout of terrain is fixed in every game, all tiles are of type “plains” except for tile(1,0) = Ocean, tile (0,1) = Hills, tile (2,2) = Mountains."
  @Test
  public void allOtherTilesArePlains(){
    for (int i = 0; i < GameConstants.WORLDSIZE; i++){
      for (int j = 0; j < GameConstants.WORLDSIZE; j++){
        if(i == 1 && j == 0){
          assertEquals(game.getTileAt(new Position(i,j)).getTypeString(), "ocean");
        } else if(i == 0 && j == 1){
          assertEquals(game.getTileAt(new Position(i,j)).getTypeString(), "hills");
        } else if(i == 1 && j == 1){
          assertEquals(game.getTileAt(new Position(i,j)).getTypeString(), "mountain");
        } else if(i == 2 && j == 2){
          assertEquals(game.getTileAt(new Position(i,j)).getTypeString(), "mountain");
        } else {
          assertEquals(game.getTileAt(new Position(i,j)).getTypeString(), "plains");
        }
      }
    }
  }

  //FRS p. 458 states, that "Red has initially one archer at (2,0)"
  @Test
  public void redHasAnArcherAt2Point0(){
    Position currentpos = new Position(2,0);
    assertTrue(game.getUnitAt(currentpos).getTypeString().equals(GameConstants.ARCHER));
    assertTrue(game.getUnitAt(currentpos).getOwner().equals(Player.RED));
  }

  //FRS p. 462 states, that "Red cannot move Blue’s units"
  @Test
  public void blueCannotMoveRedsUnits(){
    assertEquals(game.getPlayerInTurn(), Player.RED);
    Position pos1 = new Position(2,0);
    Position pos2 = new Position(3,0);
    Unit unit = game.getUnitAt(pos1);
    assertEquals(unit.getOwner(), Player.RED);
    game.endOfTurn();
    assertEquals(game.getPlayerInTurn(), Player.BLUE);
    game.moveUnit(pos1, pos2);
    assert(!game.moveUnit(pos1,pos2));
    assertThat(game.getUnitAt(pos1), is(unit));
  }

  //FRS p. 458 states, that "only one unit is allowed on a tile at any time."
  @Test
  public void onlyOneUnitAtATileAtATime(){
    Position redpos = new Position(2,0);
    assertTrue(game.getUnitAt(redpos).getOwner().equals(Player.RED));
    Position bluepos = new Position(3,2);
    assertTrue(game.getUnitAt(bluepos).getOwner().equals(Player.BLUE));
    game.moveUnit(redpos, bluepos);
    assertTrue(game.getUnitAt(bluepos).getOwner().equals(Player.RED));
    assertFalse(game.getUnitAt(bluepos).getOwner().equals(Player.BLUE));
  }

  //FRS p. 458 states, that "The attacking unit always wins no matter what the defensive or attacking strengths are of either units."
  @Test
  public void attackingUnitAlwaysWins(){
    Position pos1 = new Position(2,0);
    Position pos2 = new Position(3,0);
    assertEquals(game.getPlayerInTurn(), Player.RED);
    Unit redUnit = game.getUnitAt(pos1);
    Unit blueUnit = game.getUnitAt(pos2);
    assertEquals(game.getUnitAt(pos2), blueUnit);
    game.moveUnit(pos1, pos2);
    assertEquals(game.getUnitAt(pos2), redUnit);
  }
}