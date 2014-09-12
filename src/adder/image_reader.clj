(ns adder.image-reader 
  ;(:gen-class)
  (:import (myjava ImageOpener)
           (myjava PageScraper))
  )


(def test-acct (ImageOpener.))

(def test-acct2 (PageScraper.))

(defn open-image2 [loc]
 ;(myjava ImageOpener)
  (.RunMe test-acct loc))
 
 (defn get-rank-new [url]
 
  (.ParseURL test-acct2 url)
 
)
