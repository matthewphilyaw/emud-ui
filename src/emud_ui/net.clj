(ns emud-ui.net
  (:require [clojure.java.io :as cio])
  (:import (java.net Socket)))

(declare conn-handler)

(defn connect [server]
  (let [socket (Socket. (:name server) (:port server))
        in (cio/reader (.getInputStream socket))
        out (cio/writer (.getOutputStream socket))
        conn (ref {:in in :out out})]
      conn))

(defn write-to [conn message]
  (doto (:out @conn)
    (.write (str message))
    (.newLine)
    (.flush))
  nil)

(defn read-from [conn]
  (.readLine (:in @conn)))