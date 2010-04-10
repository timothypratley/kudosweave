(ns pages.test-home
  (:use clojure.test)
  (:use com.kudosweave.template-google)
  (:use com.kudosweave.pages.home))

(deftest test-home
         (is (home layout {})))

