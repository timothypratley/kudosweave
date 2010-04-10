(ns com.kudosweave.pages.not-authorized)

(defn not-authorized
  [layout req]
  ; TODO: return the correct code
  {:status 200
   :headers {}
   :body (layout "Not Authorized"
                 [[:div {:dir "ltr"}
                   "You are not authorized to do that."]])})

