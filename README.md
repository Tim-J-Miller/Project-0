# Game Design Document

This document will outline the proposed design for a command line interface game which reads from/writes to a .JSON file and a connected database.

The core design is based on the [Balloon Analogue Risk Task](http://www.impulsivity.org/measurement/BART), wherein participants are presented with a button and a balloon. Each press of the button inflates the balloon and increases the participants' reward until either they step away and recieve their reward, or pop the balloon and recieve nothing.

This game will be more complex than the risk task, with some rpg elements sprinkled in to add some atmosphere and to add the necissary data points for the project requirements.

The objective of the game is to accumulate as much gold as possible. To accomplish this task, players must explore the dungeon. However, the player does not have the means to explore the dungeon on their own and must hire followers to fight on their behalf.

## Program Start
The program looks for a .JSON file which holds the Player IDs and Character names of every player that has played on the machine.

### If the expected .JSON with relevent data is found
The player is asked to enter their ID.
##### if character entered does not correspond to the entered ID
The player may try to import the character from the DB. If the character is not in the DB the player will be asked to create a new character who will be added to the .JSON and the DB.
##### if ID is not found on in the .JSON (new player/ no .JSON found/ .JSON empty)
The player may then try to import a save from the DB using their Player ID. If there is no entry for the Player ID is found in the DB then the player will be asked to make a new unique ID which will be added to the .JSON and the DB.

## Upon starting the game 
Players are directed to the tavern. In the tavern players are faced with their first choice via a CLI loop.

```
[playerName], you have [followerCount] followers and [goldTotal] gold. What would you like to do?
    Hire another follower?
        How much?
        Never mind.
    Leave.
```

Each follower will have stats based on how much gold was spent to aquire them. At minimum, the followers will have an HP stat which will determine the maximum amount of damage they can sustain. Time allowing, there may be other stats that determine their effectiveness in combat which may include any of the usual rpg stats (strength, accuracy, defence, evasiveness, luck). Followers will be added to a table in the DB.

## After aquiring some followers

Players may enter the dungeon wherein the players are faced with their second choice via another CLI loop.

```
[playerName], you have entered dungeon level [dungeonLevel].
You have found [goldAmount] so far.
You have [followerCount] followers remaining.[followers.hpcritical] are in critical condition. 
What would you like to do?
    Check follower HP
        return List(followers.hp)
    Proceed.
        [dungeonLevel++]
        [goldAmound+= random amount * dungeonLevel]
        [random follower.hp -= random amount * dungeonLevel]
    Leave.
        [if followers.count > 0] goldTotal += goldAmount
```

If at any point a follower's HP reaches 0, they are deleted from the DB.

## DB Model
#### Player Table
| Player ID | Character Name | Gold Total|
| ------ | ------ |------ |
| CharVar(30) | CharVar(30) | Int |
| Primary Key | | |
#### Follower Table
| Player ID | Follower Name | HP|
| ------ | ------ |------ |
| CharVar(30) | CharVar(30) | Int |
| Foreign key, Composite Key | Composite Key | |

 
