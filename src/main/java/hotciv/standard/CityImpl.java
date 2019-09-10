package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;
import java.security.acl.Owner;

public class CityImpl implements City {
  private Player owner;
  private int treasury;

  public CityImpl(Player owner){
    this.owner = owner;
    treasury = 0;
  }

  @Override
  public Player getOwner() {return owner; }

  @Override
  public int getSize() {return 1; }

  @Override
  public int getTreasury() {return treasury; }

  @Override
  public String getProduction() {return "";}

  @Override
  public String getWorkforceFocus() {return "";}

  public void addToTreasury() {
    treasury += 6;
  }
}

