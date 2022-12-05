(ns aoc22.day-05
  (:require [aoc22.utils :refer [definput]]
            [clojure.string :as str]
            [clojure.core.match :refer [match]]
            [clojure.set :refer [intersection]]
            [clojure.java.io :as io]
            [clojure.edn :as edn]
            [instaparse.core :as insta]))

(definput)

(def input-parser
  (insta/parser (io/resource "day-05.bnf")))

(defn stack-lines->stacks [nums & stack-lines]
  (let [initial (zipmap nums (repeat []))
        lines (map #(zipmap nums %) stack-lines)]
    (reduce (fn [stacks line] (merge-with #(if %2 (conj %1 %2) %1) stacks line)) initial lines)))

(defn process-movement [do-reverse curr-stacks & {:keys [from to quantity]}]
 ; (prn curr-stacks)
  (let [froms (get curr-stacks from)
        tos (get curr-stacks to)
        [new-froms rest] (split-at (- (count froms) quantity) froms)
        new-tos (concat tos (if do-reverse (reverse rest) rest))]
    (assoc curr-stacks from (into [] new-froms) to (into [] new-tos))))


(defn make-transformer [do-reverse]
  {:puzzle-input (partial reduce (partial process-movement do-reverse))
   :stacks (comp #(apply stack-lines->stacks %) reverse vector)
   :movements vector
   :stack-line vector
   :stack-nums vector
   :movement (comp (partial into {}) vector)

   :non-existing-crate #(identity nil)
   :existing-crate identity
   :stack-identifier #(keyword (str "stack-" %))
   :number edn/read-string})

(defn input->str [do-reverse input]
  (->> input
      input-parser
      (insta/transform (make-transformer do-reverse))
      (map (fn [[k, v]] [k, (last v)]))
      (sort-by first)
      (map second)
      (str/join "")))

(def part-1 (partial input->str true))
(def part-2 (partial input->str false))


(def example-input
"    [D]
[N] [C]
[Z] [M] [P]
 1   2   3

move 1 from 2 to 1
move 3 from 1 to 3
move 2 from 2 to 1
move 1 from 1 to 2\n")

(comment
  (process-movement {:stack-1 ["Z" "N"], :stack-2 ["M" "C" "D"], :stack-3 ["P"]} {:quantity 1, :from :stack-2, :to :stack-1})
  (insta/transform input-transformer (input-parser example-input))
  (time (part-1 input))
  (time (part-2 input)))
