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
  private Player playerInTurn;
  private int currentYear;
  private HashMap<Position, TileImpl> world = new HashMap();
  private HashMap<Position, CityImpl> cities = new HashMap();
  private HashMap<Position, UnitImpl> units = new HashMap();

  public GameImpl(){
    playerInTurn = Player.RED;
    currentYear = -4000;
    for(int i=0; i<GameConstants.WORLDSIZE; i++){
      for(int j=0; j<GameConstants.WORLDSIZE; j++){
        createTile(new Position(i,j), new TileImpl(GameConstants.PLAINS));
      }
    }
    createTile(new Position(1,0), new TileImpl(GameConstants.OCEANS));
    createTile(new Position(0,1), new TileImpl(GameConstants.HILLS));
    createTile(new Position(1,1), new TileImpl(GameConstants.MOUNTAINS));
    createTile(new Position(2,2), new TileImpl(GameConstants.MOUNTAINS));
    createCity(new Position(1,1), new CityImpl(Player.RED));
    createCity(new Position(4,1), new CityImpl(Player.BLUE));
    createUnit(new Position(2,0), new UnitImpl(Player.RED, GameConstants.ARCHER));
    createUnit(new Position(3,2), new UnitImpl(Player.BLUE, GameConstants.LEGION));
    createUnit(new Position(4,3), new UnitImpl(Player.RED, GameConstants.SETTLER));
  }

  private void createTile(Position p, TileImpl type) {world.put(p, type); }

  private void createCity(Position p, CityImpl owner) {cities.put(p, owner); }

  // Note: was Unit, changed to UnitImpl
  private void createUnit(Position p, UnitImpl owner) {units.put(p, owner); }

  public Tile getTileAt( Position p ) { return world.get(p); }

  public UnitImpl getUnitAt( Position p ) {
    return units.get(p);
  }

  public City getCityAt( Position p ) { return cities.get(p); }

  public Player getPlayerInTurn() {
    return playerInTurn;
  }

  public Player getWinner() {
    if (currentYear == -3000) {
      return Player.RED;
    } else { return null; }
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

    // Handle which player should move
    if (getPlayerInTurn().equals(Player.RED)) {
        if (unit.getOwner().equals(Player.RED)) {
          units.remove(from);
          createUnit(to, unit);
        }
      }
    if (getPlayerInTurn().equals(Player.BLUE)) {
        if (unit.getOwner().equals(Player.BLUE)) {
          units.remove(from);
          createUnit(to, unit);
        }
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
    currentYear += 100;
    for(Map.Entry<Position, CityImpl> entry : cities.entrySet()){
      entry.getValue().addToTreasury(6);
      produceUnit(entry.getKey());
    }
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  public void changeProductionInCityAt( Position p, String unitType ) {
    CityImpl city = cities.get(p);
    city.setProduction(unitType);
  }

  public void performUnitActionAt( Position p ) {}

  public void produceUnit(Position p) {
    CityImpl city = cities.get(p);
    if(city.getTreasury() >= GameConstants.getPriceOfProduction(city.getProduction())){
      city.removeFromTreasury(GameConstants.getPriceOfProduction(city.getProduction()));
      if(units.get(p) == null) {
        units.put(p, new UnitImpl(city.getOwner(), city.getProduction()));
      }} else {
        for (Position ps : hotciv.utility.Utility.get8neighborhoodOf(p)){
          if(units.get(ps) == null) {
            units.put(ps, new UnitImpl(city.getOwner(), city.getProduction()));
        }
      }
    }
  }
}