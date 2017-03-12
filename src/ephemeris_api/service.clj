(ns ephemeris-api.service
  (:require [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route :as route]
            [io.pedestal.interceptor :refer [interceptor]]
            [pedestal-api
             [core :as api]
             [helpers :as helps]]
            [schema.core :as s]
            [ephemeris.core :refer (calc)]))

(s/defschema Points
  {:points {s/Keyword {:lon s/Num
                       :lat s/Num
                       :sdd s/Num}}})

(def mundane
  (api/annotate
   {:summary     "Get the current mundane planetary positions"
    :responses   {200 {:body Points}}}
   (interceptor
    {:name  ::mundane
     :enter (fn [ctx]
              (assoc ctx :response
                     {:status 200
                      :body (calc {:angles []
                                   :houses false
                                   :meta false})}))})))

;;(s/with-fn-validation)
(api/defroutes routes
  {:info {:title       "Ephemeris Api"
          :description "For Astrology Applications"
          :version     "0.1"}
   :tags [{:name         "ephemeris"
           :description  "calc"
           :externalDocs {:description "Find out more"
                          :url         "http://swagger.io"}}]}
  [[["/" ^:interceptors [api/error-responses
                         (api/negotiate-response)
                         (api/body-params)
                         api/common-body
                         (api/coerce-request)]
                         ;;(api/validate-response)]
     ["/ephemeris" ^:interceptors [(api/doc {:tags ["ephemeris"]})]
      ["/utc" {:get mundane}]]]
    ["/swagger.json" {:get api/swagger-json}]
    ["/*resource" {:get api/swagger-ui}]]])

(def service
  {:env                      :prod
   ;; Routes can be a function that resolves routes,
   ;; we can use this to make the routes reloadable
   ::bootstrap/routes        #(deref #'routes)
   ;; linear-search, and declaring the swagger-ui handler last in the routes,
   ;; is important to avoid the splat param for the UI matching API routes
   ::bootstrap/router        :linear-search
   ::bootstrap/resource-path "/public"
   ::bootstrap/type          :jetty
   ::bootstrap/port          8081})
