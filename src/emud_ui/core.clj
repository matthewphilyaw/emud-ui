(ns emud-ui.core
  (:use [clojure.tools.cli :only (cli)]
        emud-ui.data
        emud-ui.net)
  (:gen-class :main true))

(defn repl [conn]
  (println (decode-message (read-from conn))) ;; blocking call
  (flush )
  (print "=> ")
  (flush )
  (let [line (read-line)]
    (if (and (not (= "exit" line)) (not (nil? conn)))
      (do
        (write-to conn (encode-message line))
        (recur conn)))))

(defn -main [& args]
  (let [[opts args banner]
    (cli args
      ["-h" "--help" "Show help" :flag true :default false]
      ["-n" "--server-name" "Server name" :default "localhost"]
      ["-p" "--port" "Port number" :default 14000])]
    (when (:help opts)
      (println banner)
      (System/exit 0))
    (let [s (:server-name opts)
          p (Integer. (:port opts))]
      (if 
        (and s p
          (do
            (println " - Type 'exit' to end program.")
            (repl (connect {:name s :port p}))))
        (prn banner)))))