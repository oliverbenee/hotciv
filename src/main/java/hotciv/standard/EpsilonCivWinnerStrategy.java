package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.WinnerStrategy;

import java.util.HashMap;
import java.util.Map;

public class EpsilonCivWinnerStrategy implements WinnerStrategy{
  private HashMap<Player, Integer> attackswon;

  public EpsilonCivWinnerStrategy() {
    HashMap<Player, Integer> attackswon = new HashMap<>();
    this.attackswon = attackswon;
    attackswon.put(Player.BLUE, 0);
    attackswon.put(Player.RED, 0);
  }

  @Override
  public Player getWinner(GameImpl game) {
    for(Map.Entry<Player, Integer> entry : getAttacksWonForPlayers().entrySet()){
      int value = entry.getValue();
      if(value >= 3){return entry.getKey();}
    }
    return null;
  }

  @Override
  public void incrementAttacksWonByPlayer(Player p){
    boolean playerHasWonAttack = attackswon.containsKey(p);
    if(!playerHasWonAttack){
      attackswon.put(p, 1);
    } else {
      attackswon.put(p, attackswon.get(p) + 1);
    }
  }

  public HashMap<Player, Integer> getAttacksWonForPlayers(){
    return attackswon;
  }

  @Override
  public int getAttacksWonByPlayer(Player p){
    return attackswon.get(p);
  }
}