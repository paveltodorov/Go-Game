package GameLogic

case class Move(position: Tuple2[Int,Int], piece: Piece) {
  require(piece != Empty, "Invalid Piece, only BlackPiece or WhitePiece are allowed")
}

