(ns com.kudosweave.pages.not-found)

(defn not-found
  [layout req]
  {:status 404
   :headers {}
   :body (layout "Not Found"
                 [[:p "404 page not found"]])})

