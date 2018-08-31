package com.cnielsen.maze_runner.csv_parser

import java.io.{BufferedReader, File, FileReader}
import scala.compat.java8.StreamConverters._
import javax.inject.Inject
import scala.util.{Failure, Success, Try}

class FileParser @Inject()(
                            file: File
                          ) {

  def parseFile: List[List[Either[ParserExceptions, Int]]] = {
    new BufferedReader(new FileReader(file)).lines().toScala[Stream].map { item =>
      item.split(",").toList.map(a =>
        Try(a.trim.toInt) match {
          case Failure(e) => e match {
            case _: NumberFormatException => Left(ParserExceptions.NumberFormatException)
            case _ => Left(ParserExceptions.Exception)
          }
          case Success(v) =>
            if (v == 1 || v == 0) {
              Right(v)
            } else {
              Left(ParserExceptions.NumberOutOfRangeException)
            }
        }
      )
    }
  }.toList

  val oneError = parseFile.collect()

}

sealed trait ParserExceptions

object ParserExceptions {

  case object NumberFormatException extends ParserExceptions

  case object Exception extends ParserExceptions

  case object NumberOutOfRangeException extends ParserExceptions

}

