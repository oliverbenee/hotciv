package hotciv.framework;
import hotciv.standard.*;

public interface ActionStrategy {
  /**
   * Performs the unit action for the specified unit type.
   * @param p the position of the unit whose action is to be performed.
   * @param game the instance of the game being played.
   */
  void performUnitActionAt(Position p, GameImpl game);
}
