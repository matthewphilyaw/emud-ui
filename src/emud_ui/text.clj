(ns emud-ui.text
  (:use [clojure.string :only [split-lines]]))

(defn part-by-line [l text]
  (filter #(not (empty? %))
           (map #(partition-all l %)
                 (split-lines text))))

(defn create-buffer
  [r text]
  (apply concat (part-by-line r text)))
