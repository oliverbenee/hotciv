package hotciv.standard;

import hotciv.framework.Player;
import hotciv.framework.Unit;
import hotciv.framework.Position;

public class UnitImpl implements Unit {
  private Player owner;

  public UnitImpl(Player owner){
    this.owner = owner;
  }

  public String getTypeString() {
    return "archer";
  }

  public Player getOwner(){
    return owner;
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
