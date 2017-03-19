(ns ephemeris-api.config
  (:require [nomad :refer [defconfig]]
            [clojure.java.io :as io]
            [environ.core :refer [env]]))

(defconfig base (io/resource "config.edn"))

(defn- configurable [keys val associable]
  (if (nil? val)
    associable
    (assoc-in associable keys val)))

;; non-nil apply fn
(defn- nna [val fun]
  (if (nil? val) nil ;; doesn't apply to nil values
    (apply fun val)))

(defn- to-int [s] (Integer. s))

(defn config []
  (->> (base)
    (configurable [:type]
                  (nna (env :ephemeris-api-type) keyword))
    (configurable [:http :host] (env :ephemeris-api-host))
    (configurable [:http :port]
                  (nna (env :ephemeris-api-port) to-int))))
