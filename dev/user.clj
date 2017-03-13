(ns user
  (:require [clojure.tools.namespace.repl :as repl]))

(def refresh repl/refresh)

(defn dev-main []
  (require 'dev)
  (in-ns 'dev)
  (dev/-main))
