package hotciv.framework;

import hotciv.standard.GameImpl;

public interface AttackStrategy {
  /**
   * Specify whether or not the attacker wins.
   * @param game the game object representing the game being played.
   * @param friendlyPosition The position of the attacker.
   * @param enemyPosition The position of the defender.
   * @return a boolean value: Whether or not the attacker wins.
   */
  boolean attackerWins(GameImpl game, Position friendlyPosition, Position enemyPosition);

  /**
   * Calculates the combined defensive strength of a unit.
   * @param game the game object representing the game being played.
   * @param unitPosition the position of the unit whose defensive strength is to be calculated.
   * @return the combined defensive strength of the unit.
   */
  int calculateDefensiveStrength(GameImpl game, Position unitPosition);

  /**
   * Calculates the combined attacking strength of a unit.
   * @param game the game object representing the game being played.
   * @param unitPosition the position of the unit whose attacking strength is to be calculated.
   * @return the combined attacking strength of the unit.
   */
  int calculateAttackingStrength(GameImpl game, Position unitPosition);
}
