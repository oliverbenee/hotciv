package hotciv.standard;
import hotciv.standard.GameImpl;
import hotciv.framework.*;

import java.util.Map;

public class BetaCivImpl implements GameType {
  private GameImpl game;

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

  public Player gameWinner(GameImpl game) {
    int cityCounter = 0;
    // Run through hashmap of cities and ensure, that playerInTurn is the owner.
    for(Map.Entry<Position, CityImpl> entry : game.getCities().entrySet()){
      if(game.getPlayerInTurn() == entry.getValue().getOwner()){
        cityCounter += 1;
      }
    }
    if(cityCounter == 2){
      return game.getPlayerInTurn();
    } else {
      return null;
    }
  }
}