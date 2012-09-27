(ns emud-ui.text
  (:use [clojure.string :only [split-lines]]))

(defn part-by-line [l text]
  (filter #(not (empty? %))
           (map #(partition-all l %)
                (split-lines text))))

(defn create-buffer
  [r text]
  (apply concat (part-by-line r text)))

;; TODO - Implement max history mechanism. Possibly take a that as a parameter, and (take max-history alteredBuffer) to implement it.
(defn alt-buffer [buffer max-columns text]
  (dosync (alter buffer (fn [_] (concat (reverse (create-buffer max-columns text)) @buffer))))
  buffer)

