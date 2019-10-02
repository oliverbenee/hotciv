package hotciv.standard;

import hotciv.framework.GameConstants;
import hotciv.framework.MapStrategy;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.Game;

public class AlphaCivMapStrategy implements MapStrategy {
  public void createWorld(GameImpl game){
    for(int i=0; i<GameConstants.WORLDSIZE; i++){
      for(int j=0; j<GameConstants.WORLDSIZE; j++){
        game.createTile(new Position(i,j), new TileImpl(GameConstants.PLAINS));
      }
    }
    game.createTile(new Position(1,0), new TileImpl(GameConstants.OCEANS));
    game.createTile(new Position(0,1), new TileImpl(GameConstants.HILLS));
    game.createTile(new Position(1,1), new TileImpl(GameConstants.MOUNTAINS));
    game.createTile(new Position(2,2), new TileImpl(GameConstants.MOUNTAINS));
    game.createCity(new Position(1,1), new CityImpl(Player.RED));
    game.createCity(new Position(4,1), new CityImpl(Player.BLUE));
    game.createUnit(new Position(2,0), new UnitImpl(Player.RED, GameConstants.ARCHER, 1));
    game.createUnit(new Position(3,2), new UnitImpl(Player.BLUE, GameConstants.LEGION, 1));
    game.createUnit(new Position(4,3), new UnitImpl(Player.RED, GameConstants.SETTLER , 1));
  }
}

