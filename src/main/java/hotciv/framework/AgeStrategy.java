package hotciv.framework;

public interface AgeStrategy {
  /**
   * Updates the game age.
   * @param age the current world age.
   * @return the new world age.
   */
  int updateAge(int age);
}
