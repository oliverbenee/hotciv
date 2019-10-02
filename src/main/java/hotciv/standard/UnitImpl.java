package hotciv.standard;
import hotciv.framework.*;

public class UnitImpl implements Unit {
  private Player owner;
  private String type;
  private int moveCount;
  private boolean fortify;

  public UnitImpl(Player owner, String unitType, int moveCount){
    this.owner = owner;
    this.type = unitType;
    this.moveCount = moveCount;
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

  void setFortify(){
    fortify = !fortify;
  }

  public int getDefensiveStrength(){
    int defensiveStrength = GameConstants.getDefensiveStrength(type);
    if(fortify) defensiveStrength*=2;
    return defensiveStrength;
  }

  public int getAttackingStrength(){
    return 1;
  }
}
