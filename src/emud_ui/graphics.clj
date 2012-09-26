(ns emud-ui.graphics
  (:use emud-ui.text)
  (:require [lanterna.screen :as s]))

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

;; TODO - add a section in top border for displayin curent server
;; TODO - To cope with resizing possibly look into refactoring
(defn draw-border [dev]
  (let [[c r] (s/get-size dev)]
    ;; position cursor
    (s/move-cursor dev 5 (- r 2))
    (s/put-string dev 2 (- r 2) "=>")
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
    (s/put-string dev 4 0 "EMUD")
    (s/redraw dev)))

;; TODO - Need to handle a print-scr from different thread
(defn print-scr [dev c index rows]
  (when (seq rows)
    ;; clear the line first before drawing the next.
    (s/put-string dev c index (apply str (repeat (- ((s/get-size dev) 0) 5) " ")))
    (s/put-string dev c index (apply str (first rows)))
    (recur dev c (inc index) (rest rows))))

;; TODO - possibly look into scrolling the message pane with a key combo like :shift :up
(defn get-input [dev c buff]
  (let [inp (s/get-key-blocking dev)
        row (- ((s/get-size dev) 1) 2)]
    (if (= :enter inp)
      (do
        (s/put-string dev 5 row (apply str (repeat (- c 5) " ")))
        (s/move-cursor dev 5 row)
        (apply str buff))
      (if (keyword inp)
        (if (and (= :backspace inp) (> c 5))
          (do
            (let [new-pos (dec c)]
              (s/put-string dev new-pos row " ")
              (s/move-cursor dev new-pos row)
              (s/redraw dev)
              (get-input dev new-pos (pop buff))))
          (get-input dev c buff))
        (if (< c (- ((s/get-size dev) 0) 3))
          (do
            (s/put-string dev c row (str inp))
            (s/move-cursor dev (inc c) row)
            (s/redraw dev)
            (get-input dev (inc c) (conj buff inp)))
          (get-input dev c buff))))))

;; TODO - Add means of stoping repl and closing program
;; TODO - Need to create line buffer as a reference to be used with the threads and then call create-buffer with it
(defn repl-it [dev line-buff]
  (let [x (get-input dev 5 [])
        draw-buff (concat (reverse (create-buffer (- ((s/get-size dev) 0) 30) x)) line-buff)]
    ;;(prn draw-buff)
    (print-scr dev 2 1 (reverse (take (- ((s/get-size dev) 1) 4) draw-buff)))
    (s/redraw dev)
    (recur dev draw-buff)))

;; TODO - look into resizing options
;; TODO - modify to receive ip/port combo that can be used for displaying current server
(defn start-ui [con]
  (let [scr (s/get-screen :auto)]
   (s/start scr)
   (s/clear scr)
   (s/redraw scr)
   (draw-border scr)
   (repl-it scr '())
   (s/stop scr)))
