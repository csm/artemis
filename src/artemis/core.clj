(ns artemis.core
  (:require [clojure.tools.cli :as cli]
            [compojure.api.sweet :as r]
            [compojure.handler :refer [site]]
            [immutant.web :as web]
            [ring.middleware.reload :as reload]
            [ring.swagger.json-schema :as rjs]
            [schema.core :as s])
  (:gen-class))

(defn greet
  ([] (greet "World"))
  ([who] (str "Hello, " who "!")))

(s/defschema Greeting {:result (rjs/field s/Str {:description "The greeting"})})

(r/defapi app
  {:swagger {:spec "/swagger.json"
             :ui "/api-docs"
             :data {:info {:version "0.1.0-SNAPSHOT"
                           :title "Artemis"
                           :description "Test server."}}}}
  (r/context "/api/v1" []
             (r/GET "/hello" []
                    :summary "Greet the world"
                    :description "Greets the world."
                    :return Greeting
                    {:status 200
                     :body {:result (greet)}})

             (r/GET "/hello/:name" [name]
                    :summary "Greet someone"
                    :description "Greets somebody. Pass the name of the person to greet in the URL."
                    :return Greeting
                    :path-params [name :- String]
                    {:status 200
                     :body {:result (greet name)}})

             (r/POST "/hello" [])))

(def options
  [["-d" "--dev" "Run in dev mode (reloads code on changes)."]
   ["-h" "--help"]])

(def handler (site app))

(defn -main
  "Runs the server."
  [& args]
  (let [options (cli/parse-opts args options)
        _ (cond
            (-> options :options :help)
            (do
              (println (str "Usage: artemis [options]\n\n" (:summary options)))
              (System/exit 0))
            (not (empty? (:errors options)))
            (do
              (doall
               (for [err (:errors options)]
                 (println err)))
              (System/exit 1))
            :else {})
        handler (if (-> options :options :dev)
                  (reload/wrap-reload (site #'app))
                  (site app))]
    (web/run handler {:port 8888})))
