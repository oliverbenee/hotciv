package hotciv.broker.main;

import frds.broker.ClientRequestHandler;
import frds.broker.Requestor;
import frds.broker.ipc.socket.SocketClientRequestHandler;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.broker.GameProxy;
import hotciv.framework.Game;
import hotciv.visual.CompositionTool;
import hotciv.visual.HotCivFactory4;
import minidraw.framework.DrawingEditor;
import minidraw.standard.MiniDrawApplication;

public class HotCivClient {
  private Game game;

  public static void main(String[] args){
    new HotCivClient(args[0]);
  }
  public HotCivClient(String hostname){
    System.out.println("=== HotCiv MANUAL TEST Client (Socket) (host:"
            + hostname + ") ===");

    // Create client side broker implementations, using the local
    // method client request handler to avoid any real IPC layer.
    ClientRequestHandler crh =
            new SocketClientRequestHandler(hostname, 25565);
    Requestor requestor =
            new StandardJSONRequestor(crh);

    game = new GameProxy(requestor);

    DrawingEditor editor =
            new MiniDrawApplication( "Paint the HotCiv world map...",
                    new HotCivFactory4(game) );

    editor.open();
    editor.showStatus("This is a HotCiv client connected to the server.");

    editor.setTool(new CompositionTool(editor, game));
  }
}
