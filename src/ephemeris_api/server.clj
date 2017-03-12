(ns ephemeris-api.server
  (:require [ephemeris-api.service :as service]
            [io.pedestal.http :as bootstrap])
  (:gen-class))

(defonce service-instance nil)

(defn run-dev
  "The entry-point for 'lein run-dev'"
  [& args]
  (println "\nCreating your [DEV] server...")
  (-> service/service ;; start with :prod config
      (merge {:env :dev ;; override with :dev
              ;; do not block thread that starts web server - is this for dev or prod?
              ::bootstrap/join? false
              ;; all origins are allowed in dev mode
              ::bootstrap/allowed-origins {:creds true :allowed-origins (constantly true)}})
      ;; Wire up interceptor chains
      bootstrap/default-interceptors
      bootstrap/dev-interceptors
      bootstrap/create-server
      bootstrap/start))

(defn create-server []
  (alter-var-root #'service-instance
                  (constantly (bootstrap/create-server
                               (-> service/service
                                   (assoc ::bootstrap/port (Integer. (or (System/getenv "PORT") 8081)))
                                   (bootstrap/default-interceptors))))))

(defn start []
  (when-not service-instance
    (create-server))
  (bootstrap/start service-instance))

(defn stop []
  (when service-instance
    (bootstrap/stop service-instance)))

(defn -main [& args]
  (start))
