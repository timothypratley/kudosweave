(ns com.kudosweave.pages.ally
  (:require [com.kudosweave.datastore :as ds]))


(defn ally
  [layout player]
  (let [q (ds/query "player" [:filter "username" = player])]
    {:status 200
     :headers {}
     :body (layout "Ally"
                   [[:p (if-let [them (seq (ds/find-one q))]
                          (do (ds/put (update-in them [:kudos] inc))
                            (str "You allied with " (:username them)))
                          (str "Player " player " Not found! "))]])}))

  #_(foo {:status 302
   :headers {"Location" "/"}})

