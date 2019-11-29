package hotciv.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.NameService;
import hotciv.framework.OperationConstants;
import hotciv.framework.Player;
import hotciv.framework.Unit;

import javax.servlet.http.HttpServletResponse;

public class HotCivUnitInvoker implements Invoker {
  public static final Gson gson = new Gson();
  private NameService nameService;

  public HotCivUnitInvoker(NameService nameService) {
    this.nameService = nameService;
  }

  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payload) {
    ReplyObject reply = null;

    //Demarshall parameters into JsonArray
    JsonParser parser = new JsonParser();
    JsonArray array =
            parser.parse(payload).getAsJsonArray();

    // Get unit typestring
    if(operationName.equals(OperationConstants.UNIT_GET_TYPESTRING)){
      Unit unit = nameService.getUnit(objectId);
      if(unit != null) {
        String uid = unit.getTypeString();
        System.out.println("Type: " + uid);
        reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(uid));
      } else {
        reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("no unit found"));
      }
    }
    // Get unit owner
    if(operationName.equals(OperationConstants.UNIT_GET_OWNER)){
      Unit unit = nameService.getUnit(objectId);
      if(unit != null) {
        Player uid = unit.getOwner();
        System.out.println("Player: " + uid);
        reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(uid));
      } else {
        reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("no unit found"));
      }
    }
    // Get unit movecount
    boolean isGetMoveCount = operationName.equals(OperationConstants.UNIT_GET_MOVECOUNT);
    if(isGetMoveCount){
      Unit unit = nameService.getUnit(objectId);
      if (unit != null) {
        int moveCount = unit.getMoveCount();
        // Used to convert int to String.
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(moveCount);
        String moveString = sb.toString();
        reply = new ReplyObject(HttpServletResponse.SC_CREATED, moveString);
      } else {
        reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("no unit found"));
      }
    }
    // Get unit attack strength
    boolean isGetAttackingStrength = operationName.equals(OperationConstants.UNIT_GET_OFFENSE);
    if(isGetAttackingStrength){
      Unit unit = nameService.getUnit(objectId);
      if (unit != null) {
        int attackingStrength = unit.getAttackingStrength();
        // Used to convert int to String.
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(attackingStrength);
        String attackingString = sb.toString();
        reply = new ReplyObject(HttpServletResponse.SC_CREATED, attackingString);
      } else {
        reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("no unit found"));
      }
    }
    // Get unit defensive strength
    boolean isGetDefensiveStrength = operationName.equals(OperationConstants.UNIT_GET_DEFENSE);
    if(isGetDefensiveStrength){
      Unit unit = nameService.getUnit(objectId);
      if(unit != null){
        int defensiveStrength = unit.getDefensiveStrength();
        // Used to convert int to String
        StringBuilder sb = new StringBuilder();
        sb.append("");
        sb.append(defensiveStrength);
        String defensiveString = sb.toString();
        reply = new ReplyObject(HttpServletResponse.SC_CREATED,defensiveString);
      } else {
        reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("no unit found"));
      }
    }
    return reply;
  }
}
