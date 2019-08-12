trait Game {

  val rowCount: Int
  val colCount: Int

  //type Positions = Vector[Vector[Piece]]
  /**
   * A record of boards of the game.
   * The Stream.head is the current board
   */
  type BoardRecord = Stream[Board]



  def isLegalMove(move: Move, boardRecord: BoardRecord): Boolean = ???

  /**
   * Given a move and board record, play a move on the board, capture no-liberty opponent's pieces
   * If a move is illegal, an IllegalArgumentException exception will be thrown
   * @return a new board record
   */
  def playMove(move: Move, boardRecord: BoardRecord): BoardRecord = ???

  def reverseOneMove(boardRecord) : BoardRecord = boardRecord match {
  	case x => boardRecord.tail
  	case _ => Board();	
  } ;
}
