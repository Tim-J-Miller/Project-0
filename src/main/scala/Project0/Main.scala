package Project0
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import java.sql.DriverManager

object Main {
  def main(args: Array[String]):Unit= {
    println("Hello, World! 👺")
     
    
    // //manually load driver
    //   classOf[org.postgresql.Driver].newInstance()
      
    //   //use JDBC's DriverManager to get a connection. JDBC is DB agnostic
    //   val conn = DriverManager.getConnection("")

    //   //use drivermanager to get a connection
    //   //use the connection to prepare a sql statement
    //   val stmt = conn.prepareStatement("SELECT * FROM track WHERE length(name) > ?;")
    //   stmt.setInt(1,50)
    //   stmt.execute()
    //   //afterexecuting the statement, use it to get a resultset
    //   val rs = stmt.getResultSet()
    //   while(rs.next()){
    //     println(rs.getString("name"))//retrieve parts of records off the resultset
    //   }
  }
}