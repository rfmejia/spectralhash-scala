package com.github.rfmejia.spectralhash.generators

import com.github.rfmejia.spectralhash.SpectralHash
import java.security.MessageDigest
import scala.util.Try

/** Base trait for spectral hash generators */
trait SpectralHashGenerator {
  val version: Int

  /** Hash algorithm name supported by @java.security.MessageDigest. */
  val hashAlgorithm: String

  def buildHash(peaks: Seq[(Double, Double)]): Try[SpectralHash] =
    hashPeaks(peaks).map(hash => SpectralHash(version, hash))

  /** Convert a sequence of peaks into a hash byte string. */
  def hashPeaks(peaks: Seq[(Double, Double)]): Try[Array[Byte]]

  /** Convert a sequence of peaks into a formatted string prior to hashing. */
  def encodePeaks(peaks: Seq[(Double, Double)]): String
}

/** Generates a SHA1 hash from a sequence of peak pairs using the standard encoding rules from version 1 of the spectral hash specification document. */
trait SHA1HashGenerator extends SpectralHashGenerator {
  val version = 1
  val hashAlgorithm = "SHA1"

  def hashPeaks(peaks: Seq[(Double, Double)]): Try[Array[Byte]] = Try {
    val md = MessageDigest.getInstance(hashAlgorithm)
    md.update(encodePeaks(peaks).getBytes())
    md.digest()
  }
}

