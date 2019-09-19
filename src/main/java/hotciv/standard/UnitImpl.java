package hotciv.standard;
import hotciv.framework.*;

public class UnitImpl implements Unit {
  private Player owner;
  private String type;
  private int defensiveStrength;

  public UnitImpl(Player owner, String unitType, int defensiveStrength){
    this.owner = owner;
    this.type = unitType;
    this.defensiveStrength = defensiveStrength;
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
    return defensiveStrength;
  }

  public void setDefensiveStrength(int newDefensiveStrength) { defensiveStrength = newDefensiveStrength; }

  public int getAttackingStrength(){
    return 1;
  }
}
