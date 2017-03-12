(defproject ephemeris-api "0.0.1-SNAPSHOT"
  :description "Ephemeris HTTP API"
  :min-lein-version  "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ephemeris "0.0.1"]
                 [metosin/compojure-api "1.1.10"]
                 [pedestal-api "0.3.1" :exclusions [prismatic/schema]]
                 [prismatic/schema "1.1.3"]
                 [io.pedestal/pedestal.service "0.5.2"]
                 [io.pedestal/pedestal.jetty "0.5.2"]
                 [ch.qos.logback/logback-classic "1.1.8" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.22"]
                 [org.slf4j/jcl-over-slf4j "1.7.22"]
                 [org.slf4j/log4j-over-slf4j "1.7.22"]
                 [org.clojure/tools.logging "0.3.1"]]
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
           :repl-options {:timeout 90000 ;; 90 seconds
                          :init-ns ephemeris-api.server}}
     :repl
       {:ultra {:repl {:sort-keys false
                       :map-coll-separator :line}}}
     :uberjar {:aot :all}}
  :aliases {"test" ["midje"]
            "autotest" ["midje" ":autotest"]}
  :main ^:skip-aot ephemeris-api.server
  :target-path "target/%s"
  :pom-location "target/"
  :uberjar-name "server.jar"
  :deploy-branches ["master"])
