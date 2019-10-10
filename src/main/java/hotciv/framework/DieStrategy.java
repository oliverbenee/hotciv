package hotciv.framework;

public interface DieStrategy {
  /**
   * Return the bonus value to be added to the attacking strength or defending strength of the unit.
   * @return int value between 1 and 6. Value is decided depending on the strategy used.
   */
  int rollDie();
}
