(ns com.kudosweave.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:require [com.kudosweave.template-google :as t])
  (:require [com.kudosweave.user-service :as u])
  (:use com.kudosweave.pages.home)
  (:use com.kudosweave.pages.login)
  (:use com.kudosweave.pages.not-authorized)
  (:use com.kudosweave.pages.attack)
  (:use com.kudosweave.pages.ally)
  (:use com.kudosweave.pages.not-found)
  (:require [compojure.core :as c])
  (:require [ring.util.servlet :as s]))

(defn ensure-logged-in
  []
  nil)

(defn ensure-admin
  []
  nil)

; TODO: do login/auth middleware
(c/defroutes webservice
             ; public views
             (c/GET "/login" [] (login t/layout))

             ; logged in views
             ;(c/ANY "/*" [] (ensure-logged-in))
             (c/GET "/" [] (home t/layout (assoc {} :username (u/get-user))))
             (c/GET "/attack/:player" [player] (attack t/layout player))
             (c/GET "/ally/:player" [player] (ally t/layout player))
             
             ; admin only
             ;(c/ANY "/*" [] (ensure-admin))
             ; TODO: not-found only shown to admin user??
             (c/ANY "*" [] (not-found t/layout)))

(s/defservice webservice)

