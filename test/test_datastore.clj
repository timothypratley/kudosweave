(ns test-datastore
  (:use clojure.test)
  (:use com.kudosweave.datastore))

(deftest test-query
         (is (query "foo" [:filter "bar" not= "baz"] [:sort "bar"])))

