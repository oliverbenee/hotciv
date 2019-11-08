package hotciv.standard;

import hotciv.framework.*;

import java.util.*;

/** Test stub for game for visual testing of
 * minidraw based graphics.

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

public class DeltaCivMapStrategy implements MapStrategy {
  // A simple implementation to draw the map of DeltaCiv
  private Map<Position,Tile> world;
  public Tile getTileAt( Position p ) { return world.get(p); }

  /** Define the world as the DeltaCiv layout */
  public void createWorld(GameImpl game) {
    // Basically we use a 'data driven' approach - code the
    // layout in a simple semi-visual representation, and
    // convert it to the actual Game representation.
    String[] layout =
            new String[] {
                    "...ooMooooo.....",
                    "..ohhoooofffoo..",
                    ".oooooMooo...oo.",
                    ".ooMMMoooo..oooo",
                    "...ofooohhoooo..",
                    ".ofoofooooohhoo.",
                    "...ooo..........",
                    ".ooooo.ooohooM..",
                    ".ooooo.oohooof..",
                    "offfoooo.offoooo",
                    "oooooooo...ooooo",
                    ".ooMMMoooo......",
                    "..ooooooffoooo..",
                    "....ooooooooo...",
                    "..ooohhoo.......",
                    ".....ooooooooo..",
            };
    // Conversion...
    Map<Position,Tile> theWorld = new HashMap<>();
    String line;
    for ( int r = 0; r < GameConstants.WORLDSIZE; r++ ) {
      line = layout[r];
      for ( int c = 0; c < GameConstants.WORLDSIZE; c++ ) {
        char tileChar = line.charAt(c);
        String type = "error";
        if ( tileChar == '.' ) { type = GameConstants.OCEANS; }
        if ( tileChar == 'o' ) { type = GameConstants.PLAINS; }
        if ( tileChar == 'M' ) { type = GameConstants.MOUNTAINS; }
        if ( tileChar == 'f' ) { type = GameConstants.FOREST; }
        if ( tileChar == 'h' ) { type = GameConstants.HILLS; }
        Position p = new Position(r,c);
        game.createTile(p, new TileImpl(type));
      }
    }
    game.createCity(new Position(8,12), new CityImpl(Player.RED));
    game.createCity(new Position(4,5), new CityImpl(Player.BLUE));
    game.createUnit(new Position(4,4), new UnitImpl(Player.BLUE, GameConstants.LEGION,1));
    game.createUnit(new Position(3,8), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createUnit(new Position(5,5), new UnitImpl(Player.RED, GameConstants.SETTLER, 1));
  }
}