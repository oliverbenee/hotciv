package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.framework.WinnerStrategy;

import java.util.HashMap;
import java.util.Map;

public class EpsilonCivWinnerStrategy implements WinnerStrategy{
  private HashMap<Player, Integer> attackswon;

  @Override
  public Player getWinner(GameImpl game) {
    for(Map.Entry<Player, Integer> entry : attackswon.entrySet()){
      int value = entry.getValue();
      if(value >= 3){return entry.getKey();}
    }
    return null;
  }

  @Override
  public void incrementAttacksWonByPlayer(Player p){
    if (attackswon.get(p) != null) {
      attackswon.put(p, attackswon.get(p) + 1);
    }
    attackswon.put(p, 1);
  }

  @Override
  public int getAttacksWonByPlayer(Player p){
    return attackswon.get(p);
  }
}