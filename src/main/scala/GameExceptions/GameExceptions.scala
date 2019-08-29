package GameExceptions

sealed trait GoException

case class PositionNotInBoardException(positioin: Tuple2[Int,Int]) extends GoException

case class SuicidalMoveException(positioin: Tuple2[Int,Int]) extends GoException

case class RepeatedMoveException(positioin: Tuple2[Int,Int]) extends GoException

case class PositionIsEyeException(positioin: Tuple2[Int,Int]) extends GoException

case class OccupiedPositionexception(positioin: Tuple2[Int,Int]) extends GoException


