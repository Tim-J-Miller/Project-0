package Project0

import org.postgresql.util.PSQLException
import scala.collection.mutable.ArrayBuffer
import java.sql.DriverManager
import java.sql.Connection

object Dao {
    var conn:Connection = null
    val dbURL = sys.env("DBURL")
    val dbNAME = sys.env("DBNAME")
    val dbPASS = sys.env("DBPASS")
    def run():Unit = {

    }
    def getConnection():Option[Connection] = {
        //manually load driver
        classOf[org.postgresql.Driver].newInstance()
        try {
            //use JDBC's DriverManager to get a connection. JDBC is DB agnostic
            conn = DriverManager.getConnection(dbURL, dbNAME, dbPASS)
            Some(conn)

        }
        catch {
            case e: PSQLException => {
                println(e.getMessage())
                None
            }
        }
    }
    def getAvailablePlayers(offsetNumber: Int):Boolean = {
        val conOpt:Option[Connection] = getConnection()
        if (conOpt == None) {
            false
        }
        else {
            try{
                //use drivermanager to get a connection
                //use the connection to prepare a sql statement
                val stmt = conOpt.get.prepareStatement("SELECT * FROM players LIMIT ? OFFSET ?;")
                stmt.setInt(1, 10)
                stmt.setInt(1, offsetNumber*10)
                stmt.execute()
                //afterexecuting the statement, use it to get a resultset
                val rs = stmt.getResultSet()
                while(rs.next()){
                    println(rs.getInt("player_id") +"\t"+ rs.getString("player_name")+"\t"+ rs.getString("character_name")+"\t"+ rs.getString("gold_total").toInt)
                }
                true
            }
            catch{
                case e: PSQLException => {
                    println(e.getMessage())
                    false
                }
            }
            finally {
                conOpt.get.close()
            }
        }
    }
    def loadPlayer(playerName: String):Boolean = {  //login function
        val conOpt:Option[Connection] = getConnection()
        if (conOpt == None) {
            false
        }
        else {
            try{
                val stmt = conOpt.get.prepareStatement("SELECT * FROM players WHERE player_name = ?;")
                stmt.setString(1, playerName)
                stmt.execute()
                
                val rs = stmt.getResultSet()
                while(rs.next()){
                    GameCli.p.playerID = rs.getInt("player_id")
                    GameCli.p.playerName = rs.getString("player_name")
                    GameCli.p.characterName = rs.getString("character_name")
                    GameCli.p.goldTotal = rs.getString("gold_total").toInt
                }
                true
            }
            catch{
                case e: PSQLException => {
                    println(e.getMessage())
                    false
                }
            }
            finally {
                conOpt.get.close()
            }
        }
    }
    def loadFollowers() = {
        val conOpt:Option[Connection] = getConnection()
        if (conOpt == None) {
            false
        }
        else {
            try{
                val stmt = conOpt.get.prepareStatement("SELECT follower_name, hp FROM followers LEFT JOIN players ON players.player_id = followers.player_id WHERE player_name = ?;")
                stmt.setString(1, GameCli.p.playerName)
                println(stmt)
                stmt.execute()
                
                val rs = stmt.getResultSet()
                while(rs.next()){
                    GameCli.p.followers.addOne(Follower(rs.getString("follower_name"), rs.getString("hp").toInt))
                }
                true
            }
            catch{
                case e: PSQLException => {
                    println(e.getMessage())
                    false
                }
            }
            finally {
                conOpt.get.close()
            }
        }
    }
    def createPlayer(playerName: String, characterName: String):Boolean = {//create new account and login
        val conOpt:Option[Connection] = getConnection()
        if (conOpt == None) {
            false
        }
        else {
            try{
                val stmt = conOpt.get.prepareStatement("INSERT INTO Players (player_name, character_name, gold_total) " +
                  "VALUES (?,?,?);")
                stmt.setString(1, playerName)
                stmt.setString(2, characterName)
                stmt.setInt(3, 50)
                stmt.execute()
                true
            }
            catch{
                case e: PSQLException => {
                    println(e.getMessage())
                    false
                }
            }
            finally {
                conOpt.get.close()
            }
        }
    }
    //only call if GameCli.p.followers.length > 0
    def createFollowers(): Boolean = {
        val conOpt:Option[Connection] = getConnection()
        var followerInsertList = ArrayBuffer[String]()
        GameCli.p.followers.foreach(z => {
            followerInsertList.addOne("("+GameCli.p.playerID+",'"+z.name+"',"+z.HP+")")
        })
        if (conOpt == None) {
            false
        }
        else {
            try{
                val stmt = conOpt.get.prepareStatement("INSERT INTO Followers (player_id, follower_name, hp) " +
                  "VALUES "+followerInsertList.mkString(",")+";")
                stmt.execute()
                true
            }
            catch{
                case e: PSQLException => {
                    println(e.getMessage())
                    false
                }
            }
            finally {
                conOpt.get.close()
            }
        }
    }
    def updatePlayer():Boolean = {
        val conOpt:Option[Connection] = getConnection()
        if (conOpt == None) {
            false
        }
        else {
            try{
                val stmt = conOpt.get.prepareStatement("UPDATE Players SET player_name = ?, character_name = ?, gold_total = ? WHERE player_id = ?;")
                stmt.setString(1, GameCli.p.playerName)
                stmt.setString(2, GameCli.p.characterName)
                stmt.setInt(3, GameCli.p.goldTotal)
                stmt.setInt(4, GameCli.p.playerID)
                stmt.execute()
                true
            }
            catch{
                case e: PSQLException => {
                    println(e.getMessage())
                    false
                }
            }
            finally {
                conOpt.get.close()
            }
        }
    }
    def deletePlayer() = {
        val conOpt:Option[Connection] = getConnection()
        if (conOpt == None) {
            false
        }
        else {
            try{
                val stmt = conOpt.get.prepareStatement("DELETE FROM Players WHERE player_id = ?;")
                stmt.setInt(1, GameCli.p.playerID)
                stmt.execute()
                true
            }
            catch{
                case e: PSQLException => {
                    println(e.getMessage())
                    false
                }
            }
            finally {
                conOpt.get.close()
            }
        }
    }
    def deleteFollowers() = {
        val conOpt:Option[Connection] = getConnection()
        if (conOpt == None) {
            false
        }
        else {
            try{
                val stmt = conOpt.get.prepareStatement("DELETE FROM Followers WHERE player_id = ?;")
                stmt.setInt(1, GameCli.p.playerID)
                stmt.execute()
                true
            }
            catch{
                case e: PSQLException => {
                    println(e.getMessage())
                    false
                }
            }
            finally {
                conOpt.get.close()
            }
        }
    }
}