package hotciv.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HotCivGameInvoker implements Invoker {
  private Game game;
  private final Gson gson;

  public HotCivGameInvoker(Game servant) {
    game = servant;
    gson = new Gson();
  }

  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payloadJSONArray) {
    ReplyObject reply = null;

    // Demarshall parameters into a JsonArray
    JsonParser parser = new JsonParser();
    JsonArray array =
            parser.parse(payloadJSONArray).getAsJsonArray();

    try {
     // Get Winner
     if (operationName.equals(OperationConstants.OPERATION_WINNER)) {
       String uid = game.getWinner().toString();
       reply = new ReplyObject(HttpServletResponse.SC_CREATED,
               gson.toJson(uid));
     }
     // Get player in turn
     if(operationName.equals(OperationConstants.PLAYER_IN_TURN)){
       String uid = game.getPlayerInTurn().toString();
       reply = new ReplyObject(HttpServletResponse.SC_CREATED,
               gson.toJson(uid));
     }
     // Get world age
     if (operationName.equals(OperationConstants.CURRENT_WORLD_AGE)) {
       int age = game.getAge();
       // Used to convert int to String.
       StringBuilder sb = new StringBuilder();
       sb.append(age);
       String ageString = sb.toString();
       reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(ageString));
     }
     // End of turn
     if(operationName.equals(OperationConstants.END_OF_TURN)){
       game.endOfTurn();
       String uid = game.getPlayerInTurn().toString();
       reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(uid));
     }
     // Move Unit
     if(operationName.equals(OperationConstants.MOVE_UNIT)) {
       Position from = gson.fromJson(array.get(0), Position.class);
       Position to = gson.fromJson(array.get(1), Position.class);
       boolean moved = game.moveUnit(from, to);
       reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(moved));
     }
     /* Change production
     TODO: DETTE ER PASS BY VALUE - FØRST NÆSTE UGE
     if(operationName.equals(OperationConstants.CITY_CHANGE_PRODUCTION)) {
       Position cityPosition = new Position(1,1);
       game.changeProductionInCityAt(cityPosition, GameConstants.B52);
       String uid = game.getCityAt(cityPosition).getProduction();
       reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(uid));
     }
     */
     // Perform unit action
   } catch (Exception e){}
    return reply;
  }
}
