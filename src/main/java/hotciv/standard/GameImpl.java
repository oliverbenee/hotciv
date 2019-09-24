package hotciv.standard;
import java.util.*;
import hotciv.framework.*;

/** Skeleton implementation of HotCiv.

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

public class GameImpl implements Game {
  private WinnerStrategy winnerStrategy;
  private AgeStrategy ageStrategy;
  private ActionStrategy actionStrategy;
  private MapStrategy mapStrategy;
  private Player playerInTurn;
  private int currentYear;
  private HashMap<Position, TileImpl> world = new HashMap();
  private HashMap<Position, CityImpl> cities = new HashMap();
  private HashMap<Position, UnitImpl> units = new HashMap();

  public GameImpl(WinnerStrategy winnerStrategy, AgeStrategy ageStrategy, ActionStrategy actionStrategy, MapStrategy mapStrategy){
    this.winnerStrategy = winnerStrategy;
    this.ageStrategy = ageStrategy;
    this.actionStrategy = actionStrategy;
    this.mapStrategy = mapStrategy;
    playerInTurn = Player.RED;
    currentYear = -4000;
  }

  void createTile(Position p, TileImpl type) {world.put(p, type); }

  public void createCity(Position p, CityImpl owner) {cities.put(p, owner); }

  // Note: was Unit, changed to UnitImpl
  void createUnit(Position p, UnitImpl owner) {units.put(p, owner); }

  public Tile getTileAt( Position p ) { return world.get(p); }

  public UnitImpl getUnitAt( Position p ) {
    return units.get(p);
  }

  public CityImpl getCityAt( Position p ) { return cities.get(p); }

  public HashMap<Position, CityImpl> getCities() {return cities; }

  public Player getPlayerInTurn() {
    return playerInTurn;
  }

  public Player getWinner() {
    return winnerStrategy.getWinner(this);
  }

  public int getAge() { return currentYear; }

  public boolean moveUnit( Position from, Position to ) {
    UnitImpl unit = getUnitAt(from);
    // Handle illegal tiles
    if (world.get(to).getTypeString().equals(GameConstants.OCEANS)) {
      return false;
    }
    if (world.get(to).getTypeString().equals(GameConstants.MOUNTAINS)) {
      return false;
    }

    // Handle attempts to move player's own unit on to another of player's own unit.
    if (getUnitAt(to) != null && getUnitAt(to).getOwner().equals(playerInTurn)){
      return false;
    }

    // Handle attempts to move a unit, when the units has no moves left. If this is the case, the unit is not allowed to move.
    if (getUnitAt(from).getMoveCount() == 0){
      return false;
    }

    // Handle which player should move
    if (getPlayerInTurn().equals(Player.RED)) {
        if (unit.getOwner().equals(Player.RED)) {
          units.remove(from);
          createUnit(to, unit);
          getUnitAt(to).decreaseMoveCount();
        }
      }
    if (getPlayerInTurn().equals(Player.BLUE)) {
        if (unit.getOwner().equals(Player.BLUE)) {
          units.remove(from);
          createUnit(to, unit);
          getUnitAt(to).decreaseMoveCount();
        }
      }
    // Handle city conquest
    if (getCityAt(to) != null && !getCityAt(to).getOwner().equals(playerInTurn)) {
      getCityAt(to).setOwner(getPlayerInTurn());
    }

    return false;
  }

  public void endOfTurn() {
    if (playerInTurn == Player.RED) {
      playerInTurn = Player.BLUE;
    } else {
      playerInTurn = Player.RED;
      endOfRound();
    }
  }

  private void endOfRound() {
    // Add 6 production to to treasury
    for(Map.Entry<Position, CityImpl> entry : cities.entrySet()){
      entry.getValue().addToTreasury(6);
      produceUnit(entry.getKey());
    }
    // Reset unit move count.
    for(Position p: units.keySet()){
      getUnitAt(p).resetMoveCount();
    }
    currentYear = ageStrategy.updateAge(currentYear);
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  public void changeProductionInCityAt( Position p, String unitType ) {
    CityImpl city = cities.get(p);
    city.setProduction(unitType);
  }

  public void performUnitActionAt(Position p) {actionStrategy.performUnitActionAt(p, this);}

  public void produceUnit(Position p) {
    CityImpl city = cities.get(p);
    if(city.getTreasury() >= GameConstants.getPriceOfProduction(city.getProduction())){
      city.removeFromTreasury(GameConstants.getPriceOfProduction(city.getProduction()));
      if(units.get(p) == null) {
        units.put(p, new UnitImpl(city.getOwner(), city.getProduction(), GameConstants.getMoveDistance(city.getProduction()), GameConstants.getDefensiveStrength(city.getProduction())));
      }} else {
        for (Position ps : hotciv.utility.Utility.get8neighborhoodOf(p)){
          if(units.get(ps) == null) {
            units.put(ps, new UnitImpl(city.getOwner(), city.getProduction(), GameConstants.getMoveDistance(city.getProduction()), GameConstants.getDefensiveStrength(city.getProduction())));
        }
      }
    }
  }

  public void removeUnit(Position p){units.remove(p);}
}