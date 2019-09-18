package hotciv.standard;
import hotciv.framework.WinnerStrategy;

import hotciv.framework.Game;
import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

public class AlphaCivWinnerStrategy implements WinnerStrategy {
  @Override
  public Player getWinner(GameImpl game) {
    if (game.getAge() >= -3000) {
      return Player.RED;
    } else { return null; }
  }
}
