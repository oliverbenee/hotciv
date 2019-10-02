package hotciv.standard;

import hotciv.framework.AttackStrategy;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Position;
import hotciv.utility.Utility;
import hotciv.utility.Utility2;
import java.util.Random;

public class EpsilonCivDynamicAttackStrategy implements AttackStrategy{
  Random random = new Random();

  public int calculateDefensiveStrength(GameImpl game, Position defenderPosition){
    int defenderDefensiveStrength = 0;
    int unitDefensiveStrength = game.getUnitAt(defenderPosition).getDefensiveStrength();
    defenderDefensiveStrength += unitDefensiveStrength;

    boolean DefenderIsOnAlliedCity = game.getCityAt(defenderPosition) != null;
    if(DefenderIsOnAlliedCity) defenderDefensiveStrength *= 3;

    int defenderTerrainFactor = Utility.getTerrainFactor(game, defenderPosition);
    defenderDefensiveStrength *= defenderTerrainFactor;

    int defenderFriendlySupport = Utility2.getFriendlySupport(game, defenderPosition, game.getUnitAt(defenderPosition).getOwner());
    defenderDefensiveStrength += defenderFriendlySupport;

    return defenderDefensiveStrength;
  }

  public int calculateAttackingStrength(GameImpl game, Position attackerPosition){
    int attackerStrength = 0;
    int unitAttackingStrength = game.getUnitAt(attackerPosition).getAttackingStrength();
    attackerStrength += unitAttackingStrength;

    boolean attackerIsOnAlliedCity = game.getCityAt(attackerPosition) != null && game.getCityAt(attackerPosition).getOwner() == game.getUnitAt(attackerPosition).getOwner();
    if(attackerIsOnAlliedCity) attackerStrength *=3;

    int attackerTerrainFactor = Utility.getTerrainFactor(game, attackerPosition);
    attackerStrength *= attackerTerrainFactor;

    int attackerFriendlySupport = Utility2.getFriendlySupport(game, attackerPosition, game.getUnitAt(attackerPosition).getOwner());
    attackerStrength += attackerFriendlySupport;

    return attackerStrength;
  }

  @Override
  public boolean attackerWins(GameImpl game, Position friendlyPosition, Position enemyPosition) {
    int attackerBonus = random.nextInt(6);
    int defenderBonus = random.nextInt(6);

    int defenderDefensiveStrength = calculateDefensiveStrength(game, enemyPosition) + defenderBonus;
    int attackerAttackingStrength = calculateAttackingStrength(game, friendlyPosition) + attackerBonus;

    if(attackerAttackingStrength > defenderDefensiveStrength)return true;
    else {return false;}
  }
}
