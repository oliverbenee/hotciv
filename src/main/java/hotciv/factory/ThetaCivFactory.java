package hotciv.factory;

import hotciv.framework.*;
import hotciv.standard.*;

public class ThetaCivFactory implements HotCivFactory {
  @Override
  public AgeStrategy createAgeStrategy() {
    return new AlphaCivAgeStrategy();
  }

  @Override
  public ActionStrategy createActionStrategy() {
    return new GammaCivActionStrategy();
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
