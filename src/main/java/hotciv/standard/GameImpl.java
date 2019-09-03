package hotciv.standard;
import java.util.HashMap;
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
  Player playerInTurn;
  int currentYear;
  private HashMap<Position, TileImpl> world = new HashMap();
  private HashMap<Position, CityImpl> cities = new HashMap();
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
    createCity(new Position(1,1), new CityImpl(Player.RED));
    createCity(new Position(4,1), new CityImpl(Player.BLUE));
  }
  public void createTile(Position p, TileImpl type) {world.put(p, type); }

  public void createCity(Position p, CityImpl owner) {cities.put(p, owner); }

  public Tile getTileAt( Position p ) { return world.get(p); }

  public Unit getUnitAt( Position p ) { return null; }

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
    return false;
  }

  public void endOfTurn() {
    if (playerInTurn == Player.RED) {
      playerInTurn = Player.BLUE;
    } else {
      playerInTurn = Player.RED;
      currentYear += 100;
    }
  }

  public void changeWorkForceFocusInCityAt( Position p, String balance ) {}

  public void changeProductionInCityAt( Position p, String unitType ) {}

  public void performUnitActionAt( Position p ) {}
}