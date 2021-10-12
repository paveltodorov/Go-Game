package Server

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.util.List
import GameLogic._
import Server.Commands._

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
        val input: String = reader.readLine()
        val (newState, output): (GameState, String) = input.executeCommand(gameState)
        //gameState = newState.copy(newState.grid,newState.score,newState.turn,newState.boardHistory)
        gameState = newState
        sendMessageToClient(input + "\n\n" + newState.toString + "\n" +
          output + "\n" + "it is " + newState.turn.toString + " 's turn")
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