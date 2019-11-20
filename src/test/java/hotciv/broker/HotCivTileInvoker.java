package hotciv.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.OperationConstants;
import hotciv.framework.Tile;

import javax.servlet.http.HttpServletResponse;

public class HotCivTileInvoker implements Invoker {
  private Tile tile;
  private final Gson gson;

  public HotCivTileInvoker(Tile servant) {
    tile = servant;
    gson = new Gson();
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
      if (operationName.equals(OperationConstants.TILE_GET_TYPESTRING)) {
        String uid = tile.getTypeString();
        reply = new ReplyObject(HttpServletResponse.SC_CREATED,
                gson.toJson(uid));
      }
    } catch (Exception e) {}
    return reply;
  }
}
