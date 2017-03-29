(defproject ephemeris-api "0.0.1-SNAPSHOT"
  :description "Ephemeris HTTP API"
  :min-lein-version  "2.4.0" ;; due to lein-immutant
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [ephemeris "0.0.1"]
                 [prismatic/schema "1.1.4"]
                 [pedestal-api "0.3.1" :exclusions [prismatic/schema]]
                 [io.pedestal/pedestal.service "0.5.2"]
                 [io.pedestal/pedestal.jetty "0.5.2"] ;; for dev
                 [io.pedestal/pedestal.immutant "0.5.2"] ;; for prod
                 [ch.qos.logback/logback-classic "1.2.2" :exclusions [org.slf4j/slf4j-api]]
                 [org.slf4j/jul-to-slf4j "1.7.25"]
                 [org.slf4j/jcl-over-slf4j "1.7.25"]
                 [org.slf4j/log4j-over-slf4j "1.7.25"]
                 [org.clojure/tools.logging "0.3.1"]
                 [jarohen/nomad "0.7.3"]
                 [environ "1.1.0"]
                 [stencil "0.5.0"]]
  :plugins [[lein-environ "1.1.0"]]
  :env {:ever :project/version ;; ephemeris-api v[ersion]
        :base "/api/"} ;; keep the trailing /
  :source-paths ["src"]
  :resource-paths ["resources"]
  :profiles
    {:dev {:source-paths ["dev" "src"]
           :jvm-opts ["-Dnomad.env=dev"]
           :dependencies [[ns-tracker "0.3.1"]
                          [proto-repl "0.3.1"]
                          [martian-test "0.1.4"]
                          [midje "1.8.3"]
                          [midje-notifier "0.2.0"]]
           :plugins [[lein-midje "3.2"]
                     [lein-ancient "0.6.10"]
                     [lein-immutant "2.1.0"]]
           :immutant {:nrepl-port 0
                      :nrepl-interface :management}
           :repl-options {:timeout 150000 ;; 2 & 1/2 minutes
                          :init-ns dev}}
     :repl
       {:ultra {:repl {:sort-keys false
                       :map-coll-separator :line}}}
     :uberjar {:main ephemeris-api.server :aot :all}}
  :immutant {:init pedestal-immutant.server/initialize
             :resolve-dependencies true
             :context-path "/"
             :war {:name "ephemeris-api"}}
  :aliases {"test" ["midje"]
            "autotest" ["midje" ":autotest"]
            "uberwar" ["immutant" "war"]}
  :main ^:skip-aot ephemeris-api.server
  :target-path "target/"
  :pom-location "target/"
  :uberjar-name "server.jar"
  :deploy-branches ["master"])
