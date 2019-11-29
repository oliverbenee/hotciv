package hotciv.framework;

public class OperationConstants {
  public static final char SEPARATOR = '_';

  // Constants for invoker
  public static final String GAME_PREFIX = "operation";

  //Game methods
  public static final String OPERATION_WINNER = GAME_PREFIX + "_winner";
  public static final String CURRENT_WORLD_AGE = GAME_PREFIX + "_current_world_age";
  public static final String PLAYER_IN_TURN = GAME_PREFIX + "_player_in_turn";
  public static final String END_OF_TURN = GAME_PREFIX + "_end_of_turn";
  public static final String MOVE_UNIT = GAME_PREFIX + "_move_unit";
  public static final String UNIT_ACTION = GAME_PREFIX + "_unit_action";

  //Broker 2 Game methods
  public static final String GET_TILE_AT = GAME_PREFIX + "_get_tile_at";
  public static final String GET_UNIT_AT = GAME_PREFIX + "_get_unit_at";
  public static final String GET_CITY_AT = GAME_PREFIX + "_get_city_at";

  // Constants for city
  public static final String CITY_PREFIX = "city";

  //City methods
  public static final String CITY_GET_OWNER = CITY_PREFIX + "_get_owner";
  public static final String CITY_GET_SIZE = CITY_PREFIX + "_get_size";
  public static final String CITY_GET_TREASURY = CITY_PREFIX + "_get_treasury";
  public static final String CITY_GET_PRODUCTION = CITY_PREFIX + "_get_production";
  public static final String CITY_GET_WORKFORCE_FOCUS = CITY_PREFIX + "_get_workforce_focus";
  public static final String CITY_CHANGE_PRODUCTION = "operation_city_change_production";

  // Constants for tile
  public static final String TILE_PREFIX = "tile";

  //Tile method
  public static final String TILE_GET_TYPESTRING = TILE_PREFIX + "_get_typestring";

  // Constants for unit
  public static final String UNIT_PREFIX = "unit";

  //Unit method
  public static final String UNIT_GET_TYPESTRING = UNIT_PREFIX + "_get_typestring";
  public static final String UNIT_GET_OWNER = UNIT_PREFIX + "_get_owner";
  public static final String UNIT_GET_MOVECOUNT = UNIT_PREFIX + "_get_move_count";
  public static final String UNIT_GET_DEFENSE = UNIT_PREFIX + "_get_defense";
  public static final String UNIT_GET_OFFENSE = UNIT_PREFIX +"_get_offense";
}
