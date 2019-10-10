package hotciv.framework;
import hotciv.standard.*;

public interface WinnerStrategy {
  /**
   * Find the winner of the game.
   * @param game the instance of the game being played.
   * @return Player, whom has won the game in accordance with the specified victory conditions.
   */
  Player getWinner(GameImpl game);

  /**
   * Increment the number of attacks won by the player.
   * @param p The player who won an attack.
   */
  void incrementAttacksWonByPlayer(Player p);

  /**
   * Return the number of attacks won by a player.
   * @param p The player whose number of attacks won is to be returned.
   * @return Int, the amount of attacks the player has won.
   */
  int getAttacksWonByPlayer(Player p);
}