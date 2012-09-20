(ns emud-ui.net
  (:require [clojure.java.io :as cio])
  (:import (java.net Socket)))

(defn pack-msg [msg]
  (let [len (count msg)
        buf (java.nio.ByteBuffer/allocate (+ len 4))]
    (.putInt buf len)
    (.put buf (.getBytes msg))
    (.array buf)))

(defn connect [server]
  (let [socket (Socket. (:name server) (:port server))
        in (java.io.DataInputStream. (.getInputStream socket))
        out (.getOutputStream socket)
        conn (ref {:in in :out out})]
      conn))

(defn write-to [conn message]
  (.write (:out @conn) (pack-msg message)) 
  (.flush (:out @conn))
  nil)

(defn read-from [conn]
  (let [length (.readInt (:in @conn))
        buf (byte-array length)]
    (.read (:in @conn) buf, 0, length)
    (apply str (map char buf))))

