(ns ephemeris-api.server
  (:gen-class) ; for -main method in uberjar
  (:require [io.pedestal.http :as bootstrap]
            [ephemeris-api
              [service :as service]
              [config :refer [config]]]))

(defonce service-instance nil)

(def cfg (config))

(defn create-server
  "Standalone dev/prod mode."
  ([] (create-server {}))
  ([{:keys [pedestal-opts]}]
   (alter-var-root
    #'service-instance
    (constantly
     (bootstrap/create-server
      (-> (merge service/service
            ;; configurable pedestal adapter :type with a default
            ;; only jetty & immutant are made available (as deps)
            {::bootstrap/type (get cfg :type :immutant)}
            {::bootstrap/host (get-in cfg [:http :host])}
            {::bootstrap/port (get-in cfg [:http :port])}
            pedestal-opts)
          (bootstrap/default-interceptors)))))))

(defn -main [& args]
  (create-server)
  (bootstrap/start service-instance))
