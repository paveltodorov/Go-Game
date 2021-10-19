package Client

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.Socket
import java.net.UnknownHostException
import java.util.Scanner
import Client._
import io.IO
import io.Console._
import scala.io.StdIn
//import cats.effect.IO

object Client {

  def main(args: Array[String]): Unit = {
    val client: Client = new Client()
    client.run()
    client.close()
  }

}

class Client() {
  val port: Int = 80
  val socket: Socket = new Socket("localhost", port)
  val in: BufferedReader = new BufferedReader(
    new InputStreamReader(socket.getInputStream))
  val out: PrintWriter = new PrintWriter(socket.getOutputStream, true)

  private def run(): Unit = {
    val sc: Scanner = new Scanner(System.in)
    val receiver: ClientMessageReceiver = new ClientMessageReceiver(in)
    receiver.start()

    try while (true) {
      // val msg: String = sc.next()
      // out.println(msg)
      // out.flush()

      // val msg = IO(() => sc.next())
      // val msg.flatMap(x => out.println(x))
      val program = for {
        msg <- IO(() => sc.next())
        _ <- IO(() => out.println(msg))
        _ <- IO(() => out.flush())
      } yield msg
      program.unsafeRun()

    } finally sc.close()
  }

  /*

  try{while (true) {
      /*val msg: String = sc.next()
      out.println(msg)
      out.flush()*/
   val program = for {
    msg <- IO(sc.next())
    _ <- IO(out.println(msg))
    _ <- IO(out.flush())
   } yield ()
  program.unsafeRun()
}
    finally sc.close()

  */

  def close(): Unit = {
    socket.close()
    in.close()
    out.close()
  }
}
