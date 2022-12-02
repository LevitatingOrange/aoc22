# Solutions for Advent of Code 2022

My solutions for the Advent of Code 2022 in Clojure.

## Utilities
* Put your AoC session key into an environment variable `AOC_SESSION_KEY` and run `clojure -X:download-input :day <day>` to download inputs for the day and store them in `inputs/`
* Use the macro `aoc22.utils/definputs` to automatically define `input` containing the input file for the day (namespace has to be named `day-<day>`)

## TODOs
* Try out clerk
* Add some nice auto clojurescript to README or smth?
