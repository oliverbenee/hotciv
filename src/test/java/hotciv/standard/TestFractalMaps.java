package hotciv.standard;

import hotciv.factory.SemiCivFactory;
import hotciv.factory.TestEpsilonCivFactory;
import hotciv.factory.TestSemiCivFactory;
import hotciv.framework.*;
import org.junit.*;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class TestFractalMaps {
  private GameImpl game;
  private MapStrategy mapStrategy;

  /**
   * Fixture for FractalMaps integration testing.
   */
  @Before
  public void setUp() {
    mapStrategy = new FractalMapAdapter();
    game = new GameImpl(new TestSemiCivFactory());
    mapStrategy.createWorld(game);
  }

  // This test has a 1/25 chance of failing due to the fact, that we literally are testing to ensure randomness.
  @Test
  public void shouldGeneratePlainsLessThan25TimesVeryOften(){
    int timesPlains = 0;
    Position testPosition = new Position(1,1);
    for (int i = 0; i < 25; i++) {
      mapStrategy.createWorld(game);
      String tileType = game.getTileAt(testPosition).getTypeString();
      boolean isPlains = tileType.equals(GameConstants.PLAINS);
      if (isPlains) timesPlains += 1;
    }
    assertTrue(timesPlains < 25);
  }

  // This test has a 1/25 chance of failing due to the fact, that we literally are testing to ensure randomness.
  @Test
  public void shouldGenerateOceansLessThan25TimesVeryOften(){
    int timesOceans = 0;
    Position testPosition = new Position(1,1);
    for (int i = 0; i < 25; i++) {
      mapStrategy.createWorld(game);
      String tileType = game.getTileAt(testPosition).getTypeString();
      boolean isPlains = tileType.equals(GameConstants.OCEANS);
      if (isPlains) timesOceans += 1;
    }
    assertTrue(timesOceans < 25);
  }

  // This test has a 1/25 chance of failing due to the fact, that we literally are testing to ensure randomness.
  @Test
  public void shouldGenerateForestLessThan25TimesVeryOften(){
    int timesForest = 0;
    Position testPosition = new Position(1,1);
    for (int i = 0; i < 25; i++) {
      mapStrategy.createWorld(game);
      String tileType = game.getTileAt(testPosition).getTypeString();
      boolean isPlains = tileType.equals(GameConstants.FOREST);
      if (isPlains) timesForest += 1;
    }
    assertTrue(timesForest < 25);
  }

  // This test has a 1/25 chance of failing due to the fact, that we literally are testing to ensure randomness.
  @Test
  public void shouldGenerateMountainsLessThan25TimesVeryOften(){
    int timesMountains = 0;
    Position testPosition = new Position(1,1);
    for (int i = 0; i < 25; i++) {
      mapStrategy.createWorld(game);
      String tileType = game.getTileAt(testPosition).getTypeString();
      boolean isPlains = tileType.equals(GameConstants.MOUNTAINS);
      if (isPlains) timesMountains += 1;
    }
    assertTrue(timesMountains < 25);
  }

  // This test has a 1/25 chance of failing due to the fact, that we literally are testing to ensure randomness.
  @Test
  public void shouldGenerateHillsLessThan25TimesVeryOften(){
    int timesHills = 0;
    Position testPosition = new Position(1,1);
    for (int i = 0; i < 25; i++) {
      mapStrategy.createWorld(game);
      String tileType = game.getTileAt(testPosition).getTypeString();
      boolean isPlains = tileType.equals(GameConstants.HILLS);
      if (isPlains) timesHills += 1;
    }
    assertTrue(timesHills < 25);
  }
}