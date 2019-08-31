package GameLogic

case class GameGrid(grid: Vector[Vector[Piece]]) {
  type Chain = Set[Tuple2[Int, Int]]

  def getWidth() : Int = grid(0).size

  def getHight() : Int = grid.size

  def getChains(piece: Piece): Set[Chain] =
    getChainsHelper(Move((0, 0), piece), Set.empty, Set.empty)

  def getChainsHelper(
      move: Move,
      chains: Set[Chain],
      visited: Chain
  ): Set[Chain] = {
    val nextPosition: Tuple2[Int, Int] = move.position.getNextPosition()

    if (nextPosition != null) {
      val i = move.position._1
      val j = move.position._2
      if (grid(i)(j) == move.piece && !visited.contains((i, j))) {
        val currentChain: Chain = getChainContaining(i, j)
        getChainsHelper(
          Move(nextPosition, move.piece),
          chains + currentChain,
          visited ++ currentChain
        )
      } else {
        getChainsHelper(Move(nextPosition, move.piece), chains, visited)
      }
    } else {
      chains
    }
  }

  def getChainContaining(x: Int, y: Int): Chain =
    BFS(Seq((x, y)), Set.empty, Set.empty)

  def BFS(
      toVisit: Seq[(Int, Int)],
      visited: Set[(Int, Int)],
      chain: Chain
  ): Chain = toVisit.isEmpty match {
    case true => chain
    case false => {
      val position: (Int, Int) = toVisit.head
      val neighbours: Seq[(Int, Int)] = position.getNeighbours().toSeq.filter {
        case (i, j) =>
          !visited
            .contains((i, j)) && grid(i)(j) == grid(position._1)(position._2)
      }
      BFS(toVisit.tail ++ neighbours, visited + position, chain + position)
    }
  }

  def putPiece(move: Move): GameGrid = {
    GameGrid(
      grid.updated(
        move.position._1,
        grid(move.position._1).updated(move.position._2, move.piece: Piece)
      )
    )
  }

  def removePiece(tuple: Tuple2[Int, Int]): GameGrid = {
    GameGrid(
      grid.updated(tuple._1, grid(tuple._1).updated(tuple._2, Empty: Piece))
    )
  }

  def removeChains(positionsToBeRemoved: List[Tuple2[Int, Int]]): GameGrid =
    positionsToBeRemoved match {
      case Nil       => this
      case (x :: xs) => this.removePiece(x).removeChains(xs)
    }

  def getLiberties(chain: Set[Tuple2[Int, Int]]): Set[Tuple2[Int, Int]] = {
    chain.flatMap(x => x.getNeighbours).filter(x => grid(x._1)(x._2) == Empty)
  }

  def isCaptured(chain: Set[Tuple2[Int, Int]]): Boolean = {
    getLiberties(chain).isEmpty
  }

  def getCapturedPiecesBelongingTo(piece: Piece): Chain = {
    getChains(piece).filter(x => isCaptured(x)).flatten
  }

  override def toString(): String = {
    grid.map(x => x.mkString).mkString("\n") 
  }

  def toStringWithMarkers(): String  = { 
    ("012345678901234567890" take this.getWidth mkString) + "\n" + 
    grid.zipWithIndex.map{case (x,i) => x.mkString + " " + i}.mkString("\n") 
  }

  def isInsideBoard(tuple: Tuple2[Int, Int]): Boolean = tuple.isInsideBoard

  def isOccupied(tuple: Tuple2[Int, Int]): Boolean = tuple.isOccupied

  implicit class Neighbour(tuple: Tuple2[Int, Int]) {
    def getNeighbours(): Set[Tuple2[Int, Int]] = {
      val neighborDisplacement = Set((-1, 0), (1, 0), (0, -1), (0, 1))
      neighborDisplacement
        .map({ case (dx, dy) => (tuple._1 + dx, tuple._2 + dy) })
        .filter({ case (i, j) => (i, j).isInsideBoard })
    }

    def isInsideBoard(): Boolean = {
      0 <= tuple._1 && tuple._1 < grid.size && 0 <= tuple._2 && tuple._2 < grid(0).size
    }

    def isOccupied(): Boolean = grid(tuple._1)(tuple._2) != Empty

    def isEmpty(): Boolean = grid(tuple._1)(tuple._2) == Empty

    def getPiece(): Piece = grid(tuple._1)(tuple._2)

    //check if a field is Eye
    def isEye(piece: Piece): Boolean = {
      getNeighbours()
        .filter(x => grid(x._1)(x._2) == piece.opponentPiece)
        .size == 4
    }

    def getNextPosition(): Tuple2[Int, Int] = {
      if (tuple._2 < getWidth - 1) (tuple._1, tuple._2 + 1)
      else if (tuple._1 < (getHight - 1)) (tuple._1 + 1, 0)
      else null
    }
  }
}


object GameGrid {
  implicit class StringToGameGrid(str: String) {
    def toGameGrid(): GameGrid = {
      ???
    }
  }

  implicit class VectorToGameGrid(vector: Vector[Vector[Character]]) {
    def toGameGrid(): GameGrid = {
      GameGrid(vector.map(x => x.map(y => characterToPiece(y))))
    }

    def characterToPiece(char: Char): Piece = char match {
      case 'B' => BlackPiece
      case 'W' => WhitePiece
      case '.' => Empty
      case _   => Empty
    }
  }

  implicit class StringVectorToGameGrid(val vector: Vector[String])
      extends AnyVal {
    def toGameGrid(): GameGrid = {
      GameGrid(vector.map(x => x.map(y => characterToPiece(y)).toVector))
    }

    def characterToPiece(char: Char): Piece = char match {
      case 'B' => BlackPiece
      case 'W' => WhitePiece
      case '.' => Empty
      case _   => Empty
    }
  }
}
