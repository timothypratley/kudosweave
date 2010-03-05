(ns com.kudosweave.ally
  (:import [com.google.appengine.api.datastore Query Query$FilterOperator])
  (:require [appengine-clj.datastore :as ds])
  (:require [appengine-clj.users :as users]))


(defn ally
  [req]
  (let [them (ds/find-all (doto (Query. "player")
                        (.addFilter "username" Query$FilterOperator/EQUAL (:player req))))]
    (if (seq them)
      (ds/create (update-in (first them) [:kudos] inc))))
  {:status 302
   :headers {"Location" "/"}})

