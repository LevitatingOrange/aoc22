(ns aoc22.day-01
  (:require [aoc22.utils :refer [definput]]
            [clojure.string :as str]))

(definput)

(defn get-calories [input] (let [lines (str/split-lines input)]
                (->> lines
                     (pmap #(if (empty? %) nil (Integer/parseInt %)))
                     (partition-by nil?)
                     (filter #(some some? %))
                     (pmap #(reduce + %)))))

(defn part-1 [input]
  (apply max (get-calories input)))

(defn part-2 [input]
  (->> input
       (get-calories)
       (sort >)
       (take 3)
       (reduce +)))

(comment
  (part-1 input)
  (part-2 input))
