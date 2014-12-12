(ns experimental.simple-server
  (:require [org.httpkit.server :as hk]
            [org.httpkit.timer  :as timer]))

(defn hello-app [req]
  {:status  200
   :headers {"Content-Type" "text/html"}
   :body    "hello HTTP!"})

(defonce stop-server-lambda (atom nil))

(defn stop-server []
  (when-not (nil? @stop-server-lambda)
    (@stop-server-lambda :timeout 1000)
    (reset! stop-server-lambda nil)))

(defn unified-handler [req]
  (hk/with-channel req channel
    (hk/on-close channel (fn [status]
                           (println "channel closed")))
    (if (hk/websocket? channel)
      (println "WebSocket channel")
      (println "HTTP channel"))
    (hk/on-receive channel (fn [data]
                             (hk/send! channel data)))))

(defn streaming-handler [req]
  (hk/with-channel req channel
    (hk/on-close channel (fn [status]
                           (println "channel closed, " status)))
    (loop [id 0]
      (when (< id 10)
        (timer/schedule-task (* id 200)
                             (hk/send! channel (str "message from server #" id) false))
        (recur (inc id))))
    (timer/schedule-task 10000 (hk/close channel))))

(defn ws-handler [req]
  (hk/with-channel req channel
    (hk/on-close channel (fn [status]
                           (println "channel closed, " status)))
    (hk/on-receive channel (fn [data]
                             (hk/send! channel data)))))
;(defn -main [& args]
  ;(reset! stop-server-lambda (hk/run-server hello-app {:port 8080})))

;(defn -main [& args]
  ;(hk/run-server unified-handler {:port 8080}))

;(defn -main [& args]
  ;(hk/run-server streaming-handler {:port 8080}))

(defn -main [& args]
  (hk/run-server ws-handler {:port 9090}))
