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
    def run(id: String, characterName: String, goldTotal: Int):Unit = {
        
        p = Player(id, characterName, goldTotal, followers = new ArrayBuffer[Follower])

        
    /*      functions for handling followers

            //adding followers
            p.followers.addOne(Follower("Frank", 5))
            p.followers.addOne(Follower("Steve",44))
            p.followers.addOne(Follower("Frank",2))

           //print all followers
           p.followers.foreach(x => println(s"${x.name} has ${x.HP} hp"))
    
           //damage all
           p.followers.foreach(x => {x.HP = x.HP - 10})
        
           //damage conditionally
           p.followers.foreach(x => {
               if (x.name == "Steve"){
                   x.HP = x.HP - 10
               }
           })

           //remove dead followers
           p.followers = p.followers.filter(x => x.HP > 0)

           //print all followers
           p.followers.foreach(x => println(s"${x.name} has ${x.HP} hp"))
    */      
           var gameLoop = true
           var townMenuLoop = true
           var tavernMenuLoop = false
           var dungeonMenuLoop = false
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
                            save(p)
                        }
                        case x if (x.equalsIgnoreCase("Exit")) => {
                            townMenuLoop = false
                            tavernMenuLoop = false
                            dungeonMenuLoop = false
                            gameLoop = false
                            save(p)
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
                            save(p)
                        }
                        case x if (x.equalsIgnoreCase("Exit")) => {
                            townMenuLoop = false
                            tavernMenuLoop = false
                            dungeonMenuLoop = false
                            gameLoop = false
                            save(p)
                        }
                        case _ => {
                            println("Failed to parse a command")
                        }
                    }
                }
                while(dungeonMenuLoop){
                    printDungeonMenuOptions()
                    var input = StdIn.readLine()
                    dungeonMenuLoop=false
                }
                save(p)
                gameLoop = false
           }

     }
    def hireFollower(hirePrice: Int) = {
        val rand = new Random
        val listNames = List("Rico", "Tailor", "Ryan", "Henry", "Michael", "Michele", "Sara", "Magnolia", "(⌐■_■)", "ᕦ(ò_óˇ)ᕤ", "🐱‍👤")
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
    def save(player: Player) = {
        //Save state to DB
        println(player)
    }
    def printTownMenuOptions():Unit = {
        List(
            "You find yourself in the town square",
            s"You have ${p.followers.length} followers",
            "What would you like to do?\n",
            "Follower list: Check the status of your followers",
            "Go to tavern: hire some followers who will fight on your behaf",
            "Go to dungeon: lead your followers into the dungeon to find riches",
            "Save: save the game 💾",
            "Exit: exit the game 🙋‍♂"
        ).foreach(println)
    }
    def printTavernMenuOptions():Unit = {
        List(
            "You find yourself in the tavern",
            s"You have ${p.goldTotal} gold and ${p.followers.length} followers",
            "What would you like to do?\n",
            "Hire follower [gold amount]: Offer the specified amount of gold to hire a new follower \n\t (larger amounts will higher stronger followers)",
            "Leave tavern: return to the town square",
            "Save: save the game 💾",
            "Exit: exit the game 🙋‍♂"
        ).foreach(println)
    }
    def printDungeonMenuOptions():Unit ={

    }
}

case class Follower(val name: String, var HP: Int = 10)

case class Player(val id:String = "", val name: String = "", var goldTotal: Int = 0, var followers: ArrayBuffer[Follower] = ArrayBuffer())