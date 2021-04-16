package Project0

import scala.io.StdIn
import scala.util.matching.Regex
import scala.collection.mutable.ArrayBuffer

class Cli{
    
    val commandArgPattern: Regex = "(\\w+)\\s*(.*)".r
    
    var p = Player()

    def welcomeMenu(): Unit ={
        printWelcome()
        var welcomeMenuLoop = true
        var playerID = ""
        var charName = ""
        var goldTotal = 0
        while (welcomeMenuLoop){
            printMenuOptions()
            var input = StdIn.readLine()
            input match {
                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("login") => {
                    if (arg.isEmpty()) playerID = loginMenu() else playerID = loginMenu(arg.toLowerCase())
                    println(s"Logged in as $playerID")
                    welcomeMenuLoop = false
                }
                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("create") => {
                    if (arg.isEmpty()) charName = createMenu() else charName = createMenu(arg.toLowerCase())
                    println(s"Created account $charName")
                    welcomeMenuLoop = false
                }
                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("exit") => {
                    welcomeMenuLoop = false
                }
                case commandArgPattern(cmd, arg) => {
                    println(s"Parsed command $cmd with args $arg did not correspond to an option")
                }
                case _ => {
                    println("Failed to parse a command")
                }
            }
        }
        println("Welcome to the game, visit the tavern first to aquire some followers before entering the dungeon")
        GameCli.run() 
    }
    
    def loginMenu():String = {
        //TODO
        //read from .JSON and print locally stored player IDs
        //request player enter their ID
        //if ID does not match .JSON info then ask if should query DB with entered info
            //if no then return to welcome loop
        //queary DB with .JSON/entered ID to validate account info
            //if no remote account found in DB print no such account exists and return to welcome loop
            
            //if DB returns valid info then enter execute GameCli.run with returned info
""
    }    
    def loginMenu(id: String):String = {
        //TODO
        //if ID does not match .JSON info then ask if should query DB with entered info
            //if no then return to welcome loop
        //queary DB with .JSON/entered ID to validate account info
            //if no remote account found in DB print no such account exists and return to welcome loop
            
            //if DB returns valid info then enter execute GameCli.run with returned info
""
    }
    def createMenu():String = {
        //TODO
        //prompt user to enter the desired ID
        //if .JSON contains entered ID then ask for new ID loop until unique is entered
        //once ID is entered that isnt in .JSON query DB with entered ID, if not unique then restart loop
        //if new ID is not present in DB then add to DB and proceed to GameCli.createNewChar
        ""
    }
    def createMenu(id :String):String = {
        //TODO
        //if .JSON contains entered ID then ask for new ID loop until unique is entered
        //once ID is entered that isnt in .JSON query DB with entered ID, if not unique then restart loop
        //if new ID is not present in DB then add to DB and proceed to GameCli.createNewChar
        ""
    }

    def delete(player: Player) = {
        //drop player from DB
        println(player)
    }
    def printWelcome(): Unit = {
        println("Welcome to Project 0 CLI")
    }

    def printMenuOptions(): Unit = {
        List(
            "Login: Sign in with an existing ID",
            "Create: Create a new ID",
            "exit: exits WC CLI:"
            ).foreach(println)
    }

    def createNewChar(playerID: String):Unit = {
        //logged on with new character

        var newCharName = ""
        var startingGold = 750
        printCharGenWelcome()
        var charGenMenuLoop = true
        while (charGenMenuLoop){
            printCharGenMenuOptions()
            var input = StdIn.readLine()
            input match {
                case "exit" => {
                    charGenMenuLoop = false
                }
                case _ => {
                    if (input.isEmpty()) {
                        println("Please name your character")
                    }
                     else{
                        newCharName = input
                        //add row to player table, values (playerID, newCharName, startingGold)
                        println(s"Created character $newCharName")
                        charGenMenuLoop = false
                     } 
                }
            }
        }
    }
    
    def printCharGenWelcome():Unit = {

    }
    def printCharGenMenuOptions():Unit ={

    }
}