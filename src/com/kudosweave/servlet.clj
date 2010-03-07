(ns com.kudosweave.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use com.kudosweave.template)
  (:use com.kudosweave.not-found)
  (:use com.kudosweave.home)
  (:use com.kudosweave.attack)
  (:use com.kudosweave.ally)
  (:use compojure.http.servlet)
  (:use compojure))


(defroutes webservice
           (GET "/" req (home req))
           (GET "/attack/:player" [player] (attack player))
           (GET "/ally/:player" [player] (ally player))
           (ANY "*" req (not-found req)))

(defservice webservice)

