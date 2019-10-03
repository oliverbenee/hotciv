package hotciv.standard;

import hotciv.framework.DieStrategy;

public class FixedDieStrategy implements DieStrategy {
  private int eyes;

  public FixedDieStrategy(int eyes){ this.eyes = eyes;}
  public int rollDie(){
    return eyes;
  }
}
