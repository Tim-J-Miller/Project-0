package Project0

import org.postgresql.util.PSQLException
import java.sql.DriverManager
import java.sql.Connection

object Dao {
    var conn:Connection = null
    def run():Unit = {

    }
    def getConnection():Option[Connection] = {
        //manually load driver
        classOf[org.postgresql.Driver].newInstance()
        try {
            //use JDBC's DriverManager to get a connection. JDBC is DB agnostic
            conn = DriverManager.getConnection("")
            Some(conn)

        }
        catch {
            case e: PSQLException => {
                println(e.getMessage())
                None
            }
        }
    }
    def getPlayer(idNumber: Int):Boolean = {
        var conOpt:Option[Connection] = getConnection()
        if (conOpt == None) {
            false
        }
        else {
            try{
                //use drivermanager to get a connection
                //use the connection to prepare a sql statement
                val stmt = conOpt.get.prepareStatement("SELECT * FROM players WHERE player_id = ?;")
                stmt.setInt(1, idNumber)
                stmt.execute()
                //afterexecuting the statement, use it to get a resultset
                val rs = stmt.getResultSet()
                while(rs.next()){
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
    def getPlayer(playerName: String):Boolean = {  
        var conOpt:Option[Connection] = getConnection()
        if (conOpt == None) {
            false
        }
        else {
            try{
                //use drivermanager to get a connection
                //use the connection to prepare a sql statement
                val stmt = conOpt.get.prepareStatement("SELECT * FROM players WHERE player_name = ?;")
                stmt.setString(1, playerName)
                stmt.execute()
                //afterexecuting the statement, use it to get a resultset
                val rs = stmt.getResultSet()
                while(rs.next()){
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
    def getFollowers() = {
        var conOpt:Option[Connection] = getConnection()
        if (conOpt == None || GameCli.p.playerName == "") {
            false
        }
        else {
            try{
                //use drivermanager to get a connection
                //use the connection to prepare a sql statement
                val stmt = conOpt.get.prepareStatement("SELECT follower_name, hp FROM followers LEFT JOIN players ON players.player_id = followers.player_id WHERE player_name = ?;")
                stmt.setString(1, GameCli.p.playerName)
                stmt.execute()
                //afterexecuting the statement, use it to get a resultset
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
}