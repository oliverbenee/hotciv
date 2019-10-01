package hotciv.standard;
import hotciv.framework.*;

public class UnitImpl implements Unit {
  private Player owner;
  private String type;
  private int moveCount;
  private int defensiveStrength;
  private int attackingStrength;
  private boolean fortify;

  public UnitImpl(Player owner, String unitType, int moveCount, int defensiveStrength, int attackingStrength){
    this.owner = owner;
    this.type = unitType;
    this.moveCount = moveCount;
    this.defensiveStrength = defensiveStrength;
    this.attackingStrength = attackingStrength;
  }

  public String getTypeString() {
    return type;
  }

  public Player getOwner(){
    return owner;
  }

  public int getMoveCount(){
    if(fortify) {
      return 0;
    } else {
      return moveCount;
    }
  }

  void resetMoveCount() { moveCount = 1; }

  void decreaseMoveCount() { moveCount--; }

  public int getDefensiveStrength(){
    return defensiveStrength;
  }

  public void setDefensiveStrength(int newDefensiveStrength) { defensiveStrength = newDefensiveStrength; }

  public int getAttackingStrength(){
    return 1;
  }
}
