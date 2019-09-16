package hotciv.standard;

import hotciv.framework.*;
import org.junit.*;
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
  private GameImpl game;

  /** Fixture for alphaciv testing. */
  @Before
  public void setUp() {
    game = new GameImpl(new AlphaCivImpl());
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
    assertNull(game.getWinner());
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

  // Ensure, that cities produce 6 production per round
  @Test
  public void citiesShouldProduce6ProductionPerRound(){
    int firstTreasury = game.getCityAt(new Position(1,1)).getTreasury();
    assertEquals(0, firstTreasury);
    game.endOfTurn();
    game.endOfTurn();
    assertEquals(firstTreasury + 6, game.getCityAt(new Position(1,1)).getTreasury());
  }

  // FRS p. 458 states, that "the layout of terrain is fixed in every game, all tiles are of type "plain" except for tile(1,0) = Ocean, tile (0,1) = Hills, tile (2,2) = Mountains."
  @Test
  public void ensureThatThereIsOceanAt1Point0(){
    assertEquals(game.getTileAt(new Position(1,0)).getTypeString(), "ocean");
  }

  // FRS p. 458 states, that "the layout of terrain is fixed in every game, all tiles are of type "plain" except for tile(1,0) = Ocean, tile (0,1) = Hills, tile (2,2) = Mountains."
  @Test
  public void ensureThatThereIsAHillsTileAt0Point1(){
    assertEquals(game.getTileAt(new Position(0,1)).getTypeString(), "hills");
  }

  // FRS p. 458 states, that "the layout of terrain is fixed in every game, all tiles are of type "plain" except for tile(1,0) = Ocean, tile (0,1) = Hills, tile (2,2) = Mountains."
  @Test
  public void ensureThatThereIsAMountainsTileAt2Point2(){
    assertEquals(game.getTileAt(new Position(2,2)).getTypeString(), "mountain");
  }

  //FRS p. 458 states, that "Red has initially one archer at (2,0)"
  @Test
  public void redHasAnArcherAt2Point0(){
    Position currentpos = new Position(2,0);
    assertEquals(game.getUnitAt(currentpos).getTypeString(), GameConstants.ARCHER);
    assertEquals(game.getUnitAt(currentpos).getOwner(), Player.RED);
  }

  //FRS p. 458 states, that "Blue has one legion at (3,2)"
  @Test
  public void blueHasALegionAt3Point2(){
    Position currentpos = new Position(3,2);
    assertEquals(game.getUnitAt(currentpos).getTypeString(), GameConstants.LEGION);
    assertEquals(game.getUnitAt(currentpos).getOwner(), Player.BLUE);
  }

  //FRS p. 458 states, that "Player red has a settler at (4,3)"
  @Test
  public void redHasASettlerAt4Point3(){
    Position currentpos = new Position(4,3);
    assertEquals(game.getUnitAt(currentpos).getTypeString(), GameConstants.SETTLER);
    assertEquals(game.getUnitAt(currentpos).getOwner(), Player.RED);
  }

  //FRS p. 462 states, that "Red cannot move Blue’s units"
  @Test
  public void blueCannotMoveRedsUnits(){
    assertEquals(game.getPlayerInTurn(), Player.RED);
    Position unitPosition = new Position(2,0);
    Position targetPosition = new Position(3,0);
    Unit unit = game.getUnitAt(unitPosition);
    assertEquals(unit.getOwner(), Player.RED);
    game.endOfTurn();
    assertEquals(game.getPlayerInTurn(), Player.BLUE);
    game.moveUnit(unitPosition, targetPosition);
    assert(!game.moveUnit(unitPosition,targetPosition));
    assertThat(game.getUnitAt(unitPosition), is(unit));
  }

  //FRS p. 458 states, that "only one unit is allowed on a tile at any time."
  @Test
  public void onlyOneUnitAtATileAtATime(){
    Position redpos = new Position(2,0);
    assertEquals(game.getUnitAt(redpos).getOwner(), Player.RED);
    Position bluepos = new Position(3,2);
    assertEquals(game.getUnitAt(bluepos).getOwner(), Player.BLUE);
    game.moveUnit(redpos, bluepos);
    assertEquals(game.getUnitAt(bluepos).getOwner(), Player.RED);
    assertNotEquals(game.getUnitAt(bluepos).getOwner(), Player.BLUE);
  }

  //FRS p. 458 states, that "The attacking unit always wins no matter what the defensive or attacking strengths are of either units."
  @Test
  public void attackingUnitAlwaysWins(){
    Position attackerPosition = new Position(2,0);
    Position defenderPosition = new Position(3,0);
    assertEquals(game.getPlayerInTurn(), Player.RED);
    Unit redUnit = game.getUnitAt(attackerPosition);
    Unit blueUnit = game.getUnitAt(defenderPosition);
    assertEquals(game.getUnitAt(defenderPosition), blueUnit);
    game.moveUnit(attackerPosition, defenderPosition);
    assertEquals(game.getUnitAt(defenderPosition), redUnit);
  }

  //FRS p. 458 states, that "Specifically, the settler’s action does nothing."
  @Test
  public void settlersActionDoesNothing(){
    Position currentpos = new Position(4,3);
    assertEquals(game.getUnitAt(currentpos).getTypeString(), GameConstants.SETTLER);
    assertEquals(game.getUnitAt(currentpos).getOwner(), Player.RED);
  }

  // Ensure, that units cannot move over mountains.
  @Test
  public void unitsCannotMoveOverMountains(){
    Position blueUnitPosition = new Position(3,2);
    Position mountainPosition = new Position(2,2);
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    Unit blueUnit = game.getUnitAt(blueUnitPosition);
    assertThat(game.getUnitAt(blueUnitPosition), is(blueUnit));
    game.moveUnit(blueUnitPosition, mountainPosition);
    assertThat(game.getUnitAt(blueUnitPosition), is(blueUnit));
  }

  // Ensure, that units cannot move over oceans.
  @Test
  public void unitsCannotMoveOverOceans(){
    Position blueUnitPosition = new Position(3,2);
    Position oceanPosition = new Position(1,0);
    game.endOfTurn();
    assertThat(game.getPlayerInTurn(), is(Player.BLUE));
    Unit blueUnit = game.getUnitAt(blueUnitPosition);
    assertThat(game.getUnitAt(blueUnitPosition), is(blueUnit));
    game.moveUnit(blueUnitPosition, oceanPosition);
    assertThat(game.getUnitAt(blueUnitPosition), is(blueUnit));
  }

  //FRS p. 458 states, that "When a city has accumulated enough production it produces the unit selected for production"
  @Test
  public void produceUnitWhenProductionIsAcquired(){
    Position redCityPosition = new Position(1,1);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.LEGION));
    assertThat(game.getCityAt(redCityPosition).getTreasury(), is(0));
    assertNull(game.getUnitAt(redCityPosition));
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getCityAt(redCityPosition).getTreasury(), is(12));
    assertNull(game.getUnitAt(redCityPosition));
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getUnitAt(redCityPosition).getTypeString(), is(GameConstants.LEGION));
  }

  //FRS p. 458 states, that "the unit’s cost is deducted from the city’s treasury of production."
  @Test
  public void deduceUnitCostFromCitysTreasury(){
    Position redCityPosition = new Position(1,1);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.LEGION));
    assertThat(game.getCityAt(redCityPosition).getTreasury(), is(0));
    assertNull(game.getUnitAt(redCityPosition));
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getCityAt(redCityPosition).getTreasury(), is(3));
  }

  // FRS p. 458 states, that "The player may select to produce either archers, legions, or settlers."
  @Test
  public void playerMayChangeProductionToArcher(){
    Position redCityPosition = new Position(1,1);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.LEGION));
    game.changeProductionInCityAt(redCityPosition, GameConstants.ARCHER);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.ARCHER));
  }

  // FRS p. 458 states, that "The player may select to produce either archers, legions, or settlers."
  @Test
  public void playerMayChangeProductionToSettler(){
    Position redCityPosition = new Position(1,1);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.LEGION));
    game.changeProductionInCityAt(redCityPosition, GameConstants.SETTLER);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.SETTLER));
  }

  // Extra test to ensure, that the spawned unit has changed.
  @Test
  public void changedUnitIsSpawned(){
    Position redCityPosition = new Position(1,1);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.LEGION));
    game.changeProductionInCityAt(redCityPosition, GameConstants.ARCHER);
    assertThat(game.getCityAt(redCityPosition).getProduction(), is(GameConstants.ARCHER));
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    assertThat(game.getUnitAt(redCityPosition).getTypeString(), is(GameConstants.ARCHER));
    assertThat(game.getUnitAt(new Position(0,0)).getTypeString(), is(GameConstants.ARCHER));
  }

  // Ensure, that the unit is placed on top of the city when produced, and there are no other units on top.
  @Test
  public void placeUnitOnTopOfCityIfNoOtherUnitExists(){
    Position redCityPosition = new Position(1,1);
    assertNull(game.getUnitAt(redCityPosition));
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.produceUnit(redCityPosition);
    UnitImpl firstUnit = game.getUnitAt(redCityPosition);
    assertThat(firstUnit.getTypeString(), is(GameConstants.LEGION));
  }

  // Ensure, that the unit is placed north of the city when produced, if there is another unit on top of the city itself.
  @Test
  public void placeUnitToTheNorthIfThereIsAUnitOnTheCity(){
    Position redCityPosition = new Position(1,1);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.produceUnit(redCityPosition);
    UnitImpl firstUnit = game.getUnitAt(redCityPosition);
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.endOfTurn();
    game.produceUnit(redCityPosition);
    UnitImpl secondUnit = game.getUnitAt(new Position(1,2));
    assertThat(secondUnit.getTypeString(), is(GameConstants.LEGION));
  }
}