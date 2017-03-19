(ns ephemeris-api.service
  (:require [io.pedestal.http :as bootstrap]
            [io.pedestal.interceptor.helpers :refer [before handler]]
            [schema.core :as s]
            [pedestal-api.core :as api]
            [ephemeris-api.config :refer [config]]
            [ephemeris.core :refer (calc)]))

(s/defschema Points
  {:points {s/Keyword {:lon s/Num
                       :lat s/Num
                       :sdd s/Num}}})

(def mundane
  (api/annotate
   {:summary   "Get the current mundane planetary positions"
    :responses {200 {:body Points}}}
   (handler ::mundane
     (fn [request]
       {:status 200
        :body (calc {:angles []
                     :houses false
                     :meta false})}))))

;; (s/with-fn-validation) ;; Optional, but nice to have at compile time
(api/defroutes routes
  {:info {:title       "Ephemeris Api"
          :description "For Astrology Applications"
          :version     "0.1"
          :externalDocs {:description "Find out more"
                         :url         "https://github.com/astrolet/ephemeris-api"}}}
  [[[(get (config) :base) ^:interceptors
            [api/error-responses
             (api/negotiate-response)
             (api/body-params)
             api/common-body
             (api/coerce-request)
             (api/validate-response)]
      ["/now" {:get mundane}]
      ["/swagger.json" {:get api/swagger-json}]
      ["/*resource" {:get api/swagger-ui}]]]])

(def service
  {:env :prod
   ::bootstrap/routes routes
   ::bootstrap/router :linear-search
   ::bootstrap/resource-path "/public"})
