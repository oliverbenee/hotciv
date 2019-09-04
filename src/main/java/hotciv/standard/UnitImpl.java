package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Unit;

public class UnitImpl implements Unit {
  public String getTypeString() {
    return "";
  }

  public Player getOwner(){
    return Player.RED;
  }

  public int getMoveCount(){
    return 0;
  }

  public int getDefensiveStrength(){
    return 0;
  }

  public int getAttackingStrength(){
    return 1;
  }
}
