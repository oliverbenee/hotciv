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

### Requirements related to hotciv, but not related to AlphaCiv

* [OK] Units cannot move over ocean tiles
* [OK] Units cannot move over mountain tiles
* [OK] When the player changes the unit to be produced, the correct unit is spawned