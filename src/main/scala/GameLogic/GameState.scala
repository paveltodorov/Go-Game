package GameLogic

import GameExceptions._

case class GameState( //positions: Vector[Vector[Piece]] = Vector.fill(rowCount, colCount)(Empty)
    grid: GameGrid = GameGrid(Vector.fill(9, 9)(Empty)),
    score: Double = -7.5,
    turn: Piece = BlackPiece,
    boardHistory: Stream[GameState] = Stream.empty
) {

  //type Chain = Set[Tuple2[Int, Int]]
  //type Grid = Vector[Vector[Piece]]
  //def getEnemyChains(): Set[Chain] = ???
  //def getThisPlayerChains(): Set[Chain] = ???

  def playMove(move: Move): Either[GoException, GameState] = {
    for {
      _ <- validateIsInsideBoard(move)
      _ <- validateIsNotOccupied(move)
      gameState <- putPiece(move)
    } yield gameState
  }

  /*def putPiece(move: Move): Either[GoException, Board] = move.piece match {
  case BlackPiece => Right(Board(grid.putPiece(move),score + 1,move.piece.opponentPiece))
  case WhitePiece => Right(Board(grid.putPiece(move),score - 1,move.piece.opponentPiece))
}*/

  def putPiece(move: Move): Either[GoException, GameState] = {
    val capturedYourPieces = grid.getCapturedPiecesBelongingTo(turn)
    val capturedEnemyPieces =
      grid.getCapturedPiecesBelongingTo(turn.opponentPiece)

    val capturedEnemyPiecesCount = capturedEnemyPieces.size

    if (capturedYourPieces.size > 0 && capturedEnemyPiecesCount == 0) {
      Left(SuicidalMoveException(move.position))
    } else {
      val newGrid: GameGrid = grid.removeChains(capturedEnemyPieces.toList)

      val newScore = turn match {
        case WhitePiece => score - capturedEnemyPieces.size - 1
        case BlackPiece => score + capturedEnemyPieces.size + 1
        case Empty      => score
      }

      Right(GameState(newGrid, newScore, turn.opponentPiece, boardHistory))
    }
  }

  def validateIsInsideBoard(move: Move): Either[GoException, GameState] =
    grid.isInsideBoard(move.position) match {
      case true  => Right(this)
      case false => Left(PositionNotInBoardException(move.position))
    }

  def validateIsNotOccupied(move: Move): Either[GoException, GameState] =
    grid.isOccupied(move.position) match {
      case true  => Left(OccupiedPositionexception(move.position))
      case false => Right(this)
    }

  def validateIsNotSuicidal(move: Move): Either[GoException, GameState] =
    grid.isSuicidalMove(move) match {
      case true  => Left(SuicidalMoveException(move.position))
      case false => Right(this)
    }

  def validateMoveIsNotRepeated() = ???

  override def toString(): String = {
    //grid.map(x => x.map(y => y.toString) + "\n") + "\n\nScore: " + score + "\nIs  " + nextPiece + "'s turn"
    "Score  " + score.toString + "\n" +
      grid.toString + "\n" + "\n"
    // "next turn: " + nextPie
    //val score: String =  "\n\nScore: " + score + "\nIs  " + nextPiece + "'s turn"
  }
}
