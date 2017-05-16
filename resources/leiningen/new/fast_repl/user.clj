(ns user
  (:require [mount.core :as mount :refer [defstate]]
            [clojure.tools.namespace.repl :as tn]))

;; Let Clojure warn you when it needs to reflect on types, or when it does math
;; on unboxed numbers. In both cases you should add type annotations to prevent
;; degraded performance.
(set! *warn-on-reflection* true)
(set! *unchecked-math* :warn-on-boxed)

(defmacro init-aliases []
  "A macro for initializing any aliases that may be useful during development.
   Very different from (:requiring :as...) in this namespace's ns form, since that 
   would make our REPL unbootable if the application does not compile"
  `(do
      (~'ns-unalias ~''user ~''core)
      (~'alias ~''core '{{name}}.core)
      ))

(defmacro add-dependency [dependency]
  "A macro for adding a dependency via Pomegranate.
   Usage: (add-dependency [cheshire \"5.7.0\"])
   Remember that you still need to (require) or (use) the new namespaces."
  `(do (~'require '[cemerick.pomegranate])
       (~'cemerick.pomegranate/add-dependencies :coordinates '[~dependency]
         :repositories (~'merge cemerick.pomegranate.aether/maven-central
                         {"clojars" "http://clojars.org/repo"}))))

(clojure.tools.namespace.repl/set-refresh-dirs "src" "dev")


;; Lifecycle

(def start mount/start)

(defn init []
  "Full reset. Refreshes all namespaces (thus restarting any started states) and calls the
   remaining states"
  (tn/refresh-all)
  (mount/start)
  (init-aliases)
  :ready)

(defn reset []
  "Hard reset. Stops all states, refreshes the changed namespaces, then starts the states again"
  (mount/stop)
  (tn/refresh :after 'user/start)
  (init-aliases)
  :resetted)

(defn r []
  "Soft reset. Refreshes the changed namespaces (thus restarting the states in them)"
  (tn/refresh)
  (init-aliases)
  :done)