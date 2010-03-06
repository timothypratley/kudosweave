(ns com.kudosweave.template-google
  (:import [com.google.appengine.api.users UserServiceFactory])
  (:use hiccup))

(defn render-content
  [c]
  [:tr
   [:td {:id "sites-canvas-wrapper"}
    [:div {:id "sites-canvas"} c]]])

(defn layout
  [title content]
  (html [:html {:xmlns "http://www.w3.org/1999/xhtml"}
         [:head
          [:title (str title " - Kudos Weave")]
          [:link {:rel "shortcut icon" :href "/favicon.ico" :type="image/ico"}]
          [:link {:rel "stylesheet" :type "text/css"
                  :href "https://ssl.gstatic.com/sites/p/67804d/system/app/themes/solitudeolive/standard-css-solitudeolive-ltr-ltr.css"}]
          [:meta {:name "google-site-verification", :content "sNyyjRGK-5zjzwz7MMfIJe_Cjo-HsKjRTJU2-qwJjnA"}]]
         [:body {:xmlns "http://www.google.com/ns/jotspot", :id "body", :class " en"}
          [:div {:id "sites-page-toolbar"}
           [:div {:id "sites-status", :class "sites-status", :style "display:none;"}
            [:div {:id "sites-notice", :class "sites-notice"}]]]
          [:div {:id "sites-chrome-everything" :style "direction: ltr"}
           [:div {:id "sites-chrome-page-wrapper"}
            [:div {:id "sites-chrome-page-wrapper-inside"}
             [:div {:xmlns "http://www.w3.org/1999/xhtml", :id "sites-chrome-header-wrapper"}
              [:table {:id "sities-chrome-header", :class "sites-layout-hbox"}
               [:tr {:class "sites-header-primary-row"}
                [:td {:id "sites-header-title"}
                 [:h2 [:a {:href "http://play.kudosweave.com", :dir "ltr"} "Play Kudos Weave"]]]
                [:td {:class "sites-layout-searchbox"}
                 (let [user-service (UserServiceFactory/getUserService)]
                   (if-let [user (.getCurrentUser (UserServiceFactory/getUserService))]
                     [:h3 (.getNickname user) " - " [:a {:href (.createLogoutURL user-service "/")} "sign out"]]
                     [:h2 [:a {:href (.createLoginURL user-service "/")} "sign in"]]))]]]]
             [:div {:id "sites-chrome-main-wrapper"}
              [:div {:id "sites-chrome-main-wrapper-inside"}
               [:table {:id "sites-chrome-main", :class "sites-layout-hbox", :cellspacing "0"}
                (map render-content content)]]]
             [:div {:id "sites-chrome-footer-wrapper"}
              [:div {:id "sites-chrome-footer-wrapper-inside"}
               [:br]]]]]]
          [:script {:type "text/javascript"}
"var gaJsHost = ((\"https:\" == document.location.protocol) ? \"https://ssl.\" : \"http://www.\");
document.write(unescape(\"%3Cscript src='\" + gaJsHost + \"google-analytics.com/ga.js' type='text/javascript'%3E%3C/script%3E\"));"]
          [:script {:type "text/javascript"}
"try {
var pageTracker = _gat._getTracker(\"UA-15077584-1\");
pageTracker._setDomainName(\".kudosweave.com\");
pageTracker._trackPageview();
} catch(err) {}"]]]))

