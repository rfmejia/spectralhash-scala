package com.github.rfmejia.spectralhash.unmarshalling

import scala.util.Try

/**
 * Base trait for converting an instance T into a list of peak pair tuples.
 */
trait SpectrumUnmarshaller[T] {
  def unmarshal(input: T): Try[Seq[(Double, Double)]]
}

/**
 *  Unmarshaller for spectrum strings where
 *
 *  - Each line is one peak pair
 *  - Each peak is separated by a space
 *  - Intensity values are absolute
 *
 */
trait BasicStringUnmarshaller extends SpectrumUnmarshaller[String] {
  def unmarshal(input: String): Try[Seq[(Double, Double)]] = {
    def stringToTuple(in: String): (Double, Double) = in.split(' ') match {
      case Array(ion, intensity) => (ion.toDouble, intensity.toDouble)
      case s: Array[String] =>
        throw new IllegalArgumentException(s"Could not parse input line '$in' for peak pair")
    }

    Try {
      val nonEmptyPairs = input.split('\n').map(_.trim).filter(!_.isEmpty)
      val absolute = nonEmptyPairs.map(stringToTuple)
      val maxIntensity = absolute.map(_._2).max
      absolute.map(int => (int._1, int._2 / maxIntensity * 100.0f))
    }
  }
}

object BasicStringUnmarshaller extends BasicStringUnmarshaller
