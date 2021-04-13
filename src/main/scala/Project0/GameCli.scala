package Project0

import scala.io.StdIn
import scala.collection.mutable.ArrayBuffer

object GameCli{
    var p = Player()
    def run(id: String, characterName: String, goldTotal: Int):Unit = {
        //var followers = new ArrayBuffer[Follower]
        //val p = new Player(characterName, goldTotal, followers = new ArrayBuffer[Follower])
        //println(p.followers.addOne(new Follower(4)))
        //var frank = new Follower(4)
        //frank.HP -= 1
        
        p = Player(id, characterName, goldTotal, followers = new ArrayBuffer[Follower])
        println(p)
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
        
        //delete dead followers
        p.followers = p.followers.filter(x => x.HP > 0)

        //print all followers
        p.followers.foreach(x => println(s"${x.name} has ${x.HP} hp"))
        save(p)
    }
    def save(player: Player):Boolean = {
        //Save state to DB
        println(player)
        false
    }
}

case class Follower(val name: String, var HP: Int = 10)
    
case class Player(val id:String = "", val name: String = "", var goldTotal: Int = 0, var followers: ArrayBuffer[Follower] = ArrayBuffer())