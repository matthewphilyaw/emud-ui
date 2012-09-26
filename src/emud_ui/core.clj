(ns emud-ui.core
  (:use [clojure.tools.cli :only (cli)]
        emud-ui.data
        emud-ui.net
        emud-ui.graphics
        emud-ui.text)
  (:gen-class :main true))

(defn -main [& args]
  (let [[opts args banner]
    (cli args
      ["-h" "--help" "Show help" :flag true :default false]
      ["-n" "--server-name" "Server name" :default "localhost"]
      ["-p" "--port" "Port number" :default "8081"])]
    (when (:help opts)
      (println banner)
      (System/exit 0))
    (let [s (:server-name opts)
          p (Integer. (:port opts))]
      (if (and s p)
        ;; TODO - bring in tcp lib to connect to remote server
        ;; TODO - pass in server/port combo for displaying active connection
        (start-ui nil) 
        (prn banner)))))
