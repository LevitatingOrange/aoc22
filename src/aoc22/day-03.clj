(ns aoc22.day-03
  (:require [aoc22.utils :refer [definput]]
            [clojure.string :as str]
            [clojure.core.match :refer [match]]
            [clojure.set :refer [intersection]]))

(definput)

; bit "magic"
(defn char-to-prio [char]
  (let [num (int char)
        prio (bit-and num 2r11111)]
    (if (and (bit-test num 6)
             (bit-test num 5))
      (+ prio  0)
      (+ prio 26))))

(defn line-to-rucksack [line]
  (let [split-at (/ (count line) 2)]
        (pmap set (partition split-at line))))

(defn line-to-duplicate [line]
    (->> line
       (line-to-rucksack)
       (apply intersection)
       (first)
       (char-to-prio)))

(defn part-1 [input]
  (let [lines (str/split-lines input)
        priorities (pmap line-to-duplicate lines)]
    (reduce + priorities)))

(defn part-2 [input]
  (->> input
      str/split-lines
      (partition 3)
      (pmap #(first (apply intersection (pmap set %))))
      (pmap char-to-prio)
      (reduce +)))


(comment
  (->> "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL"
       (pmap char-to-prio))
  (line-to-duplicate "vJrwpWtwJgWrhcsFMMfFFhFp")
  (line-to-duplicate "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL")
  (line-to-duplicate "PmmdzqPrVvPwwTWBwg")
  (line-to-duplicate "wMqvLMZHhHMvwLHjbvcjnnSBnvTQFn")
  (line-to-duplicate "ttgJtRGJQctTZtZT")
  (line-to-duplicate "CrZsJsPPZsGzwwsLwLmpwMDw")
  (first (apply intersection (line-to-rucksack "jqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL")))
  (part-2 "vJrwpWtwJgWrhcsFMMfFFhFp\njqHRNqRjqzjGDLGLrsFMfFZSrLrFZsSL\nPmmdzqPrVvPwwTWBwg\nwMqvLMZHhHMvwLHjbvcjnnSBnvTQFn\nttgJtRGJQctTZtZT\nCrZsJsPPZsGzwwsLwLmpwMDw")

  (part-1 input)
  (part-2 input))
