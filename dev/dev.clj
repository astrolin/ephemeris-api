(ns dev
  (:require [clojure.tools.namespace.repl :as repl]
            [ns-tracker.core :as tracker]
            [io.pedestal.http :as bootstrap]
            [ephemeris-api
              [server :as server]
              [service :as service]]))

(def clear repl/clear)
(def refresh repl/refresh)

(def service (-> service/service ;; start with production configuration
                 (merge
                   {:env :dev
                    ;; do not block thread that starts web server
                    ::bootstrap/join? false
                    ;; reload routes on every request
                    ::bootstrap/routes #(deref #'service/routes)
                    ;; all origins are allowed in dev mode
                    ::bootstrap/allowed-origins (constantly true)})
                 (bootstrap/default-interceptors)
                 (bootstrap/dev-interceptors)))

(defn start [& [opts]]
  (server/create-server {:pedestal-opts (merge service opts)})
  (bootstrap/start server/service-instance))

(defn stop []
  (when server/service-instance
    (bootstrap/stop server/service-instance)))

(defn restart
  []
  (stop)
  (start))

(defn- ns-reload [track]
 (try
   (doseq [ns-sym (track)]
     (require ns-sym :reload))
   (catch Throwable e (.printStackTrace e))))

(defn watch
  ([] (watch ["src"]))
  ([src-paths]
   (let [track (tracker/ns-tracker src-paths)
         done (atom false)]
     (doto
         (Thread. (fn []
                    (while (not @done)
                      (ns-reload track)
                      (Thread/sleep 500))))
       (.setDaemon true)
       (.start))
     (fn [] (swap! done not)))))

(defn -main [& args]
  (start)
  (watch))
