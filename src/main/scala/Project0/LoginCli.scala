package Project0

import scala.io.StdIn
import scala.util.matching.Regex
import scala.collection.mutable.ArrayBuffer

object Cli{
    
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
                    if (arg.isEmpty()) loginMenu() else loginMenu(arg.toLowerCase())
                }
                case commandArgPattern(cmd, arg) if cmd.equalsIgnoreCase("create") => {
                    if (arg.isEmpty()) createMenu() else createMenu(arg.toLowerCase())
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
        var loginMenuLoop = true
        while (loginMenuLoop){
            val playerNames = FileUtil.getPlayerNames()
            val charNames = FileUtil.getCharacterNames()
            var jsonData = FileUtil.getJson()

            if (playerNames != None || charNames != None && loginMenuLoop){
                println("Please enter the username you would like to sign in with\nPreviously signed in with:\n")
                playerNames.get.foreach(println)
                var input = StdIn.readLine()
                if (playerNames.get.contains(input)){ 
                    jsonData.get.arr.foreach(x=>{
                        if (x.apply("playerName").str.equalsIgnoreCase(input)) {
                            println("Are you sure you want to sign in as Username: " + input + "\tCharacter name: " + x.apply("characterName").str + "\nY/N\n")
                        }
                    })
                    var yORn = StdIn.readLine()
                    if (yORn.equalsIgnoreCase("Y")){
                        loginMenuLoop = false
                        if(Dao.loadPlayer(input)){
                            if (GameCli.p.playerName != ""){
                                Dao.loadFollowers() 
                                GameCli.run()
                            }
                            else{
                                if (GameCli.p == Player()) println("No Player")
                            }
                        }
                    }
                }
                else if (loginMenuLoop){
                    println("Are you sure you want to sign in as Username: " + input +"\t[character name not stored locally]\nY/N\n")
                    var yORn  = StdIn.readLine()
                    if (yORn.equalsIgnoreCase("Y")){
                        loginMenuLoop = false
                        if(Dao.loadPlayer(input)){
                            if (GameCli.p.playerName != ""){
                                Dao.loadFollowers()
                                jsonData.get.arr.addOne(ujson.Obj("playerName"->GameCli.p.playerName,"characterName"->GameCli.p.characterName))
                                os.write.over(os.pwd/"players.json",jsonData.get)
                                GameCli.run()
                            }
                            else{
                                if (GameCli.p == Player()) println("No Player")
                            }
                        }
                    }
                }
            }
            else if(loginMenuLoop){
                println("Please enter the username you would like to sign in with\n")
                var input = StdIn.readLine()
                println("Are you sure you want to sign in as Username: " + input +"\nY/N\n")
                var yORn = StdIn.readLine()
                if (yORn.equalsIgnoreCase("Y")){
                    loginMenuLoop = false
                    if(Dao.loadPlayer(input)){
                        if (GameCli.p.playerName != ""){
                            Dao.loadFollowers()
                            jsonData.get.arr.addOne(ujson.Obj("playerName"->GameCli.p.playerName,"characterName"->GameCli.p.characterName))
                            os.write.over(os.pwd/"players.json",jsonData.get) 
                            GameCli.run()
                        }
                        else{
                            if (GameCli.p == Player()) println("No Player")
                        }
                    }
                }
            }
            loginMenuLoop = false
        }
    }
    def loginMenu(name: String):Unit = {
        var jsonData = FileUtil.getJson()
        val playerNames = FileUtil.getPlayerNames()
        if(Dao.loadPlayer(name)){
            if (GameCli.p.playerName != ""){
                if(Dao.loadFollowers()){ 
                    if (!playerNames.get.contains(name)){
                    jsonData.get.arr.addOne(ujson.Obj("playerName"->GameCli.p.playerName,"characterName"->GameCli.p.characterName))
                    os.write.over(os.pwd/"players.json",jsonData.get) 
                    }
                    GameCli.run()
                }
            }
        }
        else{
            loginMenu()
        }
    }
    def createMenu():Unit = {
        var createMenuLoop = true
        var jsonData = FileUtil.getJson()
        while (createMenuLoop){
            println("Please enter your desired Username")
            var usernameInput = StdIn.readLine()
            println("Please enter your desired Character name")
            var characternameInput = StdIn.readLine()
            if(Dao.createPlayer(usernameInput, characternameInput)){
                if(Dao.loadPlayer(usernameInput)){
                    if (GameCli.p.playerName != ""){
                        jsonData.get.arr.addOne(ujson.Obj("playerName"->GameCli.p.playerName,"characterName"->GameCli.p.characterName))
                        os.write.over(os.pwd/"players.json",jsonData.get)
                        println("Welcome to the game, visit the tavern first to aquire some followers before entering the dungeon")
                        GameCli.run()
                    }
                }
            }
            createMenuLoop=false
        }
    }
    def createMenu(name: String):Unit = {
        var jsonData = FileUtil.getJson()
        println("Please enter your desired Character name")
        var characternameInput = StdIn.readLine()
        if(Dao.createPlayer(name, characternameInput)){
            if(Dao.loadPlayer(name)){
                if (GameCli.p.playerName != ""){
                    jsonData.get.arr.addOne(ujson.Obj("playerName"->GameCli.p.playerName,"characterName"->GameCli.p.characterName))
                    os.write.over(os.pwd/"players.json",jsonData.get)
                    println("Welcome to the game, visit the tavern first to aquire some followers before entering the dungeon")
                    GameCli.run()
                }
            }      
        }
        else{
            createMenu()
        }
    }
    def printWelcome(): Unit = {
        println("Welcome to Project 0 CLI")
    }

    def printMenuOptions(): Unit = {
        List(
            "Login: Sign in with an existing ID",
            "Create: Create a new ID",
            "exit: exits WC CLI:\n"
            ).foreach(println)
    }
}