(ns com.kudosweave.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:use com.kudosweave.template)
  (:use ring.util.servlet)
  (:use compojure))

(defn success [body]
  {:status 200 :headers {} :body body})
(defn failure [body]
  {:status 404 :headers {} :body body})

(defroutes webservice
           (GET "/" req (success
                          (html-doc "Welcome"
                                    [:p "This site will be a proof of concept network of trust."])))
           (GET "/blah" req (success
                              (html-doc "Blah"
                                        [:p "Right now there is not much to see"])))
           ;(GET "/*" req (or (serve-file (params :*)) :next))
           (ANY "*" req (failure
                          (html-doc "Not Found"
                                    [:p "404 not found"]))))

(defservice webservice)

