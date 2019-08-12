case class Move(x: Int, y: Int, piece: Piece) {
  require(piece != Empty, "Invalid Piece, only BlackPiece or WhitePiece")
}

