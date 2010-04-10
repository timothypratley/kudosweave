(defproject kudosweave "0.1.0-SNAPSHOT"
            :description "Kudos Weave"
            :dependencies [[org.clojure/clojure "1.1.0"]
                           [org.clojure/clojure-contrib "1.1.0"]
                           ;[compojure "3.2.0"]
                           ;[org.buntin/compojure "0.4.0-SNAPSHOT"]
                           ;[hiccup "0.4.0-SNAPSHOT"]
                           ;[ring "0.1.1-SNAPSHOT"]
                           ;[com.google.appengine/appengine-api-sdk "1.3.1"]
                           [tjpext "1.0.0-SNAPSHOT"]]
            ;:namespaces [com.kudosweave.servlet]
            :compile-path "war/WEB-INF/classes/"
            :library-path "war/WEB-INF/lib/"
            #_(:dev-dependencies [;[uk.org.alienscience/leiningen-war "0.0.2"]
                               [autodoc "0.7.0"]]))
