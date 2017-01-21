# ephemeris-api

> Ephemeris HTTP API

## Use

### Run the app locally

`lein ring server`

### Run the tests

`lein test`

### Packaging and running as standalone jar

```
lein do clean, ring uberjar
java -jar target/server.jar
```

### Packaging as war

`lein ring uberwar`

## License

[Unlicensed](http://unlicense.org) free and unencumbered public domain software.

[Ephemeris LICENSE](https://github.com/astrolet/ephemeris/blob/active/LICENSE)
applies in order to use the api - which currently means:
[Swiss Ephemeris License](http://www.astro.com/swisseph).
