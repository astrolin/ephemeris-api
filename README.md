# ephemeris-api

> Ephemeris HTTP API

## Use

### Development

Start `lein repl`, load the `dev` namespace, and call `-main`.
Source code will be auto-reloaded upon changes for easier dev,
thanks to [ns-tracker](https://github.com/weavejester/ns-tracker).

```clojure
user> (dev)
dev> (-main)
```

### Standalone Package

```
lein do clean, uberjar
java -Dnomad.env=dev -jar target/uberjar/server.jar
```

## License

[Unlicensed](http://unlicense.org) free and unencumbered public domain software.

[Ephemeris LICENSE](https://github.com/astrolet/ephemeris/blob/active/LICENSE)
applies in order to use the api - which currently means:
[Swiss Ephemeris License](http://www.astro.com/swisseph).
