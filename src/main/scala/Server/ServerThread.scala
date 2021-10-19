package Server

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.List
import GameLogic._
import Server.Commands._
import io.IO
import io.Console._
import scala.io.StdIn

class ServerThread(var socket: Socket, var gameState: GameState) extends Thread {
  val reader: BufferedReader = new BufferedReader(
    new InputStreamReader(socket.getInputStream))
  val writer: PrintWriter =
    new PrintWriter(socket.getOutputStream, true)

  def sendMessageToClient(msg: String): Unit = {
    writer.println(msg)
    writer.flush()
  }

  override def run(): Unit = {
    try {
      while (true) {
        val input: IO[String] = IO(() => reader.readLine())
        val newState: IO[(GameState, String)] = input.map(x => x.executeCommand(gameState));
        val runnedState = newState.unsafeRun();
        val output = runnedState._2
        val newGameState = runnedState._1
        gameState =  newGameState
        sendMessageToClient(s"\n\n${newGameState.toString}\n${output}\nit is ${newGameState.turn.toString}'s turn")
      }
    } catch {
      case e: IOException => println(e)

    } finally try {
      socket.close()
      writer.close()
      reader.close()
    } catch {
      case e: IOException => {}
    }
  }
}

// val program = for {
//     input <- getStrLn
//     (newState, output) <- input.executeCommand(gameState)
//     _ <- sendMessageToClient(input + "\n\n" + newState.toString + "\n" +
//           output + "\n" + "it is " + newState.turn.toString + " 's turn")
//   } yield newState

//        val newState = program.unsafeRun();
 //val input: String = reader.readLine()
        // val (newState, output): (GameState, String) = input.executeCommand(gameState)
        //gameState = newState.copy(newState.grid,newState.score,newState.turn,newState.boardHistory)