package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameType;
import hotciv.framework.Player;

public class AlphaCivImpl implements GameType {

  public int ageStrategy(int age) {
    return age + 100;
  }

  public Player gameWinner(Game game) {
    if (game.getAge() >= -3000) {
      return Player.RED;
    } else { return null; }
  }
}