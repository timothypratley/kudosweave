(ns com.kudosweave.ally
  (:use com.kudosweave.template-google)
  (:import [com.google.appengine.api.datastore Query Query$FilterOperator DatastoreServiceFactory])
  (:require [appengine-clj.datastore :as ds])
  (:require [appengine-clj.users :as users]))


(defn ally
  [player]
  (let [q (doto (Query. "player")
            (.addFilter "username" Query$FilterOperator/EQUAL player))
        d (DatastoreServiceFactory/getDatastoreService)]
    {:status 200
     :headers {}
     :body (layout "Ally"
                   [[:p (if-let [them (seq (ds/find-all q))]
                          (let [e (.get d (:key (first them)))]
                            (.setProperty e "kudos" (inc (or (.getProperty e "kudos") 0)))
                            (.put d e)
                            (str "You allied with " (:username (first them))))
                          (str "Player " player " Not found! "))]])}))

  #_(foo {:status 302
   :headers {"Location" "/"}})

