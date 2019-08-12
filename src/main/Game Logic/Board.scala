case class Board(rowCount: Int, 
	             colCount:Int, 
	             //positions: Vector[Vector[Piece]] = Vector.fill(rowCount, colCount)(Empty)
	             positions: Vector[Vector[Piece]] = Vector.fill(13, 13)(Empty),
                 nextPiece: Piece = BlackPiece) {
    require(positions.length == rowCount && positions.head.length == colCount,
      "Invalid positions length") 

  def getEnemyChains(): Set[Chain] = getChains(Piece.oponentPiece) 

  def getThisPlayerChains(): Set[Chain] = getChains(nextPiece)  

//TO DO
  def getChains(piece: Piece): Set[Chain] = {
    var chains: Set[Chain]= Set.empty
    for( i <- 0 to rowCount
         j <- 0 to colCount) {
       	val chain: Chain = getChainContaining(i,j)
        chains = chains + chain;
       } yield chains	
  }

  //def getChain(): Set[Chain] = ???
 
  def getChainContaining(x:Int, y: Int): Chain = BFS(Seq((x, y)), Set.empty, Set.empty)

  def BFS(toVisit: Seq[(Int, Int)], visited: Set[(Int, Int)], chain: Chain): Chain = toVisit.isEmpty match {
  	case true => chain
    case false => {
    	val position: (Int, Int) = toVisit.head
    	val neighbours: List[(Int, Int)] = neighbors(position._1, position._2).toSeq.filter {
              case(i, j) => !visited.contains((i, j)) && positions(i)(j) == positions(pos._1)(pos._2)
            }
        visit(toVisit.tail ++ neighbours, visited + position, chain.addElement(pos))
    }		
  }

  def neghbours(x: Int, y: Int): List[(Int, Int)] = {}
  	val neighborDisplacement = List((-1, 0), (1, 0), (0, -1), (0, 1))
    neighborDisplacement.map({ case (dx, dy)  => (x + dx, y + dy)})
    .filter({ case (i, j) => isInsideBoard(i, j) })
}

  //def setToChain(elements: Set[(Int, Int)]: Chain = Chain(elements)

 /* def removeCapturedPieces(): Board = {
  	val enemyChains: Set[Chain] = getEnemyChains

  }*/

  def isInsideBoard(x: Int, y: Int): Boolean = {
    0 <= x && x < rowCount && 0 <= y && y < colCount
  }
}
  