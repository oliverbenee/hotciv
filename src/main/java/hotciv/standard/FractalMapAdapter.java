package hotciv.standard;

import hotciv.framework.*;

import java.util.*;

public class FractalMapAdapter implements MapStrategy {
  public void createWorld(GameImpl game) {
    // Define the world by creating a new FractalMaps layout
    FractalMap fractalMap = new FractalMap();
    String[] layout = fractalMap.main();

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
  }
}
