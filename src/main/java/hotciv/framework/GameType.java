package hotciv.framework;

public interface GameType {

  int ageStrategy(int age);

  Player gameWinner(Game game);
}
