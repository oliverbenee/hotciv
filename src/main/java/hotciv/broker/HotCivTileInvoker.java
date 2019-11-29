package hotciv.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.GameConstants;
import hotciv.framework.NameService;
import hotciv.framework.OperationConstants;
import hotciv.framework.Tile;
import hotciv.stub.StubTile;

import javax.servlet.http.HttpServletResponse;

public class HotCivTileInvoker implements Invoker {
  private final Gson gson;
  private NameService nameService;

  public HotCivTileInvoker(NameService nameService) {
    gson = new Gson();
    this.nameService = nameService;
  }

  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payload) {
    ReplyObject reply = null;

    // Demarshall parameters into a JsonArray
    JsonParser parser = new JsonParser();
    JsonArray array =
            parser.parse(payload).getAsJsonArray();

    try {
      // Get Type String
      boolean isGetTypeString = operationName.equals(OperationConstants.TILE_GET_TYPESTRING);
      if(isGetTypeString) {
        Tile tile = nameService.getTile(objectId);
        String uid = tile.getTypeString();
        reply = new ReplyObject(HttpServletResponse.SC_OK,
                gson.toJson(uid));
      }
    } catch (Exception e) {}
    return reply;
  }
}
