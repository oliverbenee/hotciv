package hotciv.standard;

import hotciv.framework.DieStrategy;

public class DieStub implements DieStrategy {
  private int eyes;

  public DieStub(int eyes){ this.eyes = eyes;}
  public int rollDie(){
    return eyes;
  }
}
