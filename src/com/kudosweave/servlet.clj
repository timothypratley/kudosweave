(ns com.kudosweave.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use com.kudosweave.template)
  (:use com.kudosweave.home)
  (:use com.kudosweave.attack)
  (:use com.kudosweave.ally)
  (:use ring.util.servlet)
  (:use compojure))


(defroutes webservice
           (GET "/" req (home req))
           (GET "/attack" req (attack req))
           (GET "/ally" req (ally req))
           (ANY "*" req {:status 404
                         :headers {}
                         :body (layout "Not Found"
                                    [:p "404 not found"])}))

(defservice webservice)

