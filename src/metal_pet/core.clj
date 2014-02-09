(ns metal-pet.core)

(defonce template-key-matcher #"#\{[A-Za-z0-9.-]+\}")

(defn template-key->keywords [template-key-string]
  (let [trimmed-string (->> template-key-string (drop 2) drop-last (apply str))]
    (map keyword (clojure.string/split trimmed-string #"\."))))

(defn template-keys
  [template-string]
  (->> template-string
       (re-seq template-key-matcher)
       (map template-key->keywords)))

(defn render-template 
  ([template-string opts defaults]
     (let [tks           (template-keys template-string)
           merged-opts   (reduce (fn [acc ks]
                                   (assoc-in acc ks (or (get-in opts ks)
                                                        (get-in defaults ks))))
                                 {}
                                 tks)
           rendered-keys (map #(get-in merged-opts %)
                              tks)]
       (loop [acc-str           template-string
              loc-tks           tks
              loc-rendered-keys rendered-keys]
         (if (empty? loc-tks)
           acc-str
           (recur (clojure.string/replace-first acc-str template-key-matcher (first loc-rendered-keys))
                  (rest loc-tks)
                  (rest loc-rendered-keys))))))
  ([template-string opts]
     (render-template template-string opts {})))
