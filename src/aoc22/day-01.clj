(ns aoc22.day-01
  (:require [aoc22.utils :refer [definput]]
            [clojure.string :as str]))

(definput)

(def calories (let [lines (str/split-lines input)]
                (->> lines
                     (pmap #(if (empty? %) nil (Integer/parseInt %)))
                     (partition-by nil?)
                     (filter #(some some? %))
                     (pmap #(reduce + %)))))

(defn part-1 []
  (apply max calories))

(defn part-2 []
  (->> calories
       (sort >)
       (take 3)
       (reduce +)))

(comment
  (part-1)
  (part-2))
