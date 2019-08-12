sealed abstract class Piece {
  val opponentPiece: Piece = Empty
}

case object Empty extends Piece

case object WhitePiece extends Piece {
  override val opponentPiece = BlackPiece
}

case object BlackPiece extends Piece {
  override val opponentPiece = WhitePiece
}
