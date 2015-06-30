package com.github.rfmejia.spectralhash.java.generators

import com.github.rfmejia.spectralhash
import com.github.rfmejia.spectralhash.SpectralHash
import scala.util.Try

/**
 * Contains convenience methods for use in Java.
 */
object Version1SpectralHashGenerator extends spectralhash.generators.Version1SpectralHashGenerator {
  def generateSpectralHash(input: String, prefix: String): Try[SpectralHash] =
    spectralhash.generators.Version1SpectralHashGenerator.generateSpectralHash(input, Option(prefix))

  def generateSpectralHash(input: String): Try[SpectralHash] =
    spectralhash.generators.Version1SpectralHashGenerator.generateSpectralHash(input, None)

}
