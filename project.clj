(defproject ephemeris-api "0.1.0-SNAPSHOT"
  :description "Ephemeris HTTP API"
  :min-lein-version  "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ephemeris "0.0.1"]
                 [metosin/compojure-api "1.1.10"]]
  :ring {:handler ephemeris-api.handler/app}
  :profiles
    {:dev {:dependencies [[javax.servlet/javax.servlet-api "3.1.0"]
                          [cheshire "5.5.0"]
                          [ring/ring-mock "0.3.0"]
                          [proto-repl "0.3.1"]
                          [midje "1.8.3"]
                          [midje-notifier "0.2.0"]]
           :plugins [[lein-ring "0.10.0"]
                     [lein-midje "3.2"]]
           :repl-options {:init-ns ephemeris-api.handler}}
     :repl
       {:ultra {:repl {:sort-keys false
                       :map-coll-separator :line}}}}
  :aliases {"test" ["midje"]
            "autotest" ["midje" ":autotest"]}
  :pom-location "target/"
  :uberjar-name "server.jar"
  :deploy-branches ["master"])
