(ns user
  (:require [clojure.tools.namespace.repl :as repl]))

(def refresh repl/refresh)

(defn dev []
  (require 'dev)
  (in-ns 'dev)
  #_ (dev/-main))
