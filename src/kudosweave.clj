(ns kudosweave
  (:use [compojure]))

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
          body]]))

(defroutes webservice
           (GET "/"
                sample-form)
           (GET "/blah"
                (html [:html
                       [:head
                        [:title "kudosweave"]]
                       [:body
                        [:div
                         [:h2 "Kudos Weave"]]]])))

(defn -main [&args]
  (run-server {:port 8080}
              "/*" (servlet webservice)))

