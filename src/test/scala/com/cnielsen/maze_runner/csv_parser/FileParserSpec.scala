package com.cnielsen.maze_runner.csv_parser

import java.io.File

import org.scalatest.{FreeSpec, Matchers}

class FileParserSpec extends FreeSpec with Matchers {


  def fixture(file: File) = new {

    val fileParser = new FileParser(file)


  }

  "parse file should parse file to rights of int when parsing a well formed csv" in {

    val f = fixture(new File("src/test/resources/goodMaze.csv"))
    f.fileParser.parseFile shouldEqual List(
      List(Right(1), Right(1), Right(1), Right(1), Right(0), Right(1)),
      List(Right(1), Right(1), Right(1), Right(1), Right(0), Right(1)),
      List(Right(0), Right(0), Right(0), Right(0), Right(0), Right(1)),
      List(Right(1), Right(1), Right(1), Right(1), Right(1), Right(1))
    )

  }

  "parse file should parse file to lefts of NumberFormatException when parsing a malformed csv" in {

    val f = fixture(new File("src/test/resources/badMaze.csv"))


    f.fileParser.parseFile shouldEqual List(
      List(Right(1), Left(ParserExceptions.NumberFormatException), Right(1), Right(1), Right(0), Right(1)),
      List(Right(1), Right(1), Left(ParserExceptions.NumberFormatException), Right(1), Right(0), Right(1)),
      List(Right(0), Right(0), Right(0), Left(ParserExceptions.NumberFormatException), Right(0), Right(1)),
      List(Right(1), Left(ParserExceptions.NumberFormatException), Right(1), Right(1), Left(ParserExceptions.NumberFormatException), Right(1))
    )

  }

  "parse file should parse number that are 0 or 1 to NumberOutOfRangeException" in {
    val f = fixture(new File("src/test/resources/anotherBadMaze.csv"))


    f.fileParser.parseFile shouldEqual List(
      List(Right(1), Left(ParserExceptions.NumberOutOfRangeException), Right(1), Right(1), Right(0), Right(1)),
      List(Right(1), Right(1), Right(1), Right(1), Right(0), Left(ParserExceptions.NumberOutOfRangeException)),
      List(Left(ParserExceptions.NumberOutOfRangeException), Right(0), Right(0), Right(0), Right(0), Right(1)),
      List(Left(ParserExceptions.NumberOutOfRangeException), Right(1), Right(1), Right(1), Right(1), Left(ParserExceptions.NumberOutOfRangeException))
    )


  }


}
