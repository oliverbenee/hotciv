package hotciv.framework;

import hotciv.standard.GameImpl;

public interface HotCivFactory {
  /**
   * Specify which AgeStrategy to use with the game setup.
   * AgeStrategies contain the algorithms for incrementation of game age.
   * @return AgeStrategy object with the intended world aging algorithm to be used.
   */
  AgeStrategy createAgeStrategy();

  /**
   * Specify which ActionStrategy to use with the game setup.
   * ActionStrategies contain the algorithms for handling unit actions in the game.
   * @return ActionStrategy object with the intended unit action algorithm to be used.
   */
  ActionStrategy createActionStrategy();

  /**
   * Specify which AttackStrategy to use with the game setup.
   * AttackStrategies contain the algorithms for handling which unit wins an attack or defense.
   * @return AttackStrategy object with the intended attacking algorithm to be used.
   */
  AttackStrategy createAttackStrategy();

  /**
   * Specify which MapStrategy to use with the game setup.
   * MapStrategies contain the algorithms for which kind of world layout to use.
   * @return MapStrategy object with the intended map algorithm to be used.
   */
  MapStrategy createMapStrategy();

  /**
   * Specify which WinnerStrategy to use with the game setup.
   * WinnerStrategies contain the algorithms for which kind of victory conditions to use.
   * @return WinnerStrategy object with the intended victory condition algorithms to use.
   */
  WinnerStrategy createWinnerStrategy();
}
