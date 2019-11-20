package hotciv.broker;

import frds.broker.ClientRequestHandler;
import frds.broker.Invoker;
import frds.broker.Requestor;
import frds.broker.marshall.json.StandardJSONRequestor;
import hotciv.framework.City;
import hotciv.framework.GameConstants;
import hotciv.framework.Player;
import hotciv.framework.Tile;
import hotciv.stub.StubTile;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class TestTileBroker {
  private Tile tile;

  /** Fixture for broker testing. */
  @Before
  public void setUp() {
    // Server side implementation.
    Tile servant = new StubTile(GameConstants.FOREST);

    // Server side broker implementations
    Invoker invoker = new HotCivTileInvoker(servant);

    // Create client side broker implementations, using the local
    // method client request handler to avoid any real IPC layer.
    ClientRequestHandler crh =
            new LocalMethodClientRequestHandler(invoker);
    Requestor requestor =
            new StandardJSONRequestor(crh);
    tile = new TileProxy(requestor);
  }

  // Get Tile Type String
  @Test
  public void shouldGetForestAsTypeString(){
    String type = tile.getTypeString();
    assertThat(type, is(GameConstants.FOREST));
  }
}
