package Client

import java.io.BufferedReader
import java.io.IOException
import java.lang.Thread
import scala.collection.JavaConversions._

class ClientMessageReceiver(private var reader: BufferedReader)
    extends Thread {

  //this.setDeamon(true)
  //super.deamon = true
  this.setDaemon(true)
  override def run(): Unit = {
    while (true) try {
      val msg: String = reader.readLine()
      if(msg != null) println(msg)
    } catch {
      case e: IOException => e.printStackTrace()
    }
  }

  def getIn(): BufferedReader = reader

  def setReader(reader: BufferedReader): Unit = {
    this.reader = reader
  }

}
