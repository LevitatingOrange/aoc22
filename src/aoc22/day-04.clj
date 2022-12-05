(ns aoc22.day-04
  (:require [aoc22.utils :refer [definput]]
            [clojure.string :as str]
            [clojure.core.match :refer [match]]
            [clojure.set :refer [intersection]]))

(defn check-contains-1 [min_1, max_1, min_2, max_2]
  (cond
    (and (<= min_1 min_2) (>= max_1 max_2)) :fst
    (and (<= min_2 min_1) (>= max_2 max_1)) :snd
    :else nil))

(defn check-contains-2 [min_1, max_1, min_2, max_2]
  (cond
    (and (>= max_1 min_2) (>= max_2 min_1)) :fst
    :else nil))


(defn check-line [checker line]
  (as-> line curr
      (str/split curr #"[-,]")
      (pmap #(Integer/parseInt %) curr)
      (apply checker curr)))

(defn part-1 [input]
  (->> input
       str/split-lines
       (pmap #(check-line check-contains-1 %))
       (filterv identity)
       (count)))

(defn part-2 [input]
  (->> input
       str/split-lines
       (pmap #(check-line check-contains-2 %))
       (filterv identity)
       (count)))


(definput)
(comment
  (check-contains-2 6 6 4)
  (check-line "4-8,3-9")
  (part-1 input)
  (part-2 input))
