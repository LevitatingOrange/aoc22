(ns aoc22.utils
  (:require [hato.client :as http]
            [clojure.java.io :as io]
            [clojure.spec.alpha :as s]
            [clojure.string :as str]))

(def aoc-year 2022)

(comment
  (format "day-%02d" 1)
  (def url (format "https://adventofcode.com/%d/day/%d/input" aoc-year 1))
  (:body (http/get url {:headers {"Cookie" (format "session=%s" "53616c7465645f5faf17d24ffb40119e146933876c78781fb9c7a0350a16c7c6e6ff53cb53344ad9cc7ee4b25cbbe55c4756fbdd9def20037cb2c48b9d7b1cfd")}}))
  (io/resource "day-01.txt")
  (-> "inputs/" (io/file) (.mkdir))
  (s/valid? (s/and int? #(< % 24) #(> % 0)) 25)
  (-> *ns* str (str/split #"\.") peek))

(defn download-input [&
                      {:keys [day aoc-session-key inputs-folder overwrite]
                       :or {aoc-session-key (System/getenv "AOC_SESSION_KEY")
                            inputs-folder "inputs"
                            overwrite false}}]
  {:pre [(s/valid? (s/and int? #(< % 24) #(> % 0)) day)
         (s/valid? string? aoc-session-key)]}
  (-> inputs-folder (io/file) (.mkdir))
  (let [path (format "%s/day-%02d.txt" inputs-folder day)
        url (format "https://adventofcode.com/%d/day/%d/input" aoc-year day)
        cookie (format "session=%s" aoc-session-key)]
    (if (and (not overwrite) (-> path (io/file) (.exists)))
      (throw (Exception. "input file exists. use :overwrite true to overwrite")))
    (println "Downloading from" url "...")
    (spit path (:body (http/get url {:headers {"Cookie" cookie}})))
    (println "Saved in" path "!")))

(defn load-input [& {:keys [inputs-folder] :or {inputs-folder "inputs"}}]
  (let [day-str (-> *ns* str (str/split #"\.") peek)
        path (format "%s/%s.txt" inputs-folder day-str)] (slurp path)))

(defmacro definput [] `(def ~'input (load-input)))
