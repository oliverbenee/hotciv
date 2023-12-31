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

  //Not used in AlphaCiv
  @Override
  public void incrementAttacksWonByPlayer(Player p) {}

  //Not used in AlphaCiv
  @Override
  public int getAttacksWonByPlayer(Player p) {
    return 0;
  }
}
