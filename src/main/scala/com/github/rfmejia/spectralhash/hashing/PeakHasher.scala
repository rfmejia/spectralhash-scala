package com.github.rfmejia.spectralhash.hashing

import java.security.MessageDigest
import scala.util.Try

trait PeakHasher {
  /** Hash algorithm name supported by @java.security.MessageDigest. */
  val hashAlgorithm: String

  /** Convert a sequence of peaks into a hash byte string. */
  def hashPeaks(peaks: Seq[(Double, Double)]): Try[Array[Byte]] = Try {
    val md = MessageDigest.getInstance(hashAlgorithm)
    md.update(encodePeaks(peaks).getBytes())
    md.digest()
  }

  /** Convert a sequence of peaks into a formatted string prior to hashing. */
  def encodePeaks(peaks: Seq[(Double, Double)]): String
}

/** Generates a SHA1 hash from a sequence of peak pairs using the standard encoding rules from version 1 of the spectral hash specification document. */
trait SHA1PeakHasher extends PeakHasher {
  val hashAlgorithm = "SHA-1"
}

trait SHA256PeakHasher extends PeakHasher {
  val hashAlgorithm = "SHA-256"
}

trait MD5PeakHasher extends PeakHasher {
  val hashAlgorithm = "MD5"
}

