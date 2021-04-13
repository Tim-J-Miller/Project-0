package Project0

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer
import scala.util.matching.Regex

object GameCli{
    var p = Player()
    val commandArgPattern: Regex = "(\\w+)\\s*(.*)".r
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
                    townMenuLoop=false
                }
                while(tavernMenuLoop){
                    printTavernMenuOptions()
                    var input = StdIn.readLine()
                    townMenuLoop=false
                }
                while(dungeonMenuLoop){
                    printDungeonMenuOptions()
                    var input = StdIn.readLine()
                    townMenuLoop=false
                }
                save(p)
           }

     }
    def save(player: Player):Boolean = {
        //Save state to DB
        println(player)
        false
    }
    def printTownMenuOptions():Unit = {

    }
    def printTavernMenuOptions():Unit = {

    }
    def printDungeonMenuOptions():Unit ={

    }
}

case class Follower(val name: String, var HP: Int = 10)

case class Player(val id:String = "", val name: String = "", var goldTotal: Int = 0, var followers: ArrayBuffer[Follower] = ArrayBuffer())