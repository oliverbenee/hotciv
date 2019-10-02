package hotciv.standard;

import hotciv.framework.AttackStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.utility.Utility;

public class EpsilonCivDynamicAttackStrategy implements AttackStrategy{

  public int calculateDefensiveStrength(GameImpl game, Position unitPosition){
    int defenderDefensiveStrength = 0;
    int unitDefensiveStrength = game.getUnitAt(unitPosition).getDefensiveStrength();
    defenderDefensiveStrength += unitDefensiveStrength;

    boolean DefenderIsOnAlliedCity = game.getCityAt(unitPosition) != null;
    if(DefenderIsOnAlliedCity) defenderDefensiveStrength *= 3;

    int defenderTerrainFactor = 2; //Utility.getTerrainFactor(game, unitPosition);
    defenderDefensiveStrength *= defenderTerrainFactor;
    int defenderFriendlySupport = Utility.getFriendlySupport(game, unitPosition, game.getUnitAt(unitPosition).getOwner());
    defenderDefensiveStrength += defenderFriendlySupport;
    return defenderDefensiveStrength;
  }

  private int calculateAttackingStrength(GameImpl game, Position friendlyPosition){
    int attackerStrength = 0;
    int unitAttackingStrength = game.getUnitAt(friendlyPosition).getAttackingStrength();
    attackerStrength += unitAttackingStrength;
    boolean attackerIsOnAlliedCity = game.getCityAt(friendlyPosition) != null;
    if(attackerIsOnAlliedCity) attackerStrength *=3;
    int attackerTerrainFactor = Utility.getTerrainFactor(game, friendlyPosition);
    attackerStrength *= attackerTerrainFactor;
    int attackerFriendlySupport = Utility.getFriendlySupport(game, friendlyPosition, game.getUnitAt(friendlyPosition).getOwner());
    attackerStrength += attackerFriendlySupport;
    return attackerStrength;
  }

  @Override
  public boolean attackerWins(GameImpl game, Position friendlyPosition, Position enemyPosition) {

    int defenderDefensiveStrength = calculateDefensiveStrength(game, enemyPosition);
    int attackerAttackingStrength = calculateAttackingStrength(game, friendlyPosition);

    return attackerAttackingStrength > defenderDefensiveStrength;
  }
}
