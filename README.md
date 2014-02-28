# metal-pet

Simple Clojure string templating.

# Installation
Available via Clojars.
Add the following to your project.clj dependencies.
```clj
[metal-pet "0.1.0-SNAPSHOT"]
```

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
