(ns emud-ui.data
  (:use [cheshire.core]))

(defn encode-message [input]
  (generate-string {:command input :message ""}))

(defn decode-message [msg]
  (:message (parse-string msg true)))