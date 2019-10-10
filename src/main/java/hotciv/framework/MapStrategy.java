package hotciv.framework;
import hotciv.standard.*;

public interface MapStrategy {
  /**
   * Creates the map to be used in the game. The type of map is dependent on the strategy chosen.
   * @param game The game to create the map in.
   */
  void createWorld(GameImpl game);
}
