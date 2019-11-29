package hotciv.broker;

import hotciv.framework.City;
import hotciv.framework.NameService;
import hotciv.framework.Tile;
import hotciv.framework.Unit;

import java.util.HashMap;
import java.util.Map;

public class NameServiceImpl implements NameService {
  private Map<String, City> cityNames = new HashMap<>();
  private Map<String, Tile> tileNames = new HashMap<>();
  private Map<String, Unit> unitNames = new HashMap<>();

  @Override
  public void putCity(String objectId, City city){
    cityNames.put(objectId, city);
  }

  @Override
  public City getCity(String objectId){
    return cityNames.get(objectId);
  }

  @Override
  public void putTile(String objectId, Tile tile) {
    tileNames.put(objectId, tile);
  }

  @Override
  public Tile getTile(String objectId) {
    return tileNames.get(objectId);
  }

  @Override
  public void putUnit(String objectId, Unit unit) {
    unitNames.put(objectId, unit);
  }

  @Override
  public Unit getUnit(String objectId) {
    return unitNames.get(objectId);
  }
}
