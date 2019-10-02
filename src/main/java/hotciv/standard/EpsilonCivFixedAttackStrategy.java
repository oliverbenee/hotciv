package hotciv.standard;

import hotciv.framework.AttackStrategy;
import hotciv.framework.Position;

public class EpsilonCivFixedAttackStrategy implements AttackStrategy {
  @Override
  public boolean attackerWins(Position friendlyPosition, Position enemyPosition) {
    return true;
  }
}
