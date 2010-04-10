(ns com.kudosweave.user-service
  (:import [com.google.appengine.api.users UserServiceFactory]))

; Inspired by John Hume
;http://elhumidor.blogspot.com/2009/04/clojure-on-google-appengine.html

(def user-service (atom nil))
(defn get-user-service
  "UserService for the current request."
  []
  (if @user-service
    @user-service
    (reset! user-service (UserServiceFactory/getUserService))))
;{:user (.getCurrentUser user-service) :user-service user-service}

(defn get-user
  "If the user is not logged in will return nil."
  []
  (.getCurrentUser (get-user-service)))

(defn get-login-url
  ([dest]
   (.createLoginURL (get-user-service) dest))
  ([dest auth-domain]
   (.createLoginURL (get-user-service) dest auth-domain)))

(defn get-logout-url
  ([dest]
   (.createLogoutURL (get-user-service) dest))
  ([dest auth-domain]
   (.createLogoutURL (get-user-service) dest auth-domain)))

(defn is-admin
  []
  (.isUserAdmin (get-user-service)))

(defn is-logged-in
  []
  (.isUserLoggedIn (get-user-service)))

