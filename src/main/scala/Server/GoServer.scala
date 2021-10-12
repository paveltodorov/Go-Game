package Server

import java.net.ServerSocket
import GameLogic._

object GoServer {
  val port: Int = 80
  var gameState: GameState = GameState(GameGrid(Vector.fill(9, 9)(Empty)), -7.5, BlackPiece, GameGrid(Vector.fill(9, 9)(Empty)))

  def main(args: Array[String]): Unit = {
    println("The Go Game server is running.")
    val listener: ServerSocket = new ServerSocket(port)
    try while (true) new ServerThread(listener.accept(), gameState).start()
    finally listener.close()
  }
}