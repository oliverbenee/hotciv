package hotciv.factory;

import hotciv.framework.*;
import hotciv.standard.*;

public class AlphaCivFactory implements HotCivFactory {
  @Override
  public AgeStrategy createAgeStrategy() {
    return new AlphaCivAgeStrategy();
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
    return new AlphaCivWinnerStrategy();
  }
}
