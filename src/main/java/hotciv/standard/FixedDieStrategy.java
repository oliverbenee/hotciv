package hotciv.standard;

import hotciv.framework.DieStrategy;

public class FixedDieStrategy implements DieStrategy {
  private int i = 0;

  @Override
  public int rollDie() {
    if(i==0) {
      i = 1;
      return 6;
    } else {
      i = 0;
      return 0;
    }
  }
}
