package hotciv.standard;
import hotciv.standard.*;
import hotciv.framework.*;

public class GammaCivActionStrategy implements ActionStrategy {
  @Override
  public void performUnitActionAt(Position p, GameImpl game){
    UnitImpl unit = game.getUnitAt(p);
    if(unit.getTypeString().equals(GameConstants.SETTLER)){
      game.createCity(p, new CityImpl(game.getPlayerInTurn()));
      game.removeUnit(p);
    }
    if(unit.getTypeString().equals(GameConstants.ARCHER)) {
      if(unit.getDefensiveStrength() == 3) {
        unit.setDefensiveStrength(6);
      } else {
        unit.setDefensiveStrength(3);
      }
    }
  }
}
