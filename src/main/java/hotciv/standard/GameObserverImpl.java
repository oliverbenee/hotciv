package hotciv.standard;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.ArrayList;

public class GameObserverImpl implements GameObserver {
  private ArrayList<String> observerList;

  public GameObserverImpl(){
    observerList = new ArrayList<>();
  }

  @Override
  public void worldChangedAt(Position pos) {
    observerList.add("World is changed at " + pos);
  }

  @Override
  public void turnEnds(Player nextPlayer, int age) { observerList.add("The player in turn is " + nextPlayer + " and the age is " + age); }

  @Override
  public void tileFocusChangedAt(Position position) {
    observerList.add("Tile focus is changed at " + position);
  }

  public String getList(int index){
    return observerList.get(index);
  }
}
