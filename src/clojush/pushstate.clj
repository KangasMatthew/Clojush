(ns clojush.pushstate
  (:use [clojush.globals]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; states, stacks, and instructions

;; struct-based states follow

(defmacro define-push-state-structure []
  `(defstruct push-state ~@push-types))

(define-push-state-structure)

(defn make-push-state
  "Returns an empty push state."
  []
  (struct-map push-state))


(def registered-instructions (atom #{}))

(defn register-instruction 
  "Add the provided name to the global list of registered instructions."
  [name]
  (swap! registered-instructions conj name))

(def instruction-table (atom (hash-map)))

(defmacro define-registered
  [instruction definition]
  `(do (register-instruction '~instruction)
       (swap! instruction-table assoc '~instruction ~definition)))

(defn state-pretty-print
  [state]
  (doseq [t push-types]
    (printf "%s = " t)
    (println (t state))
    (flush)))

(defn push-item
  "Returns a copy of the state with the value pushed on the named stack. This is a utility,
   not for use in Push programs."
  [value type state]
  (assoc state type (cons value (type state))))

(defn top-item
  "Returns the top item of the type stack in state. Returns :no-stack-item if called on 
   an empty stack. This is a utility, not for use as an instruction in Push programs."
  [type state]
  (let [stack (type state)]
    (if (empty? stack)
      :no-stack-item
      (first stack))))

(defn stack-ref
  "Returns the indicated item of the type stack in state. Returns :no-stack-item if called 
   on an empty stack. This is a utility, not for use as an instruction in Push programs.
   NOT SAFE for invalid positions."
  [type position state]
  (let [stack (type state)]
    (if (empty? stack)
      :no-stack-item
      (nth stack position))))

(defn pop-item
  "Returns a copy of the state with the specified stack popped. This is a utility,
   not for use as an instruction in Push programs."
  [type state]
  (assoc state type (rest (type state))))

(defn registered-for-type
  "Returns a list of all registered instructions with the given type name as a prefix."
  [type & {:keys [include-randoms] :or {include-randoms true}}]
  (let [for-type (filter #(.startsWith (name %) (name type)) @registered-instructions)]
    (if include-randoms
      for-type
      (filter #(not (.endsWith (name %) "_rand")) for-type))))

(defn registered-nonrandom
  "Returns a list of all registered instructions aside from random instructions."
  []
  (filter #(not (.endsWith (name %) "_rand")) @registered-instructions))