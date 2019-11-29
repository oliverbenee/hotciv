package hotciv.broker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import frds.broker.Invoker;
import frds.broker.ReplyObject;
import hotciv.framework.City;
import hotciv.framework.NameService;
import hotciv.framework.OperationConstants;
import hotciv.framework.Player;

import javax.servlet.http.HttpServletResponse;

public class HotCivCityInvoker implements Invoker {
  private final Gson gson;
  private NameService nameService;

  public HotCivCityInvoker(NameService nameService){
    gson = new Gson();
    this.nameService = nameService;
  }
  @Override
  public ReplyObject handleRequest(String objectId, String operationName, String payload) {
    ReplyObject reply = null;

    //Demarshall parameters into JsonArray
    JsonParser parser = new JsonParser();
    JsonArray array =
            parser.parse(payload).getAsJsonArray();


    // Get city owner
    if(operationName.equals(OperationConstants.CITY_GET_OWNER)){
      City city = nameService.getCity(objectId);
      if(city != null) {
        Player uid = city.getOwner();
        System.out.println("Player: " + uid);
        reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson(uid));
      } else {
        reply = new ReplyObject(HttpServletResponse.SC_OK, gson.toJson("no city found"));
      }
    }
    // Get city size
    boolean isGetSize = operationName.equals(OperationConstants.CITY_GET_SIZE);
    if(isGetSize){
      City city = nameService.getCity(objectId);
      int size = city.getSize();
      // Used to convert int to String.
      StringBuilder sb = new StringBuilder();
      sb.append("");
      sb.append(size);
      String sizeString = sb.toString();
      reply = new ReplyObject(HttpServletResponse.SC_CREATED, sizeString);
    }
    // Get city treasury
    boolean isGetTreasury = operationName.equals(OperationConstants.CITY_GET_TREASURY);
    if(isGetTreasury){
      City city = nameService.getCity(objectId);
      int treasury = city.getTreasury();
      // Used to convert int to String.
      StringBuilder sb = new StringBuilder();
      sb.append("");
      sb.append(treasury);
      String treasuryString = sb.toString();
      reply = new ReplyObject(HttpServletResponse.SC_CREATED, treasuryString);
    }
    // Get city production
    boolean isGetProduction = operationName.equals(OperationConstants.CITY_GET_PRODUCTION);
    if(isGetProduction){
      City city = nameService.getCity(objectId);
      String uid = city.getProduction();
      reply = new ReplyObject(HttpServletResponse.SC_CREATED,
              gson.toJson(uid));
    }
    // Get city workforce focus
    boolean isGetWorkforceFocus = operationName.equals(OperationConstants.CITY_GET_WORKFORCE_FOCUS);
    if(isGetWorkforceFocus){
      City city = nameService.getCity(objectId);
      String uid = city.getWorkforceFocus();
      reply = new ReplyObject(HttpServletResponse.SC_CREATED,
              gson.toJson(uid));
    }
    // Change city production
    if(operationName.equals(OperationConstants.CITY_CHANGE_PRODUCTION)){
      String uid = "";
    }

    return reply;
  }
}
