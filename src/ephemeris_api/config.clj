(ns ephemeris-api.config
  (:require [nomad :refer [defconfig]]
            [clojure.java.io :as io]
            [environ.core :refer [env]]))

(defconfig base (io/resource "config.edn"))

(defn configurable [keys val associable]
  (if (nil? val)
    associable
    (assoc-in associable keys val)))

(defn config []
  (->> (base)
    (configurable [:type]
                  (keyword (env :ephemeris-api-type)))
    (configurable [:http :host] (env :ephemeris-api-host))
    (configurable [:http :port]
                  (Integer. (env :ephemeris-api-port)))))
