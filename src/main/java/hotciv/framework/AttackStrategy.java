package hotciv.framework;

public interface AttackStrategy {
  boolean attackerWins(Position friendlyPosition, Position enemyPosition);
}
