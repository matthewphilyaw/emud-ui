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
      ["-p" "--port" "Port number"])]
    (when (:help opts)
      (println banner)
      (System/exit 0))
    (let [s (:server-name opts)
          p (:port opts)]
      (if (and s p)
        ;; TODO - pass in server/port combo for displaying active connection
        (let [con (try (connect {:name s :port (Integer. p)}) (catch Exception e nil))]
          (start-ui con (ref '())))
        (println banner))))
  (System/exit 0))
