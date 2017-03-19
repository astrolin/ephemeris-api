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

;; deployment adapter type
(defn dadapt []
  (let [override (nna (env :ephemeris-api-type) keyword)]
    (if (nil? override)
      (get (base) :type)
      override)))

(defn config []
  (->> (merge {:base "/api"} ;; the hardcoded defaults
              (base) ;; the per-environment configured
              {:type (dadapt)})
    (configurable [:http :host]
                  (env :ephemeris-api-host))
    (configurable [:http :port]
                  (if (= (dadapt) :jetty) ;; jetty demands int port
                    (nna (env :ephemeris-api-port) to-int)
                    (env :ephemeris-api-port)))
    (configurable [:base]
                  (env :ephemeris-api-base))))
