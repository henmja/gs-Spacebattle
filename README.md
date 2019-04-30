# Spacebattle

Spacebattle is a two-dimensional space shooter. It’s a two-player game. The number of players is limited to two because it might be too chaotic with more than two players considering the fact that the main weapon is ranged (you can either shoot or steer into the opponent in order to damage him), and the map is relatively small.

After start-up, each player first chooses to start on either the left or the right side (just as in Pong that was developed by the creators of the game system). The two triangles are the players, and the brown circles are asteroids. There are six asteroids that move across the map in random directions, and when they exit the map, crash into a player or are shot, they will respawn on a random point at the edge of the map and move in new random direction. 

Both players starts with ten health points. The player health bars are in the upper left and the upper right corner. During the first three seconds, both players are invulnerable to damage (this is to give the players some time to choose which side they want to start at, and to give them a chance to get a look at their surroundings). 

If a player is shot, or crashed into by either another player or an asteroid, the given player’s health bar reduces by one health point unless the given player is wielding a shield. You can obtain a shield by flying into the purple force field that will respawn at a random location every third second.
The grey buttons on the controller is used to steer, and the yellow button on the controller is used to fire bullets.



## Table of Contents

Precompiled

Develop

Compile and package

Screenshots

Launcher

Controller

## Precompiled

Download the game system from https://github.com/s111/gamesystem.

The game packaged together with the controller.
[All Releases](https://github.com/henmja/gs-Spacebattle/releases/tag/Releasev1)

Unzip and the spacebattle folder can now be added to the game systems games directory.

## Develop

Download the game system from https://github.com/s111/gamesystem.

Download the repository:

git clone github.com/henmja/gs-spacebattle
Import the project into your IDE of choice. Choose a new maven project and import the pom.xml file. For your IDE to be able to compile the games, you first need to have maven generate the native libraries needed by the game. You do this by executing the following command:

mvn generate-resources
Then you need to set VM options to -Djava.library.path=target/natives.

You should now be able to launch the game from your IDE. Remember that you need the game system running in the background for it to work.

## Compile and package

To package the game for distribution you must execute the following command:

mvn clean compile assembly:single

Now create this folder structure:

spacebattle/bin
Copy game.json, screenshot.png and the controller folder into the spacebattle folder. Copy the target/natives folder into it too and rename it to lib. Now copy the jar file in the target folder into the spacebattle/bin folder and rename it to spacebattle.jar.

You should now have a folder named spacebattle which looks something like this:

spacebattle/game.json
spacebattle/controller/index.html
spacebattle/controller/controller.js
spacebattle/bin/spacebattle.jar
spacebattle/lib/lwjgl.dll
spacebattle/lib/...
This folder can now be added to the game systems games directory.

## Screenshots

### Launcher
![spacebattle](https://cloud.githubusercontent.com/assets/10501925/15156704/8cee5e30-16e8-11e6-8ec9-75016a80b6a5.png)
### Controller
![image](https://cloud.githubusercontent.com/assets/10501925/14319477/a688f810-fc11-11e5-8cdf-cf03239b919a.png)
