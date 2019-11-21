package hotciv.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.City;
import hotciv.framework.OperationConstants;
import hotciv.framework.Player;

import javax.servlet.http.HttpServletResponse;

public class HotCivCityInvoker implements Invoker {
  private City city;
  private final Gson gson;

  public HotCivCityInvoker(City servant){
    city = servant;
    gson = new Gson();
  }
  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payload) {
    ReplyObject reply = null;

    //Demarshall parameters into JsonArray
    JsonParser parser = new JsonParser();
    JsonArray array =
            parser.parse(payload).getAsJsonArray();

    City city = lookupCity(objectId);
    // Get city owner
    if(operationName.equals(OperationConstants.CITY_GET_OWNER)){
      Player uid = city.getOwner();
      reply = new ReplyObject(HttpServletResponse.SC_CREATED,
              gson.toJson(uid));
    }
    // Get city size
    if(operationName.equals(OperationConstants.CITY_GET_SIZE)){
      int size = city.getSize();
      // Used to convert int to String.
      StringBuilder sb = new StringBuilder();
      sb.append("");
      sb.append(size);
      String sizeString = sb.toString();
      reply = new ReplyObject(HttpServletResponse.SC_CREATED, sizeString);
    }
    // Get city treasury
    if(operationName.equals(OperationConstants.CITY_GET_TREASURY)){
      int treasury = city.getTreasury();
      // Used to convert int to String.
      StringBuilder sb = new StringBuilder();
      sb.append("");
      sb.append(treasury);
      String treasuryString = sb.toString();
      reply = new ReplyObject(HttpServletResponse.SC_CREATED, treasuryString);
    }
    // Get city production
    if(operationName.equals(OperationConstants.CITY_GET_PRODUCTION)){
      String uid = city.getProduction();
      reply = new ReplyObject(HttpServletResponse.SC_CREATED,
              gson.toJson(uid));
    }
    // Get city workforce focus
    if(operationName.equals(OperationConstants.CITY_GET_WORKFORCE_FOCUS)){
      String uid = city.getWorkforceFocus();
      reply = new ReplyObject(HttpServletResponse.SC_CREATED,
              gson.toJson(uid));
    }
    /* Change city production TODO: DETTE ER PASS BY VALUE - FØRST NÆSTE UGE
    if(operationName.equals(OperationConstants.CITY_CHANGE_PRODUCTION)){
      String uid = "";
    }
    */

    return reply;
  }

  private City lookupCity(String objectId){
    City city = new StubCity2(Player.GREEN);
    return city;
  }
}
