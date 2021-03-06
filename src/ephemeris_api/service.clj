(ns ephemeris-api.service
  (:require [io.pedestal.http :as bootstrap]
            [io.pedestal.http.route.definition :refer [defroutes]]
            [io.pedestal.interceptor.helpers :refer [handler]]
            [io.pedestal.interceptor :refer [interceptor]]
            [ring.util.response :refer [response not-found created resource-response content-type status redirect]]
            [stencil.core :refer [render-file]]
            [schema.core :as s]
            [pedestal-api
             [core :as api]
             [helpers :refer [defhandler]]]
            [ephemeris-api.config :refer [config]]
            [ephemeris.core :refer (calc)]))

(def cfg (config))
(stencil.loader/set-cache (clojure.core.cache/ttl-cache-factory {} :ttl 0))

(s/defschema Points
  {:points {s/Keyword {:lon s/Num
                       :lat s/Num
                       :sdd s/Num}}})

(defhandler mundane
 {:summary   "Get the current mundane planetary positions."
  :parameters {}
  :responses {200 {:body Points}}}
 [request]
 {:status 200
  :body (calc {:angles []
               :houses false
               :meta false})})

(s/with-fn-validation ;; Optional, though nice to have at compile time
  (api/defroutes api-routes
    {:info {:title       "Ephemeris API"
            :description "For Astrology Applications"
            :version     (get cfg :ever)}
     :externalDocs {:description "Automatically deployed from the master branch on GitHub. Make it your own. Unlicensed Open Source. Follow the link for more info..."
                    :url         "https://github.com/astrolin/ephemeris-api#ephemeris-api"}
     :basePath "/"}
    [[[(get cfg :base) ^:interceptors
              [api/error-responses
               (api/negotiate-response)
               (api/body-params)
               api/common-body
               (api/coerce-request)
               (api/validate-response)]
        ["/now" {:get mundane}]
        ["/swagger.json" {:get [api/swagger-json]}]
        ["/*resource" {:get [api/swagger-ui]}]]]]))

(def home
  (handler
   ::home-handler
   (fn [request]
    (-> (response (render-file "pages/index.html" {:base (get cfg :base)}))
        (content-type "text/html")))))

(defroutes app-routes
  [[["/*route" {:get home}]]])

(def routes
  (concat api-routes app-routes))

(def service
  {:env :prod
   ::bootstrap/routes routes
   ::bootstrap/router :linear-search
   ::bootstrap/resource-path "/public"
   ::bootstrap/secure-headers {:content-security-policy-settings
                               {:script-src "'self' 'unsafe-inline' 'unsafe-eval'"}}})
