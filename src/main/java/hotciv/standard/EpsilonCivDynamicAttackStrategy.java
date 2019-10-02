package hotciv.standard;

import hotciv.framework.AttackStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.utility.Utility;
import hotciv.utility.Utility2;

public class EpsilonCivDynamicAttackStrategy implements AttackStrategy{

  public int calculateDefensiveStrength(GameImpl game, Position to){
    int defenderDefensiveStrength = 0;
    int unitDefensiveStrength = game.getUnitAt(to).getDefensiveStrength();
    defenderDefensiveStrength += unitDefensiveStrength;

    int defenderTerrainFactor = 2; //Utility.getTerrainFactor(game, unitPosition);
    defenderDefensiveStrength *= defenderTerrainFactor;
    int defenderFriendlySupport = Utility2.getFriendlySupport(game, to, game.getUnitAt(to).getOwner());
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
    int attackerFriendlySupport = Utility2.getFriendlySupport(game, friendlyPosition, game.getUnitAt(friendlyPosition).getOwner());
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
