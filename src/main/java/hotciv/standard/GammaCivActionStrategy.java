package hotciv.standard;
import hotciv.standard.*;
import hotciv.framework.*;

public class GammaCivActionStrategy implements ActionStrategy {
  @Override
  public void performUnitActionAt(Position p, GameImpl game){
    if(game.getUnitAt(p).getTypeString().equals(GameConstants.SETTLER)){
      game.createCity(p, new CityImpl(game.getPlayerInTurn()));
    }
    game.removeUnit(p);
  }
}
