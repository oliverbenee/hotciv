package hotciv.standard;

import hotciv.framework.GameObserver;
import hotciv.framework.Player;
import hotciv.framework.Position;

import java.util.ArrayList;
import java.util.List;

public class GameObserverImpl implements GameObserver {
  private ArrayList<String> observers;

  public GameObserverImpl(){
    observers = new ArrayList<>();
  }

  @Override
  public void worldChangedAt(Position pos) {
    observers.add("World changed at: " + pos);
  }

  @Override
  public void turnEnds(Player nextPlayer, int age) {
    observers.add("A player has ended their turn. Player " + nextPlayer + " is now in turn. World age is currently " + age);
  }

  @Override
  public void tileFocusChangedAt(Position position) {}

  public String getList(int index) {
    return observers.get(index);
  }
}
