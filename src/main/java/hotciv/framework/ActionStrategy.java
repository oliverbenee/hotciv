package hotciv.framework;
import hotciv.standard.*;

public interface ActionStrategy {
  void performUnitActionAt(Position p, GameImpl game);
}
