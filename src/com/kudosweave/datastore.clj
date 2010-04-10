(ns com.kudosweave.datastore
  (:import (com.google.appengine.api.datastore
             DatastoreServiceFactory Entity Key Query
             Query$FilterOperator Query$SortDirection))
  ;(:import (javax.jdo PersistenceManagerFactory JDOHelper))
  (:refer-clojure :exclude [get]))

; Inspired by John Hume
;http://elhumidor.blogspot.com/2009/04/clojure-on-google-appengine.html

(def data-service (atom nil))
(defn get-data-service
  "Singleton access to a data-service. (Not really necessary)"
  []
  (if @data-service
    @data-service
    (reset! data-service (DatastoreServiceFactory/getDatastoreService))))

(comment
(def persistence-manager (atom nil))
(defn get-pm
  "Singleton access to a persistence manager."
  []
  (if @persistence-manager
    @persistence-manager
    (reset! persistence-manager
            (.getPersistenceManager (JDOHelper/getPersistenceManagerFactory)))))
(defn pm-query
  [query-string]
  (.execute (.newQuery query-string))))

(defn entity-to-map
  "Converts an instance of com.google.appengine.api.datastore.Entity
  to a PersistentHashMap with properties stored under keyword keys,
  plus the entity's kind stored under :kind and key stored under :key."
  [#^Entity entity]
  (reduce #(assoc %1 (keyword (key %2)) (val %2))
    {:kind (.getKind entity) :key (.getKey entity)}
    (.entrySet (.getProperties entity))))

(defn get
  "Retrieves the identified entity or raises EntityNotFoundException."
  [#^Key key]
  (entity-to-map (.get (get-data-service) key)))

(def filter-operator
  {= Query$FilterOperator/EQUAL
   > Query$FilterOperator/GREATER_THAN
   >= Query$FilterOperator/GREATER_THAN_OR_EQUAL
   :in Query$FilterOperator/IN
   < Query$FilterOperator/LESS_THAN
   <= Query$FilterOperator/LESS_THAN_OR_EQUAL
   not= Query$FilterOperator/NOT_EQUAL})
(def sort-direction
  {:asc Query$SortDirection/ASCENDING
   :desc Query$SortDirection/DESCENDING})
(defn query
  "kind is a string
  optional vectors which can be
  [:filter property-name operator value]
  [:sort property-name :asc/:desc]
  [:sort property-name]
  [:keys true/false]
  [:keys]"
  [kind & more]
  (let [q (Query. kind)]
    (doseq [v more]
      (condp = (first v)
        :filter (.addFilter q (nth v 1) (filter-operator (nth v 2)) (nth v 3))
        :sort (if (> (count v) 2)
                (.addSort q (nth v 1) (sort-direction (nth v 2)))
                (.addSort q (nth v 1)))
        :keys (if (> (count v) 1)
                (.setKeysOnly q (nth v 1))
                (.setKeysOnly q true))))
    q))

(defn find-all
  "Executes the given com.google.appengine.api.datastore.Query
  and returns the results as a lazy sequence of items converted
  with entity-to-map."
  [#^Query query]
  (map entity-to-map (.asIterable (.prepare (get-data-service) query))))

(defn find-one
  "Executes the given com.google.appengine.api.datastore.Query
  and returns a single result converted with entity-to-map."
  [#^Query query]
  (if-let [e (.asSingleEntity (.prepare (get-data-service) query))]
    (entity-to-map e)))

(defn put
  "Takes a map of keyword-value pairs and puts a new Entity in the Datastore.
  The map must include a :kind String.
  Returns the saved Entity converted with entity-to-map
  (which will include the assigned :key).
  If you specify a :key, the Entity will be updated instead."
  ([item] (put item nil))
  ([item #^Key parent-key]
    (let [kind (:kind item)
          key (:key item)
          properties (dissoc item :kind :key)
          entity (cond
                   key (Entity. key)
                   parent-key (Entity. kind parent-key)
                   :else (Entity. kind))]
      (doseq [[prop-name value] properties]
        (when (not= prop-name :key)
          (.setProperty entity (name prop-name) value)))
      (.put (get-data-service) entity)
      (entity-to-map entity))))

(defn delete
  "Deletes the identified entities."
  [& #^Key keys]
  (.delete (get-data-service) keys))

