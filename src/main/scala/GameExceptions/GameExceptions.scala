package GameExceptions

import GameExceptions._

sealed trait GoException{
	val description: String = ""
}

case class PositionNotInBoardException(position: (Int,Int)) extends GoException {
	override val description: String = position.toString + " is outside the board"
}

case class SuicidalMoveException(position: (Int,Int)) extends GoException {
	override val description: String = position.toString + " is an illegal suicidal (or self-capture) move. "
}

case class RepeatedMoveException(position: (Int,Int)) extends GoException{
	override val description: String = position.toString + " is illegal because playing on it repeates the previous game state"
}

/*case class PositionIsEyeException(position: (Int,Int)) extends GoException {
	override val description: String = position.toString + " is an eye"
}*/

case class OccupiedPositionException(position: (Int,Int)) extends GoException{
	override val description: String = position.toString + " is already ocupied by another piece"
}


