package hotciv.standard;

import hotciv.framework.ActionStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Position;

public class ThetaCivActionStrategy implements ActionStrategy {
  @Override
  public void performUnitActionAt(Position p, GameImpl game){
    UnitImpl unit = game.getUnitAt(p);

    boolean playerInTurnOwnsUnit = unit.getOwner().equals(game.getPlayerInTurn());
    if(!playerInTurnOwnsUnit) return;

    boolean isSettler = unit.getTypeString().equals(GameConstants.SETTLER);
    if(isSettler) performSettlerAction(p, game);

    boolean isArcher = unit.getTypeString().equals(GameConstants.ARCHER);
    if(isArcher) performArcherAction(unit);

    boolean isB52 = unit.getTypeString().equals(GameConstants.B52);
    if(isB52) performB52Action(p, game);
  }

  // Settler unit action.
  private void performSettlerAction(Position p, GameImpl game){
    game.createCity(p, new CityImpl(game.getPlayerInTurn()));
    game.removeUnit(p);
  }

  // Archer unit action.
  private void performArcherAction(UnitImpl unit){
    unit.setFortify();
  }

  // B52 unit action.
  private void performB52Action(Position p, GameImpl game){
    boolean cityAtB52Position = game.getCityAt(p) != null;
    if(cityAtB52Position) {
      boolean cityHasOnePopulation = game.getCityAt(p).getSize() == 1;
      if(cityHasOnePopulation) {
        game.removeCity(p);
      } else {
        game.removeCitizenFromCity(p);
      }
    }
  }
}