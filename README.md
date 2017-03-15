# ephemeris-api

> Ephemeris HTTP API

This `ephemeris-api` provides [ephemeris](https://github.com/astrolet/ephemeris) functionality, over the HTTP protocol, as a web service.  It is powered by [Pedestal](http://pedestal.io), including adaptors for [Jetty](http://www.eclipse.org/jetty) and [Immutant](http://immutant.org).  Pedestal enables a "huge variety" of deployment possibilities, not all of which documented here.

## Rationale

For the purpose of decoupling astrology applications from ephemeris code.  Therefore one is free to implement an application in any programming language and using a data format of their choice.  There is also the possibility of including and exposing alternative ephemeris implementations with the same interface.  Client applications would simply connect to an API instance configured to serve the ephemeris they want to use / make available to their customers.  Application users with privacy or other concerns (e.g. self-service of low connectivity environments wanting Internet-independent use through local area network, or a fully-offline desktop `localhost`), can easily provide the ephemeris for themselves and for free, as long as their application allows for such configuration of an ephemeris endpoint.

## Use

### Development

Start `lein repl`, load the `dev` namespace, and call `-main`.
Source code will be auto-reloaded upon changes for easier dev,
thanks to [ns-tracker](https://github.com/weavejester/ns-tracker).

Running `lein repl` will take you to `ns dev` by default -- then:

```clojure
(-main)
```

### Standalone Package

```sh
lein do clean, uberjar
java -Dnomad.env=dev -jar target/uberjar/server.jar
```

## License

[Unlicensed](http://unlicense.org) free and unencumbered public domain software.

[Ephemeris LICENSE](https://github.com/astrolet/ephemeris/blob/active/LICENSE)
applies in order to use the api - which currently means:
[Swiss Ephemeris License](http://www.astro.com/swisseph).
