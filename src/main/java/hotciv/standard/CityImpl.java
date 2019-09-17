package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.Player;

public class CityImpl implements City {
  private Player owner;
  private int treasury;
  private String produced;

  public CityImpl(Player owner){
    this.owner = owner;
    treasury = 0;
    produced = "legion";
  }

  @Override
  public Player getOwner() {return owner; }

  public void setOwner(Player p) {owner = p; }

  @Override
  public int getSize() {return 1; }

  @Override
  public int getTreasury() {return treasury; }

  @Override
  public String getProduction() {return produced;}

  public void setProduction(String unitType) {produced = unitType; }

  @Override
  public String getWorkforceFocus() {return "";}

  public void addToTreasury(int amount) {
    treasury += amount;
  }

  public void removeFromTreasury(int amount) {treasury -= amount;}
}

