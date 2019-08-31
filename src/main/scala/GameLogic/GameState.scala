package GameLogic

import GameExceptions._

case class GameState( //positions: Vector[Vector[Piece]] = Vector.fill(rowCount, colCount)(Empty)
    grid: GameGrid = GameGrid(Vector.fill(9, 9)(Empty)),
    score: Double = -7.5,
    turn: Piece = BlackPiece,
    //boardHistory: Stream[GameState] = Stream.empty
    previousGrid: GameGrid = GameGrid(Vector.fill(9, 9)(Empty))
) {

  def playMove(move: Move): Either[GoException, GameState] = {
    for {
      _ <- validateIsInsideBoard(move)
      _ <- validateIsNotOccupied(move)
      gameState <- putPiece(move)
      _ <- validateMoveIsNotRepeated(move, gameState)
    } yield gameState
  }

  def putPiece(move: Move): Either[GoException, GameState] = {
    val gridWithTheNewPiece = grid.putPiece(move)
    val capturedYourPieces = gridWithTheNewPiece.getCapturedPiecesBelongingTo(turn)
    val capturedEnemyPieces =
      gridWithTheNewPiece.getCapturedPiecesBelongingTo(turn.opponentPiece)

    val capturedEnemyPiecesCount = capturedEnemyPieces.size

    if (capturedYourPieces.size > 0 && capturedEnemyPiecesCount == 0) {
      Left(SuicidalMoveException(move.position))
    } else {
      val newGrid: GameGrid = gridWithTheNewPiece.removeChains(capturedEnemyPieces.toList)

      val newScore = turn match {
        case WhitePiece => score - capturedEnemyPieces.size - 1
        case BlackPiece => score + capturedEnemyPieces.size + 1
        case Empty      => score
      }
      Right(GameState(newGrid, newScore, turn.opponentPiece, grid))
    }
  }

  def validateIsInsideBoard(move: Move): Either[GoException, GameState] =
    grid.isInsideBoard(move.position) match {
      case true  => Right(this)
      case false => Left(PositionNotInBoardException(move.position))
    }

  def validateIsNotOccupied(move: Move): Either[GoException, GameState] =
    grid.isOccupied(move.position) match {
      case true  => Left(OccupiedPositionException(move.position))
      case false => Right(this)
    }

  def validateMoveIsNotRepeated(move: Move,gameState: GameState): Either[GoException, GameState] = 
    (gameState.grid == previousGrid) match {
    case true => Left(RepeatedMoveException(move.position))
    case false => Right(this) 
  }

  def passTurn(): GameState = GameState(grid, score, turn.opponentPiece, previousGrid)

  override def toString(): String = {
    "Score  " + score.toString + "\n" + grid.toStringWithMarkers() + "\n" + "\n"
  }
}
