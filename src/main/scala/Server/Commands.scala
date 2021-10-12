package Server

import GameLogic._
import GameExceptions._

object Commands {
  implicit class CommandReader(val command: String) {
    def executeCommand(gameState: GameState): (GameState, String) = command.toLowerCase.split("-").toList match {
      case List("n") | List("new") => (GameState(), "A new Board Was created")

      case List("n", dimention, komi) =>
        (GameState(GameGrid(Vector.fill(dimention.toInt, dimention.toInt)(Empty)), -komi.toDouble),
          "A new Board Was created")

      case List(i, j) => {
        val move: Move = Move((i.toInt, j.toInt), gameState.turn)
        val newGameState: Either[GoException, GameState] = gameState.playMove(move)
        newGameState match {
          case Right(validState) => (validState, "move played")
          case Left(error) => (gameState, error.description)
        }
      }

      case List("pass") | List("p") => (gameState.passTurn(), gameState.turn.toString + " passed his/her turn")
      case List("s") | List("surrender") => (gameState, gameState.turn.opponentPiece.toString + "wins the game")
      case List("h") | List("help") => (gameState, printAllCommandsDecription())
      case _ => (gameState, "command not recognized")
    }
  }

  def printAllCommandsDecription(): String = {
    "n - creates a new board with dimentions 9x9\n" +
      "new - creates a new board with dimentions 9x9\n" +
      "n-d-k - creates a new board with dimentions d and komi -k\n" +
      "i-j - plays a move on position (i j)\n" +
      "pass - current player passes his/her turn\n" +
      "s - you surrender the game\n" +
      "h - prints all commands\n"
  }
}