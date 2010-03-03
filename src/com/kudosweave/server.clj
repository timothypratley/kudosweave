(ns com.kudosweave.server
  (:use compojure)
  (:use com.kudosweave.servlet))

(defn -main [& args]
  (run-server {:port 8080} "/*" (servlet webservice)))

