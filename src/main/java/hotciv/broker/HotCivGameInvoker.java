package hotciv.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.*;
import hotciv.standard.UnitImpl;
import javafx.geometry.Pos;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class HotCivGameInvoker implements Invoker {
  private Game game;
  private final Gson gson;
  private NameService nameService;

  public HotCivGameInvoker(Game servant, NameService nameService) {
    game = servant;
    gson = new Gson();
    this.nameService = nameService;
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
       if(uid != null) {
         reply = new ReplyObject(HttpServletResponse.SC_CREATED,
                 gson.toJson(uid));
       } else {
         reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("No winner found."));
       }
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
     // Get tile at position
     boolean isTileAtPosition = operationName.equals(OperationConstants.GET_TILE_AT);
     if(isTileAtPosition) {
       Position tilePosition = gson.fromJson(array.get(0), Position.class);
       Tile tile = game.getTileAt(tilePosition);
       if(tile != null) {
         String id = tile.getId();
         nameService.putTile(id, tile);

         reply = new ReplyObject(HttpServletResponse.SC_CREATED,
                 gson.toJson(id));
       } else {
         reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("No tile found!"));
       }
     }
     // Get city at position
     boolean isCityAtPosition = operationName.equals(OperationConstants.GET_CITY_AT);
     if(isCityAtPosition) {
       Position cityPosition = gson.fromJson(array.get(0), Position.class);
       City city = game.getCityAt(cityPosition);
       if(city != null) {
         String id = city.getId();
         nameService.putCity(id, city);

         reply = new ReplyObject(HttpServletResponse.SC_CREATED,
                 gson.toJson(id));
       } else {
         reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("No city found!"));
       }
     }
     // Get unit at position
     boolean isUnitAtPosition = operationName.equals(OperationConstants.GET_UNIT_AT);
     if(isUnitAtPosition) {
       Position unitPosition = gson.fromJson(array.get(0), Position.class);
       Unit unit = game.getUnitAt(unitPosition);
       if(unit != null) {
         String id = unit.getId();
         nameService.putUnit(id, unit);

         reply = new ReplyObject(HttpServletResponse.SC_CREATED,
                 gson.toJson(id));
       } else {
         reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("No unit found!"));
       }
     }
     // Perform unit action
     boolean isPerformUnitAction = operationName.equals(OperationConstants.UNIT_ACTION);
     if(isPerformUnitAction) {
       Position unitPosition = gson.fromJson(array.get(0), Position.class);
       game.performUnitActionAt(unitPosition);
       String uid = "performed unit action!";
       reply = new ReplyObject(HttpServletResponse.SC_CREATED, gson.toJson(uid));
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
   } catch (Exception e){}
    return reply;
  }
}
