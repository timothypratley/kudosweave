(ns com.kudosweave.home
  (:require [appengine-clj.datastore :as ds])
  (:require [appengine-clj.users :as users])
  (:import [com.google.appengine.api.datastore Query Query$FilterOperator])
  (:import [com.google.appengine.api.users UserServiceFactory])
  (:use com.kudosweave.template))

(defn render-player
  [p]
  [:li (str p) [:a {:href (str "/attack?player=" (:username p))} "[attack]"]
               [:a {:href (str "/ally?player=" (:username p))} "[ally]"]])


(defn home
  [req]
  (let [players (ds/find-all (doto (Query. "player") (.addSort "username")))]
    {:status 200
     :headers {}
     :body (layout "Home"
                   [:p [:div {:class "article"}
                      [:p "This site will be a proof of concept network of trust."]
                      [:p (if-let [user (:user (users/user-info))]
                            (let [username (.getNickname user)
                                  me (ds/find-all (doto (Query. "player")
                                                    (.addFilter "username" Query$FilterOperator/EQUAL username )))]
                              (when (empty? me)
                                (ds/create {:kind "player", :username username, :hp 100, :kudos 0})
                                "You just joined the game! ")
                              (str "You are " username))
                            [:p "You must " [:a {:href (.createLoginURL (UserServiceFactory/getUserService) "/")} "sign in"]
                             " to join the game."])]]
                     [:div {:class "article"}
                      [:p "Other players:"]
                      [:p (if (empty? players)
                            "There are no existing players"
                            [:ul (map render-player players)])]]])}))

