package hotciv.standard;
import hotciv.standard.*;
import hotciv.framework.*;

public class GammaCivActionStrategy implements ActionStrategy {
  @Override
  public void performUnitActionAt(Position p, GameImpl game){
    UnitImpl unit = game.getUnitAt(p);
    // Settler unit action.
    if(unit.getTypeString().equals(GameConstants.SETTLER)){
      game.createCity(p, new CityImpl(game.getPlayerInTurn()));
      game.removeUnit(p);
      //game.notifyWorldChangedAt(p);
    }
    // Archer unit action
    if(unit.getTypeString().equals(GameConstants.ARCHER)) {
      unit.setFortify();
      //game.notifyWorldChangedAt(p);
    }
  }
}
