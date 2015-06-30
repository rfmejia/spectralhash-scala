package com.github.rfmejia.spectralhash.generators

import com.github.rfmejia.spectralhash.SpectralHash
import com.github.rfmejia.spectralhash.unmarshalling.BasicStringUnmarshaller
import scala.util.Try

/**
 *  Generates a spectral hash from a basic formatted string handled
 *  by (@com.github.rfmejia.spectralhash.BasicStringUnmarshaller) using a
 *  SHA-1 algorithm. See version 1 of the spectral hash specification document.
 */
trait Version1SpectralHashGenerator extends SHA1HashGenerator with BasicStringUnmarshaller {
  val version = 1

  def generateSpectralHash(input: String, prefix: Option[String] = None): Try[SpectralHash] = unmarshal(input) flatMap (peaks => buildHash(peaks, prefix))

  def encodePeaks(peaks: Seq[(Double, Double)]): String =
    peaks.map {
      case (ion, intensity) => f"$ion%1.4f:$intensity%1.4f"
    } mkString (" ")
}

object Version1SpectralHashGenerator extends Version1SpectralHashGenerator
