(ns metal-pet.core)

(defn template-keys
  "Given a string like 'Hello #{consumer.first-name} #{consumer.last-name}, returns [[:consumer :first-name] [:consumer :last-name]]"
  [template-string]
  (->> template-string
       (re-seq #"\{[A-Za-z.-]+\}")
       (map template-key->keywords)))

(defn template-key->keywords [template-key-string]
  (let [trimmed-string (->> template-key-string (drop 1) drop-last (apply str))]
    (map keyword (clojure.string/split trimmed-string #"\."))))

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
           (recur (clojure.string/replace-first acc-str #"#\{[A-Za-z.-]+\}" (first loc-rendered-keys))
                  (rest loc-tks)
                  (rest loc-rendered-keys))))))
  ([template-string opts]
     (templatize template-string opts {})))
