package hotciv.standard;

import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;

import java.util.UUID;

public class CityImpl implements City {
  private Player owner;
  private int treasury;
  private String produced;
  private int size;
  private String id;

  public CityImpl(Player owner){
    this.owner = owner;
    treasury = 0;
    produced = GameConstants.LEGION;
    size = 1;
    this.id = UUID.randomUUID().toString();
  }

  @Override
  public Player getOwner() {return owner; }

  public void setOwner(Player p) {owner = p; }

  @Override
  public int getSize() {return size; }

  @Override
  public int getTreasury() {return treasury; }

  @Override
  public String getProduction() {return produced;}

  public void setProduction(String unitType) {produced = unitType; }

  @Override
  public String getWorkforceFocus() {return "";}

  @Override
  public String getId() {
    return id;
  }

  public void addToTreasury(int amount) {
    treasury += amount;
  }

  public void removeFromTreasury(int amount) {treasury -= amount;}

  public void removeCitizen(){
    size-=1;
  }
}

