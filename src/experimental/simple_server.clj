(ns experimental.simple-server
  (:require [org.httpkit.server :as hk]))

(defn app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "hello HTTP!"})

(defn -main [& args]
  (hk/run-server app {:port 8080}))