package hotciv.framework;

import hotciv.standard.GameImpl;

public interface HotCivFactory {
  public AgeStrategy createAgeStrategy();

  public ActionStrategy createActionStrategy();

  public AttackStrategy createAttackStrategy();

  public MapStrategy createMapStrategy();

  public WinnerStrategy createWinnerStrategy();
}
