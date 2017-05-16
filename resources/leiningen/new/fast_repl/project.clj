(defproject {{raw-name}} "0.0.1-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/tools.namespace "0.2.11"]
                 [com.taoensso/tufte "1.1.1"]
                 [org.clojure/tools.trace "0.7.9"]
                 [mount "0.1.11"]]
  :main ^:skip-aot {{namespace}}
  :target-path "target/%s"
  :repl-options {:init-ns user}
  :jvm-opts ["-XX:-OmitStackTraceInFastThrow"]
  :profiles {:uberjar {:aot :all}
             :dev {:source-paths ["dev"]
                   :dependencies [[com.cemerick/pomegranate "0.3.1"]]}})