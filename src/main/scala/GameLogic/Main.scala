package GameLogic

import io.IO
import io.Console._
import scala.io.StdIn
//import cats.effect.IO

object Main extends App {

  var gameState = GameState().playMove(Move((1,1),BlackPiece))
   
  gameState match {
   case Right(a) => println(a.toString)
   case _ => println("error")
}

//Console.putStrLn("5")
//IO(() => println("Heloo"))

val program = for {
 	_ <- putStrLn("Hello ")
    input <- getStrLn
    _ <- putStrLn(input)
  } yield input

program.unsafeRun();
}
 //def errorToDescription

/*
 def loop: IO[String] = for {
 	_ <- Console.putStrLn("Hello ")
    input <- Console.getStrLn
    _ <- Console.putStrLn(input)
  } yield input
*/
/*def processInput(input: String) : String {
 case  => bord.toAtring
 case "pass" => 
 case "r" => 
 }


 def processMove()	
 {
 	Rigth
	Left

 }
 	}
}/*

 //println("Hello, World!")
  /*val board = Board()
  	 .playMove(Move((1,2),WhitePiece))
  	 .playMove(Move((2,2),WhitePiece))
  	//.playMove(Move((2,2),BlackPiece))
  	//.playMove(Move((5,1),WhitePiece))

   println(board)*/

   /*val in = for{
   	input <- getStrLn
   } yield input

   println(in)*/
   override def main(args: Array[String]): Unit = {
   	println("--")
    loop.unsafeRun()
  }

 /*val board : Either[GameExceptions.GoException,GameLogic.Board] = Board(9,9)
  	.putPiece(Move((1,1),WhitePiece))*/
  	//.putPiece(Move((2,2),BlackPiece))
  	//.putPiece(Move((5,1),WhitePiece)) 

  //println(board.getThisPlayerChains()) 		
  
/*  board match {
  	case Right(_) => board
  	case Left(_) => 
  }*/
  
}*/*/