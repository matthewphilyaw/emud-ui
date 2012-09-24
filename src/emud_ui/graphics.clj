(ns emud-ui.graphics
  (:require [lanterna.screen :as s]))

;; TODO take remove create-buffer
;;(defn print-scr [or-r or-c r c text sc]
;; (loop [rows (take (dec r) (create-buffer c text)) index or-r]
;;   (when (seq rows)
;;    (s/put-string sc or-c index (apply str (first rows)))

(defn d-hor [c r dev]
  (loop [index c]
    (if (not (< index 1))
      (do
        (s/put-string dev index 0 "\u2501")
        (s/put-string dev index (dec (dec (dec r))) "\u2501")
        (s/put-string dev index (dec r) "\u2501")
        (recur (dec index))))))

(defn d-ver [c r dev]
  (loop [index r]
    (if (not (< index 1))
      (do
        (s/put-string dev 0 index "\u2503")
        (s/put-string dev (dec c) index "\u2503")
        (recur (dec index))))))

(defn draw-border [dev]
  (let [[c r] (s/get-size dev)]
    ;; position cursor
    (s/move-cursor dev 2 (- r 2))
    ;; draw lines for vert/hor
    (d-hor (- c 2) r dev)
    (d-ver (- c 1) (dec (dec r)) dev)
    ;; draw corners/intersections
    (s/put-string dev 0 0 "\u250f")
    (s/put-string dev (- c 2) 0 "\u2513")
    (s/put-string dev 0 (dec r) "\u2517")
    (s/put-string dev (- c 2) (dec r) "\u251b")
    (s/put-string dev 0 (- r 3) "\u2523")
    (s/put-string dev (- c 2) (- r 3) "\u252b")
    ;; put title
    (s/put-string dev 3 0 "<")
    (s/put-string dev 8 0 ">")
    (s/put-string dev 4 0 "EMUD")))

(defn start-ui [con]
  (let [scr (s/get-screen)]
   (draw-border scr)
   (s/start scr)
   (s/get-key-blocking scr)
   (s/stop scr))) 
