(defproject kudosweave "0.1.0-SNAPSHOT"
            :description "Kudos Weave"
            :repositories [["mvnsearch" "http://www.mvnsearch.org/maven2/"]]
            :dependencies [[org.clojure/clojure "1.1.0"]
                           [org.clojure/clojure-contrib "1.1.0"]
                           [compojure "0.3.2"]
                           [com.google.appengine/appengine-tools-sdk "1.3.1"]
                           [tjpext "1.0.0-SNAPSHOT"]]
            :main com.kudosweave.server
            :dev-dependencies [[leiningen-run "0.2"]
                               [uk.org.alienscience/leiningen-war "0.0.1"]])
