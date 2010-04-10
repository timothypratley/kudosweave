(ns com.kudosweave.pages.attack
  (:require [com.kudosweave.datastore :as ds])
  (:require [com.kudosweave.user-service :as users]))

(defn attack
  [layout player]
  (let [them (ds/find-one (ds/query "player" [:filter "username" = player]))
        combat-log (atom [])]
    (if them
      (let [damaged (ds/put (update-in them [:hp] dec))]
        (swap! combat-log conj
               (str (:username them) " takes 1 point of damage"))
        (when (<= (:hp damaged) 0)
          (ds/put (assoc them :hp 100))
          (swap! combat-log conj
                 (str "You killed " (:username them)
                      "! But they magically resurect...")))))
    {:status 200
     :headers {}
     :body (layout "Attack"
                   [[:p (if them
                          (apply str (interpose \newline @combat-log))
                          (str "Player " player " not found."))]])}))

  #_(foo {:status 302
   :headers {"Location" "/"}})


