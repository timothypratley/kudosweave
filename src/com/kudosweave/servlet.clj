(ns com.kudosweave.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:require [com.kudosweave.template :as t])
  (:use com.kudosweave.pages.home)
  (:use com.kudosweave.pages.login)
  (:use com.kudosweave.pages.not-authorized)
  (:use com.kudosweave.pages.attack)
  (:use com.kudosweave.pages.ally)
  (:use com.kudosweave.pages.not-found)
  (:require [compojure.http.servlet :as cs])
  (:require [compojure :as c]))


; TODO: do login/auth middleware
(c/defroutes webservice
           (c/GET "/" req (home t/layout req))
           (c/GET "/login" req (login t/layout req))
           (c/GET "/attack/:player" [player] (attack t/layout player))
           (c/GET "/ally/:player" [player] (ally t/layout player))
           (c/ANY "*" req (not-found t/layout req)))

(c/defservice webservice)

