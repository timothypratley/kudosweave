(ns com.kudosweave.template
  (:import [com.google.appengine.api.users UserServiceFactory])
  (:use hiccup))

(defn html-doc
  [title & body]
  (html [:html
         [:head
          [:title (str title " - Kudos Weave")]
          [:link {:rel "shortcut icon"
                  :href "/favicon.ico"
                  :type="image/ico"}]]
         [:body
          [:div
           [:h2
            [:a {:href "/"} "Kudos Weave"]]]
            (let [user-service (UserServiceFactory/getUserService)]
              (if-let [user (.getCurrentUser (UserServiceFactory/getUserService))]
                [:p (.getNickname user) " - "
                 [:a {:href (.createLogoutURL user-service "/")} "sign out"]]
                [:a {:href (.createLoginURL user-service "/")} "sign in"]))
          body
          [:a {:href "http://code.google.com/appengine/"}
           [:img {:src "http://code.google.com/appengine/images/appengine-silver-120x30.gif"
                  :alt "Powered by Google App Engine"
                  :title "Powered by Google App Engine"}]]]]))

