FROM java:8

ADD target/uberjar/server.jar /srv/ephemeris-api.jar

EXPOSE 8080

CMD ["java", "-Dnomad.env=prod", "-jar", "/srv/ephemeris-api.jar"]
