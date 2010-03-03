(ns com.kudosweave.servlet
  ;(:import [com.google.appengine.api.users UserServiceFactory])
  (:use compojure)
  (:gen-class :extends javax.servlet.http.HttpServlet))

(defn html-doc
  [title & body]
  (html (doctype :html4)
        [:html
         [:head
          [:title (str title " - Kudos Weave")]]
         [:body
          [:div
           [:h2
            [:a {:href "/"} "Kudos Weave"]]]
          #_(let [user-service (UserServiceFactory/getUserService)]
            (if-let [user (.getCurrentUser (UserServiceFactory/getUserService))]
              [:p (.getNickname user) " - " (link-to (.createLogoutURL user-service "/") "sign out")]
              [:p (link-to (.createLoginURL user-service "/") "sign in")]))
          body
          [:img {:src "http://code.google.com/appengine/images/appengine-silver-120x30.gif"
                 :alt "Powered by Google App Engine"
                 :href "http://code.google.com/appengine/"}]]]))

(defroutes webservice
           (GET "/" (html-doc "Welcome"
                              [:p "This site will be a proof of concept network of trust."]))
           (GET "/blah" (html-doc "Blah"
                                  [:p "Right now there is not much to see"]))
           (ANY "*" (page-not-found)))

(defservice webservice)

