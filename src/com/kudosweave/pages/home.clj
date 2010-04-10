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
  [layout req]
  (let [players (ds/find-all (ds/query "player"
                                       [:filter "username" not= (:user req)]
                                       [:sort "username"]))
        me (ds/find-one (ds/query "player"
                                  [:filter "username" = (:user req)]))
        event-log (atom [])]
    (if (not me)
      (ds/put {:kind "player", :username (:user req), :hp 100, :kudos 0})
      (swap! event-log conj (str "Welcome " (:user req) ". You just joined the game.")))
    ; TODO: how to write side-effect heavy updated objects
    ; and generating a event log better
    {:status 200
     :headers {}
     :body (layout "Home"
                   [[:div {:dir "ltr"}
                     ; TODO: use <br \>?
                     (str (interpose \newline @event-log))
                     (str "You are " (:user req))]
                    [:div {:dir "ltr"}
                     "Other players:"
                     (if (empty? players)
                       "There are no existing players"
                       [:ul (map render-player players)])]])}))


