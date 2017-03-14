FROM java:8

ADD target/uberjar/server.jar /srv/ephemeris-api.jar

EXPOSE 8080

# These logging modules only apply to :jetty deployments,
# using io.pedestal/pedestal.jetty adapter
# which may or may not be the case...
CMD ["mkdir", "/srv/modules"]
CMD ["cd", "/srv/modules"]
CMD ["curl" "-O" "https://raw.githubusercontent.com/jetty-project/logging-modules/master/capture-all/logging.mod"]
CMD ["curl" "-O" "https://raw.githubusercontent.com/jetty-project/logging-modules/master/centralized/webapp-logging.mod"]

CMD ["java", "-Dnomad.env=prod", "-jar", "/srv/ephemeris-api.jar", "--add-to-start=logging,webapp-logging"]
