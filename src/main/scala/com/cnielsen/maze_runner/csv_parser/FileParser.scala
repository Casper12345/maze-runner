package com.cnielsen.maze_runner.csv_parser

import java.io.{BufferedReader, File, FileReader}
import java.lang.NumberFormatException

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
          case Success(v) => Right(v)
        }
      )
    }
  }.toList

}

sealed trait ParserExceptions

object ParserExceptions {

  case object NumberFormatException extends ParserExceptions

  case object Exception extends ParserExceptions

}

