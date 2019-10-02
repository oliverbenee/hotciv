package hotciv.factory;

import hotciv.framework.*;
import hotciv.standard.*;

public class BetaCivFactory implements HotCivFactory {
  @Override
  public AgeStrategy createAgeStrategy() {
    return new BetaCivAgeStrategy();
  }

  @Override
  public ActionStrategy createActionStrategy() {
    return new AlphaCivActionStrategy();
  }

  @Override
  public AttackStrategy createAttackStrategy() {
    return new AlphaCivAttackStrategy();
  }

  @Override
  public MapStrategy createMapStrategy() {
    return new AlphaCivMapStrategy();
  }

  @Override
  public WinnerStrategy createWinnerStrategy() {
    return new BetaCivWinnerStrategy();
  }
}
