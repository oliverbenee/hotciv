package hotciv.standard;

import hotciv.framework.AgeStrategy;

public class AlphaCivAgeStrategy implements AgeStrategy {
  public int updateAge(int age) {
    return age + 100;
  }
}
