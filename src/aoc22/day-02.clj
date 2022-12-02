(ns aoc22.day-02
  (:require [aoc22.utils :refer [definput]]
            [clojure.string :as str]
            [clojure.core.match :refer [match]]))

;; This solution is a bit verbose but therefore pretty understandable I think
;; TODO: Maybe find a "nicer" solution

(definput)

(defn play-round-1 [opponent me]
    (let [[t_score, w_score] (match [me opponent]
         [:rock :rock] [1 3]
         [:rock :paper] [1 0]
         [:rock :scissors] [1 6]
         [:paper :rock] [2 6]
         [:paper :paper] [2 3]
         [:paper :scissors] [2 0]
         [:scissors :rock] [3 0]
         [:scissors :paper] [3 6]
         [:scissors :scissors] [3 3])]
      (+ t_score w_score)))

(defn parse-hand-1 [hand]
  (match hand
         "X" :rock
         "Y" :paper
         "Z" :scissors
         "A" :rock
         "B" :paper
         "C" :scissors))

(defn parse-hand-2 [hand]
  (match hand
         "X" :loose
         "Y" :draw
         "Z" :win
         "A" :rock
         "B" :paper
         "C" :scissors))

(defn play-round-2 [opponent strategy]
    (let [[t_score, w_score] (match [opponent strategy]
         [:rock :win] [2 6]
         [:rock :draw] [1 3]
         [:rock :loose] [3 0]
         [:paper :win] [3 6]
         [:paper :draw] [2 3]
         [:paper :loose] [1 0]
         [:scissors :win] [1 6]
         [:scissors :draw] [3 3]
         [:scissors :loose] [2 0])]
      (+ t_score w_score)))

(defn part-1 []
  (let [lines (str/split-lines input)
        scores (->> lines
                    (pmap #(apply play-round-1 (pmap parse-hand-1 (str/split % #" ")))))]
    (reduce + scores)))

(defn part-2 []
  (let [lines (str/split-lines input)
        scores (->> lines
                    (pmap #(apply play-round-2 (pmap parse-hand-2 (str/split % #" ")))))]
    (reduce + scores)))

(comment
  (pmap #(apply play-round-1 (pmap parse-hand-1 %)) [["A" "Y"] ["B" "X"] ["C" "Z"]])
  (pmap #(apply play-round-2 (pmap parse-hand-2 %)) [["A" "Y"] ["B" "X"] ["C" "Z"]])
  (part-1)
  (part-2))
