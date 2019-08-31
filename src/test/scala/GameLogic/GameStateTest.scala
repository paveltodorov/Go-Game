package GameLogic

import org.scalatest.{FlatSpec, Matchers}
import GameLogic.GameGrid.StringVectorToGameGrid
import GameExceptions._

class GameStateTest extends FlatSpec with Matchers {

  "playMove on ocupppied fielg" should "return OccupiedPositionexception" in {
    val vectorGrid: Vector[String] =
      Vector("..BB..", 
             ".B..WW", 
             "BWW...")

    val grid: GameGrid = vectorGrid.toGameGrid

    val state: GameState = GameState(grid)
    val newState = state.playMove(Move((0,3),BlackPiece))
    //newState shouldEqual Left(OccupiedPositionException((0,3)))
    newState shouldEqual Left(OccupiedPositionException((0,3)))
  }

  "playing an illegal siuciadal move" should "return SuicidalMoveException" in {
    val vectorGrid: Vector[String] =
      Vector("..BB..", 
             ".BWWB.", 
             "BWW.B.")

    val grid: GameGrid = vectorGrid.toGameGrid

    val state: GameState = GameState(grid,-7.5,WhitePiece)
    val newState = state.playMove(Move((2,3),WhitePiece))
    //newState shouldEqual Left(OccupiedPositionException((0,3)))
    newState shouldEqual Left(SuicidalMoveException((2,3)))
  }

  "playing an illegal siuciadal move on eye" should "return SuicidalMoveException" in {
    val vectorGrid: Vector[String] =
      Vector(".B....", 
             "B.B.B.", 
             "BBW.B.")

    val grid: GameGrid = vectorGrid.toGameGrid

    val state: GameState = GameState(grid,-7.5,WhitePiece)
    val newState = state.playMove(Move((1,1),WhitePiece))
    //newState shouldEqual Left(OccupiedPositionException((0,3)))
    newState shouldEqual Left(SuicidalMoveException((1,1)))
  }

  "playing a legal siuciadal move" should "put a piece on the board" in {
    val vectorGrid: Vector[String] =
      Vector("...BW.", 
             "..B.BW", 
             "BWWWB.")

    val grid: GameGrid = vectorGrid.toGameGrid

    val state: GameState = GameState(grid,-7.5,WhitePiece)
    val newState = state.playMove(Move((1,3),WhitePiece))
     //newState.right.grid shouldEqual "...BW.\n..BW.W\nBWWWB."
    val isValid = newState match {
      case Right(_) => true
      case Left(_) => false
    } 

    isValid shouldEqual true  
  }



  "playing an illegal repeating move" should "return RepeatedMoveException" in {
    val vectorGrid: Vector[String] =
      Vector("..WB..", 
             "BW.WB.", 
             "..WB..")

    val grid: GameGrid = vectorGrid.toGameGrid

    val state: GameState = GameState(grid,-7.5,BlackPiece)
    val newState = state.playMove(Move((1,2),BlackPiece))
    println(newState.toString)
    newState.right.get.playMove(Move((1,3),WhitePiece)) shouldEqual Left(RepeatedMoveException((1,3)))

    //newState shouldEqual Left(OccupiedPositionException((0,3)))
    //newState shouldEqual Left(SuicidalMoveException((1,1)))
  }

}
