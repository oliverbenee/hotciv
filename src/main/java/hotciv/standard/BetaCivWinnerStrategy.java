package hotciv.standard;
import hotciv.framework.*;

import hotciv.framework.WinnerStrategy;

import java.util.Map;

public class BetaCivWinnerStrategy implements WinnerStrategy {
  @Override
  public Player getWinner(GameImpl game) {
    int cityCounter = 0;
    // Run through hashmap of cities and ensure, that playerInTurn is the owner.
    for(Map.Entry<Position, CityImpl> entry : game.getCities().entrySet()){
      if(game.getPlayerInTurn() == entry.getValue().getOwner()){
        cityCounter += 1;
      }
    }
    if(cityCounter == game.getCities().size()){
      return game.getPlayerInTurn();
    } else {
      return null;
    }
  }

  //Not used in BetaCiv
  @Override
  public void incrementAttacksWonByPlayer(Player p) {}

  //Not used in BetaCiv
  @Override
  public int getAttacksWonByPlayer(Player p) {
    return 0;
  }
}