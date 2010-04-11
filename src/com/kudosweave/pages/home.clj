(ns com.kudosweave.pages.home
  (:require [com.kudosweave.datastore :as ds])
  (:require [com.kudosweave.user-service :as users]))


(defn render-player
  [p]
  [:li (str p) [:a {:href (str "/attack/" (:username p))} "[attack]"]
               [:a {:href (str "/ally/" (:username p))} "[ally]"]])

; TODO: replace find-one with get by indexed key (username)
; pass in all data (separate from view)
(defn home
  [username]
  (let [players (ds/find-all (ds/query "player"
                                       [:filter "username" not= username]
                                       [:sort "username"]))
        me (ds/find-one (ds/query "player"
                                  [:filter "username" = username]))
        event-log (atom [])]
    (if (not me)
      (ds/put {:kind "player", :username username, :hp 100, :kudos 0})
      (swap! event-log conj
             (str "Welcome " username ". You just joined the game.")))
    ; TODO: how to write side-effect heavy updated objects
    ; and generating a event log better
    [[:div {:dir "ltr"}
      ; TODO: use <br \>?
      (apply str (interpose \newline @event-log))
      (str "You are " username)]
     [:div {:dir "ltr"}
      "Other players:"
      (if (empty? players)
        "There are no existing players"
        [:ul (map render-player players)])]]))


