package hotciv.factory;

import hotciv.framework.*;
import hotciv.standard.*;

public class TestTranscriptDecoratorFactory implements HotCivFactory {
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
    return new EpsilonCivAttackStrategy(new FixedDieStrategy());
  }

  @Override
  public MapStrategy createMapStrategy() {
    return new AlphaCivMapStrategy();
  }

  @Override
  public WinnerStrategy createWinnerStrategy() {
    return new EpsilonCivWinnerStrategy();
  }
}
