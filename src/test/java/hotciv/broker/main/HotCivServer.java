package hotciv.broker.main;

import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketServerRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.*;
import hotciv.factory.SemiCivFactory;
import hotciv.framework.Game;
import hotciv.framework.NameService;
import hotciv.standard.GameImpl;

/**
 * Socket class copied from the TeleMed system.
 * @author Henrik Bærbak Christensen, baerbak.com
 */
public class HotCivServer {

  private static Thread daemon;

  public static void main(String[] args) throws Exception {
    new HotCivServer(); // No error handling!
  }

  public HotCivServer() throws Exception {
    int port = 25565;

    Game gameServant = new GameImpl(new SemiCivFactory());
    Invoker invoker = new HotCivGameRootInvoker(gameServant);

    // Configure a socket based server request handler
    SocketServerRequestHandler ssrh =
            new SocketServerRequestHandler();
    ssrh.setPortAndInvoker(port, invoker);

    // Alert users of how terminating the server works.
    System.out.println("=== HotCiv Socket based Server Request Handler (port:"
            + port + ") ===");
    System.out.println(" Use ctrl-c to terminate!");
    ssrh.start();

  }
}