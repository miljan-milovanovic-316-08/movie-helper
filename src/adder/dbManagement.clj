(ns adder.dbManagement
  (:require [clojure.java.jdbc :as sql]))


(def db {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :subname "//localhost:3306/watchlist"
         :user "root"
         :password ""})

;(defn list-movies []
 ; (sql/with-connection db
  ;  (sql/with-query-results rows
   ;   ["select * from flick"]
      ;(println rows)
    ;  )))




(defn list-one-film-from-series [id sid]
  (flatten (sql/with-connection db
    (sql/with-query-results row
      ["select * from flick where id = ? and series =?" id] 
      (doall row)))))





(defn list-all-movies-from-serie [sid]
  (flatten (sql/with-connection db
    (sql/with-query-results row
      ["select * from flick where series =?" sid] 
      (doall row)))))

(defn list-movies []
  (flatten (sql/with-connection db
    (sql/with-query-results rows
      ["select * from flick"]
      (doall rows)))))

(defn list-one-film [id]
  (flatten (sql/with-connection db
    (sql/with-query-results row
      ["select * from flick where id = ?" id] 
      (doall row)))))

(defn insert-flick [name Producer duration series]
  (sql/with-connection db (sql/insert-values :flick [:name :Producer :duration :series] [name Producer  duration series])))

(defn update-flick [id update-map]
  (sql/with-connection db
    (sql/update-values :flick ["id=?" id] update-map)))

(defn delete-flick [id]
  (sql/with-connection db 
    (sql/delete-rows :flick ["id=?" id])))


(defn list-one-series [id]
  (flatten (sql/with-connection db
    (sql/with-query-results row
      ["select * from series where Series_ID = ?" id] 
      (doall row)))))

;(defn save-film )


(defn list-series []
  (flatten (sql/with-connection db
    (sql/with-query-results rows
      ["select * from series"]
      (doall rows)))))

(defn insert-serie [series_name series_production]
  (sql/with-connection db (sql/insert-values :series [:series_name :series_production] [series_name series_production])))

(defn update-serie [id update-map]
  (sql/with-connection db
    (sql/update-values :series ["Series_ID=?" id] update-map)))

(defn delete-series [id]
  (sql/with-connection db 
    (sql/delete-rows :series ["Series_ID=?" id])))
