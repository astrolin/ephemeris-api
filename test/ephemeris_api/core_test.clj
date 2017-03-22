(ns ephemeris-api.core-test
  (:require [midje.sweet :refer :all]
            [martian.core :as martian]
            [martian.test :as martian-test]))

(facts "API"
  (let [m (martian/bootstrap-swagger
            "http://api.astrolin.org/swagger.json"
            {:interceptors [martian-test/generate-response]})]
    (fact "now"
      (martian/response-for m :mundane) => nil)))
