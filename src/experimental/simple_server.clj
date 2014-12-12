(ns experimental.simple-server
  (:require [org.httpkit.server :as hk]))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "hello HTTP!"})

(defonce stop-server-lambda (atom nil))

(defn stop-server []
  (when-not (nil? @stop-server-lambda)
    (@stop-server-lambda :timeout 1000)
    (reset! stop-server-lambda nil)))

(defn -main [& args]
  (reset! stop-server-lambda (hk/run-server app {:port 8080})))
