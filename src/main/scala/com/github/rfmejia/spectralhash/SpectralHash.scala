package com.github.rfmejia.spectralhash

trait HashStringOps {
  val HEX_ARRAY: Array[Char] = "0123456789abcdef".toCharArray

  /** Converts a byte array into a hex byte string. */
  def bytesToHex(bytes: Array[Byte]): String = {
    val s: List[Char] = bytes.foldLeft(List.empty[Char]) {
      (hash, byte) =>
        val v = byte & 0xFF
        hash :+ HEX_ARRAY(v >>> 4) :+ HEX_ARRAY(v & 0x0F)
    }
    s.mkString
  }
}

case class SpectralHash(version: Int, hash: Array[Byte]) extends HashStringOps {
  override val toString = s"v${version}-${bytesToHex(hash)}"
}

