package GameLogic

import org.scalatest.{FlatSpec, Matchers}
import GameLogic.GameGrid.StringVectorToGameGrid

class GameGridTest extends FlatSpec with Matchers {

  "getChains(BlackPiece)" should "return the chains belonging to black player" in {
    val vectorGrid: Vector[String] =
      Vector("..BB..", ".BBBWW", "BWW...", "B.....")

    val grid: GameGrid = vectorGrid.toGameGrid
    val chains = grid.getChains(BlackPiece)
    chains.size shouldEqual 2
    chains.contains(Set((0, 2), (0, 3), (1, 1), (1, 2), (1, 3))) shouldEqual true
    chains.contains(Set((2, 0), (3, 0))) shouldEqual true
  }

  "getChains(BlackPiece) if the black player has no pieces" should "return an empty set" in {
    val vectorGrid: Vector[String] =
      Vector("......", "....WW", "......", "W.....")

    val grid: GameGrid = vectorGrid.toGameGrid
    val chains = grid.getChains(BlackPiece)
    chains.size shouldEqual 0
  }

  "getChains(WhitePiece)" should "return the chains belonging to white player" in {
    val vectorGrid: Vector[String] =
      Vector("..BB..", ".BBBWW", "BWW...", "B.....")

    val grid: GameGrid = vectorGrid.toGameGrid
    val chains = grid.getChains(WhitePiece)
    chains.size shouldEqual 2
    chains.contains(Set((1, 4), (1, 5))) shouldEqual true
    chains.contains(Set((2, 1), (2, 2))) shouldEqual true
  }

  "putPiece(((0,4),BlackPiece))" should "put a piece on the grid " in {
    val vectorGrid: Vector[String] =
      Vector("..BB..", ".BBBWW", "BWW...", "B.....")

    val grid: GameGrid = vectorGrid.toGameGrid
    val newGrid = grid.putPiece(Move((0, 4), BlackPiece))

    newGrid.toString shouldBe "..BBB.\n.BBBWW\nBWW...\nB....."
  }

  "remove(((1,1),BlackPiece))" should "remove a piece from the grid " in {
    val vectorGrid: Vector[String] =
      Vector("..BB..", ".BBBWW", "BWW...", "B.....")

    val grid: GameGrid = vectorGrid.toGameGrid
    val newGrid = grid.removePiece((1, 1))

    newGrid.toString shouldBe "..BB..\n..BBWW\nBWW...\nB....."
  }

  "removeChains" should "given positions from the board" in {
    val vectorGrid: Vector[String] =
      Vector("..BB..", ".BBBWW", "BWW...", "B.....")

    val grid: GameGrid = vectorGrid.toGameGrid
    val newGrid = grid.removeChains(List((0, 2), (0, 3)))

    newGrid.toString shouldBe "......\n.BBBWW\nBWW...\nB....."
  }

  "getLiberties" should "get all the liberties of a chain" in {
    val vectorGrid: Vector[String] =
      Vector("..BB..", ".BBBWW", "BWW...", "B.....")

    val grid: GameGrid = vectorGrid.toGameGrid

    val firstWhiteChainLiberties = grid.getLiberties(Set((1, 4), (1, 5)))

    firstWhiteChainLiberties.size shouldBe 4
    firstWhiteChainLiberties shouldBe Set((0, 5), (0, 4), (2, 4), (2, 5))

    grid.getLiberties(Set((2, 0), (3, 0))) shouldBe Set((1, 0), (3, 1))

    grid.getLiberties(Set((2, 0), (3, 0))) shouldBe Set((1, 0), (3, 1))

    grid.getLiberties(Set((0, 2), (0, 3), (1, 1), (1, 2), (1, 3))) shouldBe Set(
      (0, 1),
      (1, 0),
      (0, 4),
      (2, 3)
    )
  }

  "toString" should "convert a grid to string" in {
    val vectorGrid: Vector[String] =
      Vector("..BB..", ".BBBWW", "BWW...", "B.....")

    val grid: GameGrid = vectorGrid.toGameGrid
    grid.toString shouldEqual "..BB..\n.BBBWW\nBWW...\nB....."
  }

  "getCapturedPiecesBelongingTo(BlackPiece)" should "return all the captured pieces belonging to the Black player" in {
    val vectorGrid: Vector[String] =
      Vector(".WBBW.", "..WW.W", "W.W.B.", "BW....")

    val grid: GameGrid = vectorGrid.toGameGrid
    val capturedPieces = grid.getCapturedPiecesBelongingTo(BlackPiece)

    capturedPieces shouldEqual Set((0, 2), (0, 3), (3, 0))
  }

}
