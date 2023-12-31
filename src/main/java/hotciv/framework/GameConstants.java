package hotciv.framework;

/** Collection of constants used in HotCiv Game. Note that strings are
 * used instead of enumeration types to keep the set of valid
 * constants open to extensions by future HotCiv variants.  Enums can
 * only be changed by compile time modification.

   This source code is from the book 
     "Flexible, Reliable Software:
       Using Patterns and Agile Development"
     published 2010 by CRC Press.
   Author: 
     Henrik B Christensen 
     Department of Computer Science
     Aarhus University
   
   Please visit http://www.baerbak.com/ for further information.

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at
 
       http://www.apache.org/licenses/LICENSE-2.0
 
   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.

*/
public class GameConstants {
  // The size of the world is set permanently to a 16x16 grid 
  public static final int WORLDSIZE = 16;
  // Valid unit types
  public static final String ARCHER    = "archer";
  public static final String LEGION    = "legion";
  public static final String SETTLER   = "settler";

  // ThetaCiv unit type
  public static final String B52       = "b52";

  // Valid terrain types
  public static final String PLAINS    = "plains";
  public static final String OCEANS    = "ocean";
  public static final String FOREST    = "forest";
  public static final String HILLS     = "hills";
  public static final String MOUNTAINS = "mountain";
  // Valid production balance types
  public static final String productionFocus = "hammer";
  public static final String foodFocus = "apple";

  public static int getPriceOfProduction(String produced){
    if(produced.equals(ARCHER)){return 10;}
    if(produced.equals(LEGION)){return 15;}
    if(produced.equals(SETTLER)){return 30;}
    return 0;
  }

  public static int getDefensiveStrength(String unit){
    if(unit.equals(ARCHER)){return 3;}
    if(unit.equals(SETTLER)){return 2;}
    if(unit.equals(LEGION)){return 3;}
    if(unit.equals(B52)){return 8;}
    return 0;
  }

  public static int getMoveDistance(String unit){
    if(unit.equals(ARCHER)){return 1;}
    if(unit.equals(SETTLER)){return 1;}
    if(unit.equals(LEGION)){return 1;}
    if(unit.equals(B52)){return 2;}
    return 0;
  }

  public static int getAttackingStrength(String unit){
    if(unit.equals(ARCHER)){return 2;}
    if(unit.equals(SETTLER)){return 0;}
    if(unit.equals(LEGION)){return 4;}
    if(unit.equals(B52)){return 1;}
    return 0;
  }

  public static boolean getFlying(String unit){
    if(unit.equals(B52)){return true;}
    return false;
  }
}
