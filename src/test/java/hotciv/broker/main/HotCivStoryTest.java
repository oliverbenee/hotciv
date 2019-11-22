package hotciv.broker.main;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.*;
import hotciv.framework.Game;
import hotciv.framework.GameConstants;
import hotciv.framework.GameObserver;
import hotciv.framework.Position;

public class HotCivStoryTest {

  public static void main(String[] args) {
    System.out.println("Setting up a server to test on... ");
    new HotCivStoryTest(args[0]);
  }

  public HotCivStoryTest(String hostname){
    System.out.println("=== HotCiv MANUAL TEST Client (Socket) (host:"
    + hostname + ") ===");

    // Set up broker part

    // Server side implementation.
    Game servant = new StubGame3();
    GameObserver nullObserver = new NullObserver();
    servant.addObserver(nullObserver);

    // Create client side broker implementations, using the local
    // method client request handler to avoid any real IPC layer.
    ClientRequestHandler crh =
            new SocketClientRequestHandler(hostname, 25565);
    Requestor requestor =
            new StandardJSONRequestor(crh);

    Game game = new GameProxy(requestor);
    game.addObserver(nullObserver);

    testSimpleMethods(game);
  }

  private static void testSimpleMethods(Game game) {
    System.out.println("=== Testing simple methods ===");
    System.out.println("------------------------------");
    System.out.println("| getPlayerInTurn():         |");
    System.out.println(game.getPlayerInTurn());
    System.out.println("------------------------------");
    System.out.println("| getWinner():               |");
    System.out.println(game.getWinner());
    System.out.println("------------------------------");
    System.out.println("| getAge():                  |");
    System.out.println(game.getAge());
    System.out.println("------------------------------------");
    System.out.println("| endOfTurn() + getPlayerInTurn(): |");
    game.endOfTurn();
    System.out.println(game.getPlayerInTurn());
    System.out.println("------------------------------");
    System.out.println("| moveUnit():                |");
    System.out.println(game.moveUnit(new Position(2, 0), new Position(3, 1)));
    System.out.println("------------------------------");
    System.out.println("| changeProductioninCityAt:  |");
    System.out.println("| " + game.getCityAt(new Position(1,1)));
    System.out.println("| Initial production:        |");
    System.out.println("| " + game.getCityAt(new Position(1,1)).getProduction());
    game.changeProductionInCityAt(new Position(1,1), GameConstants.SETTLER);
    // TODO: CHANGE TO REFLECT BROKER 2.
    System.out.println("| IntJ debugger will show B52|");
    System.out.println("| End prod. should be same:  |");
    System.out.println("| " + game.getCityAt(new Position(1,1)).getProduction());
    System.out.println("------------------------------");
  }
}