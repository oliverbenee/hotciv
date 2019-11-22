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
     boolean isWinner = operationName.equals(OperationConstants.OPERATION_WINNER);
     if(isWinner) {
       String uid = game.getWinner().toString();
       reply = new ReplyObject(HttpServletResponse.SC_CREATED,
               gson.toJson(uid));
     }
     // Get player in turn
     boolean isPlayerInTurn = operationName.equals(OperationConstants.PLAYER_IN_TURN);
     if(isPlayerInTurn){
       String uid = game.getPlayerInTurn().toString();
       reply = new ReplyObject(HttpServletResponse.SC_CREATED,
               gson.toJson(uid));
     }
     // Get world age
     boolean isCurrentAge = operationName.equals(OperationConstants.CURRENT_WORLD_AGE);
     if(isCurrentAge) {
       int age = game.getAge();
       // Used to convert int to String.
       StringBuilder sb = new StringBuilder();
       sb.append(age);
       String ageString = sb.toString();
       reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(ageString));
     }
     // End of turn
     boolean isEndOfTurn = operationName.equals(OperationConstants.END_OF_TURN);
     if(isEndOfTurn){
       game.endOfTurn();
       String uid = game.getPlayerInTurn().toString();
       reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(uid));
     }
     // Move Unit
     boolean isMoveUnit = operationName.equals(OperationConstants.MOVE_UNIT);
     if(isMoveUnit) {
       Position from = gson.fromJson(array.get(0), Position.class);
       Position to = gson.fromJson(array.get(1), Position.class);
       boolean moved = game.moveUnit(from, to);
       reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(moved));
     }
     // Change production TODO: DETTE ER PASS BY VALUE - FØRST NÆSTE UGE - BROKER 2 - IMPLEMENTER TEST EFTER FUNGERENDE GETCITYAT.
     boolean isChangeProductionInCity = operationName.equals(OperationConstants.CITY_CHANGE_PRODUCTION);
     if(isChangeProductionInCity) {
       Position cityPosition = gson.fromJson(array.get(0), Position.class);
       System.out.println("Finding city at: " + game.getCityAt(cityPosition));
       String production = gson.fromJson(array.get(1), String.class);
       game.changeProductionInCityAt(cityPosition, production);
       reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(array));
     }
     // Perform unit action
   } catch (Exception e){}
    return reply;
  }
}
