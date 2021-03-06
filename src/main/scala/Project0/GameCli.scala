package Project0

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex
import scala.util.Random

object GameCli{
    //********this variable is used throught the game
    var p = Player()
    //********DO NOT TOUCH

    val commandArgPattern: Regex = "(\\w+)\\s+(\\w+)\\s*(.*)".r
    
    def run():Unit = {
        println(s"Welcome to the town ${GameCli.p.characterName}")
            var gameLoop = true
            var townMenuLoop = true
            var tavernMenuLoop = false
            var dungeonMenuLoop = false
            var dungeonFloor = 1
            var dungeonLoot = 0
            while(gameLoop){
                while(townMenuLoop){
                    printTownMenuOptions()
                    var input = StdIn.readLine()
                    input match {
                        case commandArgPattern(cmd1, cmd2, arg) if(cmd1.equalsIgnoreCase("Go") && cmd2.equalsIgnoreCase("to")) =>{
                            arg match {
                                case x if (x.equalsIgnoreCase("tavern")) => {
                                    tavernMenuLoop = true
                                    dungeonMenuLoop = false
                                    townMenuLoop = false
                                }
                                case x if (x.equalsIgnoreCase("dungeon")) => {
                                    dungeonMenuLoop = true
                                    tavernMenuLoop = false
                                    townMenuLoop = false
                                }
                                case _ =>{
                                    println("Failed to parse a location")
                                }
                            }
                        }
                        case commandArgPattern(cmd1, cmd2, arg) if(cmd1.equalsIgnoreCase("Follower") && cmd2.equalsIgnoreCase("list")) => {
                            checkFollowers()
                        }
                        case x if (x.equalsIgnoreCase("Save")) => {
                            save()
                        }
                        case x if (x.equalsIgnoreCase("Delete")) => {
                            delete()
                        }
                        case x if (x.equalsIgnoreCase("Exit")) => {
                            townMenuLoop = false
                            tavernMenuLoop = false
                            dungeonMenuLoop = false
                            gameLoop = false
                            save()
                        }
                        case _ => {
                            println("Failed to parse a command")
                        }
                    }
                }
                while(tavernMenuLoop){
                    printTavernMenuOptions()
                    var input = StdIn.readLine()
                    input match {
                        case commandArgPattern(cmd1, cmd2, arg) if (cmd1.equalsIgnoreCase("Hire") && (cmd2.equalsIgnoreCase("follower"))) => {
                            var x = 0
                            try{
                                x = arg.toInt
                            }
                            catch {
                                case e: NumberFormatException => {x = 0}
                            }
                            hireFollower(x)
                        }
                        case commandArgPattern(cmd1, cmd2, arg) if (cmd1.equalsIgnoreCase("Leave") && (cmd2.equalsIgnoreCase("tavern"))) => {
                            tavernMenuLoop = false
                            townMenuLoop = true
                            dungeonMenuLoop = false
                        }
                        case x if (x.equalsIgnoreCase("Save")) => {
                            save()
                        }
                        case x if (x.equalsIgnoreCase("Delete")) => {
                            townMenuLoop = false
                            tavernMenuLoop = false
                            dungeonMenuLoop = false
                            gameLoop = false
                            delete()
                        }
                        case x if (x.equalsIgnoreCase("Exit")) => {
                            townMenuLoop = false
                            tavernMenuLoop = false
                            dungeonMenuLoop = false
                            gameLoop = false
                            save()
                        }
                        case _ => {
                            println("Failed to parse a command")
                        }
                    }
                }
                while(dungeonMenuLoop){
                    var rand = new Random
                    printDungeonMenuOptions(dungeonFloor, dungeonLoot)
                    var input = StdIn.readLine()
                    input match {
                        case commandArgPattern(cmd1, cmd2, arg) if (cmd1.equalsIgnoreCase("Push") && (cmd2.equalsIgnoreCase("through")))=>{
                            val successfullyAdvanced = pushFurtherIntoDungeon(dungeonFloor)
                            if (successfullyAdvanced) {
                                dungeonFloor = dungeonFloor + 1
                                dungeonLoot = dungeonLoot + dungeonFloor * rand.nextInt(5)
                            }
                            else {
                                dungeonMenuLoop = false
                                tavernMenuLoop = false
                                townMenuLoop = true
                                dungeonFloor = 1
                                dungeonLoot = 0
                            }
                        }
                        case commandArgPattern(cmd1, cmd2, arg) if (cmd1.equalsIgnoreCase("Leave") && (cmd2.equalsIgnoreCase("dungeon")))=>{
                            p.goldTotal = p.goldTotal + dungeonLoot
                            dungeonMenuLoop = false
                            tavernMenuLoop = false
                            townMenuLoop = true
                            dungeonFloor = 1
                            dungeonLoot = 0
                        }
                        case _=> {
                            println("Failed to parse a command")
                        }
                    }
                }
           }

     }
    def pushFurtherIntoDungeon(floor: Int):Boolean = {
        val rand = new Random
        p.followers.foreach(x => {
            if (rand.nextInt(100)>floor){
                x.HP = x.HP - rand.nextInt(10)
            }
        })
        p.followers = p.followers.filter(x => x.HP > 0)
        if (p.followers.length > 0) true else false
    }
    def hireFollower(hirePrice: Int) = {
        val rand = new Random
        val listNames = List("(??????_???)", "???(??_????)???", "????", "??? ??? ???_??? ??????", "????", "????")
        if(p.goldTotal >= hirePrice && hirePrice != 0){
            p.followers.addOne(Follower(listNames(rand.nextInt(listNames.length)), hirePrice * (10 + rand.nextInt((10 - 5) + 1))))
            p.goldTotal = p.goldTotal - hirePrice
        }
        else if (hirePrice == 0) {
            println("No one works for free")
        }
        else {
            println("You don't have that much gold")
        }
    }
    def checkFollowers() = {
        p.followers.foreach(x => println(s"${x.name} has ${x.HP} hp"))
    }
    def save() = {
        if(Dao.updatePlayer()){
            if(Dao.deleteFollowers()){
                if(p.followers.length > 0){
                    if (Dao.createFollowers()){
                        println(s"saved with ${p.followers.length} followers")
                    }
                    else{
                        println("error saving")
                    }
                }
                else{
                    println("saved with 0 followers")
                }
            }
            else{
                println("error saving")
            }
        }
        else{
            println("error saving")
        }
    }
    def delete() = {
        println("Are you sure? Y/N\n")
        var input = StdIn.readLine()
        if (input.equalsIgnoreCase("Y")){
            println("Are you Really sure? Y/N\n")
            input = StdIn.readLine()
            if (input.equalsIgnoreCase("Y")){
                println("Cause there is no going back once you go through with this. Y/N\n")
                input = StdIn.readLine()
                if (input.equalsIgnoreCase("Y")){
                    println("Alright")
                    if(Dao.deletePlayer()){
                        var json = FileUtil.getJson()
                        if (json != None) {
                            val x = json.get.arr.indexOf(ujson.Obj("playerName"->GameCli.p.playerName, "characterName"->GameCli.p.characterName))
                            json.get.arr.remove(x)
                            os.write.over(os.pwd/"players.json",json.get)
                        }
                        println("Deleted your save")
                    }
                    else{
                        println("error deleting save")
                    }
                }
            }
        }
    }
    def printTownMenuOptions():Unit = {
        List(
            "You find yourself in the town square",
            s"You have ${p.goldTotal} gold and ${p.followers.length} followers",
            "What would you like to do?\n",
            "Follower list: Check the status of your followers",
            "Go to tavern: hire some followers who will fight on your behaf",
            "Go to dungeon: lead your followers into the dungeon to find riches",
            "Save: save the game",
            "Delete: delete your save file from the database",
            "Exit: exit the game\n"
        ).foreach(println)
    }
    def printTavernMenuOptions():Unit = {
        List(
            "You find yourself in the tavern",
            s"You have ${p.goldTotal} gold and ${p.followers.length} followers",
            "What would you like to do?\n",
            "Hire follower [gold amount]: Offer the specified amount of gold to hire a new follower \n\t (larger amounts will higher stronger followers)",
            "Leave tavern: return to the town square",
            "Save: save the game",
            "Delete: delete your save file from the database",
            "Exit: exit the game\n"
        ).foreach(println)
    }
    def printDungeonMenuOptions(floor: Int, loot: Int):Unit ={
        List(
            s"You find yourself in floor number $floor of the dungeon and your followers have gathered $loot gold",
            s"${p.followers.length} followers still survive",
            "What would you like to do?\n",
            "Push Through: your followers will clear the way to the next floor\n\t there's more gold to be had but risk loosing followers",
            "Leave dungeon: return to town to add the gathered gold to your own\n"
        ).foreach(println)
    }
}

case class Follower(val name: String, var HP: Int = 10)

case class Player(var playerID: Int = 0, var playerName: String = "", var characterName: String = "", var goldTotal: Int = 0, var followers: ArrayBuffer[Follower] = ArrayBuffer())