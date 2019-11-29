package hotciv.broker;

import com.google.gson.Gson;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.Game;
import hotciv.framework.NameService;
import hotciv.framework.OperationConstants;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

public class HotCivGameRootInvoker implements Invoker {
  private final Game servant;
  private final NameService nameService;
  private final Map<String, Invoker> invokerMap;
  private Gson gson;

  public HotCivGameRootInvoker(Game servant){
    this.servant = servant;
    gson = new Gson();
    nameService = new NameServiceImpl();
    // Contains all invokers.
    invokerMap = new HashMap<>();

    Invoker hotcivGameInvoker = new HotCivGameInvoker(servant, nameService);
    invokerMap.put(OperationConstants.GAME_PREFIX, hotcivGameInvoker);

    Invoker hotcivCityInvoker = new HotCivCityInvoker(nameService);
    invokerMap.put(OperationConstants.CITY_PREFIX, hotcivCityInvoker);

    Invoker hotcivTileInvoker = new HotCivTileInvoker(nameService);
    invokerMap.put(OperationConstants.TILE_PREFIX, hotcivTileInvoker);

    Invoker hotcivUnitInvoker = new HotCivUnitInvoker(nameService);
    invokerMap.put(OperationConstants.UNIT_PREFIX, hotcivUnitInvoker);
  }
  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payload) {
    ReplyObject reply = null;

    // Identify the invoker to use
    String type = operationName.substring(0, operationName.indexOf(OperationConstants.SEPARATOR));
    Invoker subInvoker = invokerMap.get(type);

    // And do the upcall
    try {
      reply = subInvoker.handleRequest(objectId, operationName, payload);
    } catch (Exception e) {
      reply =
        new ReplyObject(
          HttpServletResponse.SC_NOT_FOUND,
          e.getMessage());
    }

    return reply;
  }
}

