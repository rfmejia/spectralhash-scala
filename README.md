**Note:** This implementation has been discontinued. Wrappers to the official Java library are maintained in [berlinguyinca/spectra-hash](https://github.com/berlinguyinca/spectra-hash).

*spectralhash-scala* is an implementation of the spectral hash specification (curr. draft version 1) in Scala. It is a modular library with simple immutable models for efficient use and extensibility.

The output hash will be in the format `<hash value>-<version #>` or `<prefix>-<hash value>-<version #>`.

### Installation

*spectralhash-scala* is available as an [Scala Build Tool (sbt)](http://www.scala-sbt.org/) project. Clone the repository and run `sbt assembly` to generate a JAR file. You can choose between the following options:

* Package JAR - run `sbt package` to generate `target/spectralhash-scala-*.jar`, or
* Standalone JAR - run `sbt assembly` to generate `target/spectralhash-scala-assembly-*.jar`, which contains all dependencies (including Scala classes).

*Note: [Typesafe Activator](http://www.typesafe.com/activator) can also be used in place of sbt.

### Basic Usage

The simplest way to use the library in Scala:

```scala
import com.github.rfmejia.spectralhash._
import com.github.rfmejia.spectralhash.generators.Version1SpectralHashGenerator
import scala.util.{Try, Success, Failure}

val peakPairs: String = /* ... */

// 1. Use directly as an object
val parseResult = Version1SpectralHashGenerator.generateSpectralHash(peakPairs)
  // or Version1SpectralHashGenerator.generateSpectralHash(peakPairs, Some("prefix"))
parseResult match {
  case Success(hash) => // Use hash value
  case Failure(err) => // Handle parsing errors
}

// 2. Mixin trait to your class
class MyApp with Version1SpectralHashGenerator {
  val parseResult = generateSpectralHash(peakPairs)
  ...
}
```

In Java:

```java
import com.github.rfmejia.spectralhash.SpectralHash;
import com.github.rfmejia.spectralhash.java.generators.Version1SpectralHashGenerator;
import scala.util.Try;

String peakPairs = /* ... */;
Try<SpectralHash> parseResult = 
  Version1SpectralHashGenerator.generateSpectralHash(peakPairs);
  // or Version1SpectralHashGenerator.generateSpectralHash(peakPairs, "prefix");
if (parseResult.isSuccess()) // Use hash value
else // Handle parsing errors
```

*Note: When using a package JAR, include `$SCALA_HOME/lib/scala-library.jar` in your classpath.

### Advanced usage

To create a custom spectral hasher, implement a class or object that extends `generators.SpectralHashGenerator` with a hashing algorithm `hashing.PeakHasher` implementation and an input unmarshaller `unmarshalling.SpectrumUnmarshaller`.

The library provides the following implementations:

- Hashing: `hashing.MD5PeakHasher`, `hashing.SHA1HashGenerator` and `hashing.SHA256PeakHasher`. 
- Unmarshalling: `unmarshalling.BasicStringUnmarshaller`

#### Custom unmarshallers

Unmarshallers convert an input of type `T` into a sequence of peak pairs, if possible. To create a unmarshaller, extend the trait `unmarshalling.SpectrumUnmarshaller` and implement the function

  `unmarshal(input: T): Try[Seq[(Double, Double)]]`

For example,

```scala
trait JsonUnmarshaller extends SpectrumUnmarshaller[JsArray] {
  def unmarshal(input: JsArray) = Try {
    /* Convert JSON array into Seq[(Double, Double)] */
  }
}

object MySpectralHasher extends SHA1HashGenerator with JsonStringUnmarshaller { ... }
```

