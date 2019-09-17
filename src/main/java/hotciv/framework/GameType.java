package hotciv.framework;
import hotciv.standard.GameImpl;

public interface GameType {
  int ageStrategy(int age);
  Player gameWinner(GameImpl game);
}
