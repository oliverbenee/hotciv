package hotciv.standard;
import hotciv.framework.*;

public class UnitImpl implements Unit {
  private Player owner;
  private String type;

  public UnitImpl(Player owner, String unitType){
    owner = owner;
    type = unitType;
  }

  public String getTypeString() {
    return type;
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
