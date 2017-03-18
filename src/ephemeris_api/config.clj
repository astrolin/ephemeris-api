(ns ephemeris-api.config
  (:require [nomad :refer [defconfig]]
            [clojure.java.io :as io]
            [environ.core :refer [env]]))

(defconfig base (io/resource "config.edn"))

(defn config []
  (assoc-in (base) [:http :port] (env :ephemeris-api-port)))
