## AlphaCiv requirements.
The following requiremnets are set for the project - all requirements, that have successfully been implemented has been marked with a [ * ]
### Players. (1)
* [OK] There are exactly two players, Red and Blue.
### World Layout. (6)
* [OK] The world looks exactly like shown in Figure 36.2.
* [OK] That is the layout of terrain is fixed in every game,
* [OK] all tiles are of type “plains” except for
* [OK] tile(1,0) = Ocean,
* [OK] tile (0,1) = Hills,
* [OK] tile (2,2) = Mountains.
### Units. (4)
* [OK] Only one unit is allowed on a tile at any time.
* [OK] Red has initially one archer at (2,0),
* [OK] Blue has one legion at (3,2),
* [OK] and Red a settler at (4,3).
### Attacking. (1)
* [OK] Attacks are resolved like this: The attacking unit always wins no matter what the defensive or attacking strengths are of either units.
### Unit actions. (1)
* [OK] No associated actions are supported by any unit. Specifically, the settler’s action does nothing.
### Cities. (5)
* [OK] The player may select to produce either archers, legions, or settlers.
* [OK] Cities do not grow but stay at population size 1.
* [OK] Cities produce 6 production per round which is a fixed setup.
* [OK] Red has a city at position (1,1)
* [OK] while blue has one at position (4,1).
### Unit Production. (4)
* [OK] When a city has accumulated enough production it produces the unit selected for production,
* [OK] and the unit’s cost is deducted from the city’s treasury of production.
* [OK] The unit is placed on the city tile if no other unit is present,
* [OK] otherwise it is placed on the first non-occupied adjacent tile, starting from the tile just north of the city and moving clockwise.
### Aging. (2)
* [OK] The game starts at age 4000 BC, and
* [OK] Each round advances the game age 100 years.
### Winning. (1)
* [OK] Red wins in year 3000 BC.

25 / 25 functions implemented.
9 / 9 categories implemented.

### Requirements related to HotCiv, but not related to AlphaCiv
* [OK] Units cannot move over ocean tiles
* [OK] Units cannot move over mountain tiles
* [OK] When the player changes the unit to be produced, the correct unit is spawned
* [OK] When a player moves a unit onto an enemy city, the owner is changed. (i.e. if red moves to blues city, it is now reds city.)

## BetaCiv requirements. 
### Winner. (1)
* [OK] Players can conquer cities.
* [OK] The winner is the player that first conquers all cities in the world.
### World aging. (7)
* [OK] World aging. The world ages using the following algorithm:
* [OK] Between 4000BC and 100BC 100 years pass per round. 
* [OK] Around birth of Christ the sequence is -100, -1, +1, +50. 
* [OK] Between 50AD and 1750 50 years pass per round. 
* [OK] Between 1750 and 1900 25 years pass per round. 
* [OK] Between 1900 and 1970 5 years per round. 
* [OK] After 1980 1 year per round.

## GammaCiv requirements
### Settler action. (3)
* [OK] When a settler is told to perform its action (build city), the settler unit itself is removed from the world 
* [OK] and replaced by a city of population size one. 
* [OK] Of course, the owner of the city is the same player as the one who owned the settler.
### Archer action. (Please refer to our mail-discussion from 18-09-2019 :-))
* [OK] When an archer is told to perform its action (fortify), its defensive strength is doubled, 
* [OK] however, it cannot be moved. 
* [OK] If an archer is already fortified, invoking this action removes its fortification.

## DeltaCiv requirements. (Please refer to our mail-discussion from 18-09-2019 :-))
### World Layout
 * [OK] The world’s layout is that shown in Figure 36.1 on page 454. 
 * [OK] Red must have a city at position (8,12) 
 * [OK] and blue a city at (4,5). 
 * [OK] Furthermore, it should be easy for programmers to write their own algorithms for generating a world layout to be 
used in DeltaCiv without any source code changes in the HotCiv production code.

## EpsilonCiv requirements. 
### Winner. 
* [OK] The winner is the first player to win three attacks. 
* [OK] Successful defenses do not count, only successful attacks.
#### Prerequisites for this to work:
* [OK] Each player's attack wins are counted.    
### Attacking. 
* [OK] Attacks are resolved based upon an algorithm that determines the battle outcome based on combined attack strength 
of the attacking unit and combined defense strength of the defending unit. The combined strength is calculated
based upon 
        A) the unit’s own strength, 
        B) support from adjacent friendly units, and
        C) terrain factor.
#### This is tested through the TestUtility2.java file.
* [OK] The combined attack strength, A, is first the attack strength of the unit itself. 
* [OK] To this value, a supporting strength of +1 is added for each adjacent tile that has a friendly unit. 
* [OK] This number is then multiplied by the terrain factor: 
        A) the terrain factor is 2 if the unit is on a tile of type forest or type hill; 
        B) or multiplied by 3 if the unit is in a city.

## ZetaCiv requirements. (Not implemented. Please refer to our mail-discussion from 18-09-2019 :-))
### Winner. 
* [] The winner is the player that first conquers all cities in the world (like BetaCiv). 
However, in case the game lasts more than 20 rounds then the winner is the first player to win three attacks 
(like EpsilonCiv). The counting of attacks won does not start until the 20th round has ended.

## AbstractFactory requirements.
* [] Implemented?

## BUGS TO BE FIXED
* [OK] Empty catch block in moveUnit() method.

## Other
* [] Refactoring of EpsilonCivDynamicAttackStrategy is VERY MUCH NEEDED.