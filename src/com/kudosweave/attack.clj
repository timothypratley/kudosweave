(ns com.kudosweave.attack
  (:use com.kudosweave.template-google)
  (:import [com.google.appengine.api.datastore Query Query$FilterOperator DatastoreServiceFactory])
  (:require [appengine-clj.datastore :as ds])
  (:require [appengine-clj.users :as users]))

(defn attack
  [player]
  (let [q (doto (Query. "player")
            (.addFilter "username" Query$FilterOperator/EQUAL player))
        d (DatastoreServiceFactory/getDatastoreService)]
    {:status 200
     :headers {}
     :body (layout "Attack"
                   [[:p (if-let [them (seq (ds/find-all q))]
                          (let [e (.get d (:key (first them)))]
                            (.setProperty e "hp" (dec (or (.getProperty e "hp") 100)))
                            (.put d e)
                            (str (:username (first them)) " takes 1 point of damage."
                                 (when (<= (.getProperty e "hp") 0)
                                   (.setProperty e "hp" 100)
                                   (.put d e)
                                   " You killed them! But they magically resurect...")))
                          (str "Player " player " Not found! "))]])}))

  #_(foo {:status 302
   :headers {"Location" "/"}})

