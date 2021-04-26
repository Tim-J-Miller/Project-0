package Project0

import java.nio.file.NoSuchFileException
import scala.collection.mutable.ArrayBuffer
import ujson.Value

object FileUtil {
    def makeJson() ={
        try{
            val jsonString = os.read(os.pwd/"players.json")
        }
        catch {
            case e: NoSuchFileException => {
                os.write(os.pwd/"players.json", ujson.Obj())
            }
        }  
    }
    def getJson():Option[Value.Value] ={
        try{
            val jsonString = os.read(os.pwd/"players.json")
            val data = ujson.read(jsonString)
            Some(data)
        }
        catch {
            case e: NoSuchFileException => {None}
        }  
    }
    def getPlayerNames():Option[ArrayBuffer[String]] = {
        try{
            val jsonString = os.read(os.pwd/"players.json")
            val data = ujson.read(jsonString)
            var names = ArrayBuffer[String]()
            data.arr.foreach(x => {names.addOne(x.apply("playerName").str)})
            Some(names)
        }
        catch {
            case e: NoSuchFileException => {None}
        }  
    }
    def getCharacterNames():Option[ArrayBuffer[String]] = {
        try{
            val jsonString = os.read(os.pwd/"players.json")
            val data = ujson.read(jsonString)
            var names = ArrayBuffer[String]()
            data.arr.foreach(x => {names.addOne(x.apply("characterName").str)})
            Some(names)
        }
        catch {
            case e: NoSuchFileException => {None}
        }  
    }
}