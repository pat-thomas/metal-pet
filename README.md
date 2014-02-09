# metal-pet

Simple Clojure string templating.

## Example usage
```clj
(ns example
  (:require [metal-pet.core :refer [render-template]]))

(render-template 
 "#{some-template-key} #{some-nested.template-key} some non templated string stuff #{some-default}" 
 {:some-template-key "stuff"
  :some-nested {:template-key "things"}}
 {:some-default "chicken wings"})
;; "stuff things some non templated string stuff chicken wings"
```
