package hotciv.standard;

import hotciv.framework.Game;
import hotciv.framework.GameType;
import hotciv.framework.Player;

public class BetaCivImpl implements GameType {

  public int ageStrategy(int age) {
    if (age < -100) {
      return age + 100;
    } else if (age == -100) {
      return age + 99;
    } else if (age == -1) {
      return age + 2;
    } else if (age == 1){
      return age + 49;
    } else if (age >= 50 && age < 1750){
      return age + 50;
    } else if (age >= 1750 && age < 1900) {
      return age + 25;
    } else if (age >= 1900 && age < 1970) {
      return age + 5;
    }else {
      return age + 1;
    }
  }

  public Player gameWinner(Game game) {

    if (game.getAge() >= -3000) {
      return Player.RED;
    } else { return null; }
  }
}