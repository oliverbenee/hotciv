package hotciv.standard;

import hotciv.framework.AttackStrategy;
import hotciv.framework.Position;

public class AlphaCivAttackStrategy implements AttackStrategy {
  @Override
  public boolean attackerWins(GameImpl game, Position friendlyPosition, Position enemyPosition) {
    return true;
  }

  @Override
  public int calculateDefensiveStrength(GameImpl game, Position to){
    return 0;
  }

  @Override
  public int calculateAttackingStrength(GameImpl gamge, Position from) {
    return 1;
  }
}
