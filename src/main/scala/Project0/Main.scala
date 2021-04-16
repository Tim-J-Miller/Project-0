package Project0
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import java.sql.DriverManager
import java.sql.Connection

object Main {
  def main(args: Array[String]):Unit= {
    println("Hello, \t World! 👺")



    //var p = Player("Tim", "Heros", 750)
    //GameCli.run(p)

    // Dao.getPlayer("Tim")
    // GameCli.run()
    
    // //manually load driver
    //   classOf[org.postgresql.Driver].newInstance()
      
    //   //use JDBC's DriverManager to get a connection. JDBC is DB agnostic
    //   val conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/P-0", "postgres", "postgres")

    //   //use drivermanager to get a connection
    //   //use the connection to prepare a sql statement
    //   val stmt = conn.prepareStatement("SELECT * FROM players;")
    //   stmt.execute()
    //   //afterexecuting the statement, use it to get a resultset
    //   val rs = stmt.getResultSet()
    //   while(rs.next()){
    //     println(rs.getString(1) + " " + rs.getString(2) + " " + rs.getString(3)+ " " + rs.getString(4))//retrieve parts of records off the resultset
    //   }
  }
}