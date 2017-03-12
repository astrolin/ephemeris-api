(ns ephemeris-api.handler
  (:require [ephemeris.core :refer (calc)]
            [compojure.api.sweet :refer :all]
            [ring.util.http-response :refer :all]))

(def app
  (api
    {:swagger
     {:ui "/"
      :spec "/swagger.json"
      :data {:info {:title "Ephemeris Api"
                    :description "For Astrology Applications"}
             :tags [{:name "ephemeris"
                     :description "calc"
                     :externalDocs {:description "Find out more"
                                    :url         "http://swagger.io"}}]}}}

    (context "/" []
      :tags ["ephemeris"]

      (GET "/utc" []
        :summary "points at the universal moment"
        (ok (calc {:angles [] :houses false}))))))
