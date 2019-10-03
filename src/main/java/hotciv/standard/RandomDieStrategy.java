package hotciv.standard;
import hotciv.framework.DieStrategy;
import java.util.Random;

public class RandomDieStrategy implements DieStrategy {

  @Override
  public int rollDie() {
    int eyes = (int) (Math.random()*6+1);
    return eyes;
  }
}
