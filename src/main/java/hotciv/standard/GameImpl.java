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

  private boolean friendlyAtTargetPosition(Position to){
    // Handle attempts to move player's own unit on to another of player's own unit.
    boolean friendlyAtTargetPosition = getUnitAt(to) != null && getUnitAt(to).getOwner().equals(getPlayerInTurn());
    if(friendlyAtTargetPosition) return true;
    return false;
  }

  private boolean unitHasMoves(Position unitPosition){
    // Handle attempts to move a unit, when the units has no moves left. If this is the case, the unit is not allowed to move.
    boolean unitHasMoves = getUnitAt(unitPosition).getMoveCount() != 0;
    if(unitHasMoves) return true;
    return false;
  }

  private boolean legalTile(Position to){
    // Handle illegal tiles
    boolean isOcean = getTileAt(to).getTypeString().equals(GameConstants.OCEANS);
    if(isOcean) return false;
    boolean isMountain = getTileAt(to).getTypeString().equals(GameConstants.MOUNTAINS);
    if(isMountain) return false;
    return true;
  }

  private void conquerCity(Position to){
    // Handle city conquest
    if (getCityAt(to) != null && !getCityAt(to).getOwner().equals(getPlayerInTurn())) {
      getCityAt(to).setOwner(getPlayerInTurn());
    }
  }

  private boolean playerOwnsUnit(Position unitPosition){
    boolean isOwnedByPlayerInTurn = getUnitAt(unitPosition).getOwner().equals(getPlayerInTurn());
    if(isOwnedByPlayerInTurn) return true;
    return false;
  }

  public boolean moveUnit( Position from, Position to ) {
    UnitImpl unit = getUnitAt(from);

    boolean legalTile = legalTile(to);
    if(!legalTile) return false;

    boolean friendlyTile = friendlyAtTargetPosition(to);
    if(friendlyTile) return false;

    boolean hasMoves = unitHasMoves(from);
    if(!hasMoves) return false;

    // Handle which player should move
    boolean playerInTurnOwnsUnit = playerOwnsUnit(from);
    if(!playerInTurnOwnsUnit) return false;

    removeUnit(from);
    createUnit(to, unit);
    getUnitAt(to).decreaseMoveCount();

    conquerCity(to);

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

  private boolean cityHasEnoughTreasury(Position cityPosition){
    CityImpl city = getCityAt(cityPosition);
    boolean cityTreasuryIsEnough = city.getTreasury() >= GameConstants.getPriceOfProduction(city.getProduction());
    if(cityTreasuryIsEnough) return true;
    return false;
  }

  private void findValidPositionForUnit(Position cityPosition) {
    CityImpl city = getCityAt(cityPosition);
    boolean noUnitAtCityPosition = getUnitAt(cityPosition) == (null);
    if (noUnitAtCityPosition) {
      createUnit(cityPosition, new UnitImpl(city.getOwner(), city.getProduction(), GameConstants.getMoveDistance(city.getProduction()), GameConstants.getDefensiveStrength(city.getProduction())));
    } else {
      for (Position surroundingCityPosition : hotciv.utility.Utility.get8neighborhoodOf(cityPosition)) {
        boolean noUnitAtSurroundingPosition = getUnitAt(surroundingCityPosition) == (null);
        if (noUnitAtSurroundingPosition) {
          createUnit(surroundingCityPosition, new UnitImpl(city.getOwner(), city.getProduction(), GameConstants.getMoveDistance(city.getProduction()), GameConstants.getDefensiveStrength(city.getProduction())));
        }
      }
    }
  }

  public void produceUnit(Position cityPosition) {
    CityImpl city = getCityAt(cityPosition);
    boolean cityHasEnoughTreasury = cityHasEnoughTreasury(cityPosition);
    if(cityHasEnoughTreasury) {
      city.removeFromTreasury(GameConstants.getPriceOfProduction(city.getProduction()));
      findValidPositionForUnit(cityPosition);
    }
  }

  public void removeUnit(Position p){units.remove(p);}
}