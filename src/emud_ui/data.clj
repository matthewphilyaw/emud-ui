(ns emud-ui.data
  (:use [cheshire.core]))

(defn encode-message [input]
  ;; needs to be delimted by new line. 	
  (str (generate-string {:command input}) "\n"))

(defn decode-message [msg]
  (:text (parse-string msg true)))
