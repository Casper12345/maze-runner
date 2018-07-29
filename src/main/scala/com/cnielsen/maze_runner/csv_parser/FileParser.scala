package com.cnielsen.maze_runner.csv_parser

import java.io.{BufferedReader, File, FileReader}
import scala.compat.java8.StreamConverters._
import javax.inject.Inject
import scala.util.Try

class FileParser @Inject()(
                            file: File
                          ) {


  def parseFile: List[List[Either[Throwable, Int]]] = {
    new BufferedReader(new FileReader(file)).lines().toScala[Stream].map { item =>
      item.split(",").toList.map(a =>
        Try(a.trim.toInt).toEither
      )
    }
  }.toList


}

