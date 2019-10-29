package hotciv.standard;

import hotciv.framework.*;

/**
 * A GameImpl decorator, that logs game actions.
 *
 */
public class TranscriptDecorator implements Game {
  private Game game;
  private Game decoratee;

  public TranscriptDecorator(Game gamelogged){
    game = gamelogged;
  }

  public boolean toggleTranscriptDecorator(){
    if(game == decoratee){
      //enable the logging by decorating the component
      decoratee = game; // but remember the component
      game = new TranscriptDecorator(game);
      return true;
    } else {
      //remove logging by making gameImpl point to the component object once again.
      game = decoratee;
      return  false;
    }
  }

  @Override
  public Tile getTileAt(Position p) {
    return game.getTileAt(p);
  }

  @Override
  public Unit getUnitAt(Position p) {
    return game.getUnitAt(p);
  }

  @Override
  public City getCityAt(Position p) {
    return game.getCityAt(p);
  }

  @Override
  public Player getPlayerInTurn() {
    return game.getPlayerInTurn();
  }

  @Override
  public Player getWinner() {
    Player winner = game.getWinner();
    System.out.println("Player " + winner + " won!");
    return winner;
  }

  @Override
  public int getAge() {
    int age = game.getAge();
    System.out.println("Age is now: " + age);
    return age;
  }

  @Override
  public boolean moveUnit(Position from, Position to) {
    Unit unit = game.getUnitAt(from);
    boolean moved = game.moveUnit(from, to);
    if(moved){
      System.out.println(unit.getOwner() + " moves " + unit.getTypeString() + " from " + from + " to " + to + ".");
    } else {
      System.out.println(unit.getOwner() + " moves " + unit.getTypeString() + " from " + from + " to " + to + ". But the move failed.");
    }
    return false;
  }

  @Override
  public void endOfTurn() {
    System.out.println(game.getPlayerInTurn() + " ends their turn.");
    game.endOfTurn();
  }

  @Override
  public void changeWorkForceFocusInCityAt(Position p, String balance) {
    System.out.println(game.getPlayerInTurn() + " changes workforce focus in city at " + p + " to " + balance);
    game.changeWorkForceFocusInCityAt(p, balance);
  }

  @Override
  public void changeProductionInCityAt(Position p, String unitType) {
    System.out.println(game.getPlayerInTurn() + " changes production in city at " + p + " to " + unitType);
    game.changeProductionInCityAt(p, unitType);
  }

  @Override
  public void performUnitActionAt(Position p) {
    String unitType = getUnitAt(p).getTypeString();
    String action = "does nothing";
    boolean isSettler = unitType.equals(GameConstants.SETTLER);
    if (isSettler) action = "founds city at " + p;
    boolean isArcher = unitType.equals(GameConstants.ARCHER);
    if (isArcher) action = "fortifies archer at " + p;
    boolean isBomber = unitType.equals(GameConstants.B52);
    if (isBomber) action = "bombs tile at " + p;
    game.performUnitActionAt(p);
    System.out.println(game.getPlayerInTurn() + "'s " + getUnitAt(p).getTypeString() + " " + action);
  }
}
