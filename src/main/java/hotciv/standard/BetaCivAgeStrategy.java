package hotciv.standard;
import hotciv.framework.*;

import hotciv.framework.WinnerStrategy;

public class BetaCivAgeStrategy implements AgeStrategy {
  public int updateAge(int age) {
    if (age < -100) {
      return age + 100;
    } else if (age == -100) {
      return age + 99;
    } else if (age == -1) {
      return age + 2;
    } else if (age == 1){
      return age + 49;
    } else if (age >= 50 && age < 1750){
      return age + 50;
    } else if (age >= 1750 && age < 1900) {
      return age + 25;
    } else if (age >= 1900 && age < 1970) {
      return age + 5;
    }else {
      return age + 1;
    }
  }
}
