case class Chain(elements: Set[(Int, Int)]) {
 def addElement(element: (Int,Int)) = elements + element	
 def getLiberties(): Set[(Int, Int)] = ???
 def isCaptured(): Boolean = getLiberties.isEmpty
}