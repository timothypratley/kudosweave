(ns com.kudosweave.template
  (:import [com.google.appengine.api.users UserServiceFactory])
  (:use hiccup))

(defn layout
  [title content]
  (html [:html
         [:head
          [:title (str title " - Kudos Weave")]
          [:link {:rel "shortcut icon"
                  :href "/favicon.ico"
                  :type="image/ico"}]
          [:link {:rel "stylesheet"
                  :media "screen,projection"
                  :type "text/css"
                  :href "/css/main.css"}]
          [:link {:rel "stylesheet"
                  :media "print"
                  :type "text/css"
                  :href "/css/print.css"}]
          [:link {:rel "stylesheet"
                  :media "aural"
                  :type "text/css"
                  :href "/css/aural.css"}]]
         [:body
          [:div {:id "main", :class "box"}
           [:div {:id "header"}
            [:h1 {:id "logo"} [:a {:href "/"} "Kudos Weave"]]
            [:p (let [user-service (UserServiceFactory/getUserService)]
              (if-let [user (.getCurrentUser (UserServiceFactory/getUserService))]
                [:p (.getNickname user) " - "
                 [:a {:href (.createLogoutURL user-service "/")} "sign out"]]
                [:h2 [:a {:href (.createLoginURL user-service "/")} "sign in"]]))]]
           [:div {:id "content"} [:p content]]
           [:div {:id "footer"}
            [:center
             [:a {:href "http://code.google.com/appengine/"}
              [:img {:src "http://code.google.com/appengine/images/appengine-silver-120x30.gif"
                     :alt "Powered by Google App Engine"
                     :title "Powered by Google App Engine"}]]]]]]]))

