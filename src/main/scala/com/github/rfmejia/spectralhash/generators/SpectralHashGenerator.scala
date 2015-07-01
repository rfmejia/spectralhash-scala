package com.github.rfmejia.spectralhash.generators

import com.github.rfmejia.spectralhash.hashing.PeakHasher
import com.github.rfmejia.spectralhash.SpectralHash
import scala.util.Try

/**
 *  Base trait for spectral hash generators. Implementing classes should supply a concrete
 *  implemention of @com.github.rfmejia.spectralhash.hashing.PeakHasher.encodePeaks().
 *  Also, functions for generating the hash should be supplied for every unmarshaller extended.
 *
 * <pre>
 * {@code
 *    def generateSpectralHash(input: T, prefix: Option[String): Try[SpectralHash] =
 *      unmarshal(input) flatMap (peaks => buildHash(peaks, prefix))
 * }
 * </pre>
 */
trait SpectralHashGenerator extends PeakHasher {
  val version: Int

  def buildHash(peaks: Seq[(Double, Double)], prefix: Option[String]): Try[SpectralHash] =
    hashPeaks(peaks).map(hash => SpectralHash(version, hash, prefix))
}

