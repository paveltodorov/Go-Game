package GameLogic

sealed abstract class Piece {
  val opponentPiece: Piece = Empty
  override val toString: String = "."
}

case object Empty extends Piece{
 //override val toString: String = "."
}

case object WhitePiece extends Piece {
  override val opponentPiece = BlackPiece
  override val toString: String = "W"
}

case object BlackPiece extends Piece {
  override val opponentPiece = WhitePiece
  override val toString: String = "B"
}