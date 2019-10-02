package hotciv.framework;

import hotciv.standard.GameImpl;

public interface AttackStrategy {
  boolean attackerWins(GameImpl game, Position friendlyPosition, Position enemyPosition);

  int calculateDefensiveStrength(GameImpl game, Position unitPosition);
}
