package hotciv.standard;
import hotciv.framework.DieStrategy;

public class RandomDieStrategy implements DieStrategy {

  @Override
  public int rollDie() {
    int eyes = (int) (Math.random()*6+1);
    return eyes;
  }
}
