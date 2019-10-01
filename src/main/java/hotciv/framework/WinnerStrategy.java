package hotciv.framework;
import hotciv.standard.*;

public interface WinnerStrategy {
  Player getWinner(GameImpl game);

  void incrementAttacksWonByPlayer(Player p);

  int getAttacksWonByPlayer(Player p);
}