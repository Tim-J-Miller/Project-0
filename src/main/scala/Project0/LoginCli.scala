package Project0

import scala.io.StdIn
import scala.util.matching.Regex

class Cli{
    
    val commandArgPattern: Regex = "(\\w+)\\s*(.*)".r

    def welcomeMenu(): Unit ={
        printWelcome()
        var welcomeMenuLoop = true
        var choice = ""
        while (welcomeMenuLoop){
            printMenuOptions()
            var input = StdIn.readLine()
            input match {
                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("login") => {
                    loginMenu()
                    println(s"Logged in as ${cmd}")
                    welcomeMenuLoop = false
                }
                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("create") => {
                    createMenu(cmd.toLowerCase())
                    println(s"LCreated account ${cmd}")
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
    }
    
    def loginMenu():Unit = {
        //TODO
        //read from .JSON and print locally stored player IDs
        //request player enter their ID
        //if ID does not match .JSON info then ask if should query DB with entered info
            //if no then return to welcome loop
        //queary DB with .JSON/entered ID to validate account info
            //if no remote account found in DB print no such account exists and return to welcome loop
            
            //if DB returns valid info then enter execute GameCli.run with returned info

    }
    def createMenu():Unit = {
        //TODO
        //prompt user to enter the desired ID
        //if .JSON contains entered ID then ask for new ID loop until unique is entered
        //once ID is entered that isnt in .JSON query DB with entered ID, if not unique then restart loop
        //if new ID is not present in DB then add to DB and proceed to GameCli.run
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

}