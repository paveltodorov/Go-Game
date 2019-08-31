package GameExceptions

import GameExceptions._

sealed trait GoException{
	val description: String = ""
}

case class PositionNotInBoardException(position: Tuple2[Int,Int]) extends GoException {
	override val description: String = position.toString + " is outside the bord"
}

case class SuicidalMoveException(position: Tuple2[Int,Int]) extends GoException {
	override val description: String = position.toString + " is an illegal suicidal (or self-capture) move. "
}

case class RepeatedMoveException(position: Tuple2[Int,Int]) extends GoException{
	override val description: String = position.toString + " is illegal because playing on it repeates the previous game state"
}

/*case class PositionIsEyeException(position: Tuple2[Int,Int]) extends GoException {
	override val description: String = position.toString + " is an eye"
}*/

case class OccupiedPositionException(position: Tuple2[Int,Int]) extends GoException{
	override val description: String = position.toString + " is already ocupied by another piece"
}


