package com.github.rfmejia.spectralhash

import com.github.rfmejia.spectralhash.generators.Version1SpectralHashGenerator
import com.github.rfmejia.spectralhash.unmarshalling.BasicStringUnmarshaller
import org.scalatest._
import org.scalatest.Inside.inside
import scala.util.{ Try, Success, Failure }

class SpectralHashVersion1Spec extends FlatSpec with Matchers with Version1SpectralHashGenerator {
  val sample1 = """
    124.1 31739
    125.1 2905
    129.1 2850
    131.1 49572
    132.1 4865
    133.1 81554
    144.1 1940
    145.1 41441
  """

  val sample2 = """
    100.0001 1000.0000
    101.0000 500.0000
  """

  val sample1Peaks = Seq(
    (124.1, 31739),
    (125.1, 2905),
    (129.1, 2850),
    (131.1, 49572),
    (132.1, 4865),
    (133.1, 81554),
    (144.1, 1940),
    (145.1, 41441)
  )

  "BasicStringUnmarshaller" should "parse according to the spec" in {
    val unmarshalled = BasicStringUnmarshaller.unmarshal(sample1)
    inside(unmarshalled) {
      case Success(peaks) =>
        peaks.length shouldBe 8
        peaks.map(_._1) shouldBe (sample1Peaks.map(_._1))
    }
  }

  "BasicStringUnmarshaller" should "convert absolute intensity to relative" in {
    val unmarshalled = BasicStringUnmarshaller.unmarshal(sample1)
    inside(unmarshalled) {
      case Success(peaks) =>
        val max = sample1Peaks.map(_._2).max
        val relativeIntensities = sample1Peaks.map(_._2).map(_.toDouble / max * 100)
        peaks.map(_._2) shouldEqual relativeIntensities
    }
  }

  "BasicStringUnmarshaller" should "correctly format peaks" in {
    val unmarshalled = BasicStringUnmarshaller.unmarshal(sample2)
    inside(unmarshalled) {
      case Success(peaks) =>
        encodePeaks(peaks) shouldBe "100.0001:100.0000 101.0000:50.0000"
    }
  }

  "The hash generator" should "generate the correct hash according to version 1 of the spec" in {
    inside(generateSpectralHash(sample2)) {
      case Success(hash) =>
        hash.toString shouldEqual "0694872332ebf2149cfc461b6fcdd2b4d75048c4-1"
    }

    inside(generateSpectralHash(sample2, prefix = Some("source"))) {
      case Success(hash) =>
        hash.toString shouldEqual "source-0694872332ebf2149cfc461b6fcdd2b4d75048c4-1"
    }

  }
}

