(ns com.kudosweave.pages.login
  (:require [com.kudosweave.user-service :as users]))

(defn login
  [layout req]
  {:status 200
   :headers {}
   :body (layout "Login"
                 [[:div {:dir "ltr"}
                   "This site will be a proof of concept network of trust. "
                   "You must " [:a {:href (users/get-login-url "/")} "sign in"]
                   " to join the game."]])})

