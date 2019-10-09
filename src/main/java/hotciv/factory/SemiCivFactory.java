package hotciv.factory;

import hotciv.framework.*;
import hotciv.standard.*;

public class SemiCivFactory implements HotCivFactory {
  @Override
  public AgeStrategy createAgeStrategy() {
    return new BetaCivAgeStrategy();
  }

  @Override
  public ActionStrategy createActionStrategy() {
    return new GammaCivActionStrategy();
  }

  @Override
  public AttackStrategy createAttackStrategy() {
    return new EpsilonCivAttackStrategy(new RandomDieStrategy());
  }

  @Override
  public MapStrategy createMapStrategy() {
    return new DeltaCivMapStrategy();
  }

  @Override
  public WinnerStrategy createWinnerStrategy() {
    return new EpsilonCivWinnerStrategy();
  }
}
