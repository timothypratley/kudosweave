(ns com.kudosweave.servlet
  (:gen-class :extends javax.servlet.http.HttpServlet)
  (:require [compojure.core :as c])
  (:require [ring.util.servlet :as s])
  (:require [com.kudosweave.template-google :as t])
  (:require [com.kudosweave.user-service :as u])
  (:use com.kudosweave.pages.home)
  (:use com.kudosweave.pages.login)
  (:use com.kudosweave.pages.not-authorized)
  (:use com.kudosweave.pages.attack)
  (:use com.kudosweave.pages.ally)
  (:use com.kudosweave.pages.not-found))

; TODO: use ring.util.response instead
(defn rredirect
  "Returns a Ring response for an HTTP 302 redirect."
  [url]
  {:status 302
   :headers {"Location" url}
   :body ""})

(defmacro redirect-if-not
  [check redirect]
  `(c/ANY "*" [] (if-not ~check (rredirect ~redirect))))

#_(defn with-layout
  [handler]
  (comp t/layout handler))

#_(defn with-user
  [handler]
  (fn [request]
    (handler (assoc request :username (u/get-user)))))


(c/defroutes webservice
             ; public pages
             (c/GET "/login" [] (t/layout "Login" (login)))

             (redirect-if-not (u/is-logged-in) "/login")
             ; logged in pages
             (c/GET "/" []
                    (t/layout "Home" (home (u/get-user))))
             (c/GET "/attack/:player" [player]
                    (t/layout "Attack" (attack (u/get-user) player)))
             (c/GET "/ally/:player" [player]
                    (t/layout "Ally" (ally (u/get-user) player)))
             
             (redirect-if-not (u/is-admin) "/")
             ; admin pages

             ; not found
             (rredirect "/"))

(s/defservice webservice)

