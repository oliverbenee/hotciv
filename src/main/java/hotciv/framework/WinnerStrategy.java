package hotciv.framework;
import hotciv.standard.*;

public interface WinnerStrategy {
  Player getWinner(GameImpl game);
}