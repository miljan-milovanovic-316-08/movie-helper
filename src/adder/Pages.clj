;This is the main page, contains links to every page in the project as well as GET/POST/PUT




;Useful links and topics about selectors,maps and parsing

;http://stackoverflow.com/questions/12681759/how-to-add-a-string-containing-html-with-clojure-enlive?rq=1
;http://stackoverflow.com/questions/2130448/getting-started-with-css-in-compojure

;http://stackoverflow.com/questions/7466957/combine-multiple-html-fragment-files-with-enlive-clojure?rq=1
;http://stackoverflow.com/questions/16387016/how-to-write-this-clojure-enlive-program-so-it-can-parse-multiple-urls?rq=1

;https://devcenter.heroku.com/articles/clojure-web-application
;http://people.cs.umass.edu/~liberato/blog/2013/02/06/moving-from-noir-to-compojure-and-lib-noir/
;https://groups.google.com/forum/#!forum/enlive-clj
;https://groups.google.com/forum/#!forum/compojure

(ns adder.pages
  (:use compojure.core 
        [hiccup core form page element]
        [adder.PageParser]
        [adder.reorder]
        [adder.dbManagement]
        [adder.image-reader]
		[adder.middleware])
  (:require [compojure.handler :as handler])
			
  (:import [java.io InputStream InputStreamReader BufferedReader]
           [java.net URL HttpURLConnection]))

(defn view-layout [& content]
  (html5 [:head				
				[:title "Movie Helper"]]
		[:body content]
		[:div.img]
		))   
	   
;MAIN PAGE	   
	   
(defn view-input [] 
  (view-layout 
	
			(label "lblnews" ">>> This's just in! Fresh news from CINEMABLEND.COM: <<<")
			[:br]
		
		   (link-to (create-url) (take 1(parse-news-cine)))
		   [:br]
		   (link-to "http://localhost:3000/news" " See all news in list")
		   [:br]
			[:h2 "Find your Movie"] 
			[:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"  }
			(form-to [:post "/"]
			[:br][:br]
      (text-field {:placeholder "Enter movie name" } :a) [:br] 
      (submit-button "Search")
	  [:br][:br][:br]
	  (str "<table border= 2px solid black bgcolor=#F2FB78 table align=center >"
         (str "<tr>" 
		   "<td>" ">>> Your Watchlist <<<" "</td>"
		   "</tr>"
			"<tr>"
		   "<td>" "<a href=" (str "http://localhost:3000/addfilm") ">" "Add a new film to your watchlist" "</a>" "</td>"
		   "</tr>"
		   "<tr>"
           "<td>" "<a href=" (str "http://localhost:3000/addnewmovie-series") ">" "Add new movie-series to your watchlist" "</a>" "</td>"
           "</tr>"            
         "</table>"))
		 [:br][:br] [:br]
		 (str "<table border= 2px solid black bgcolor=#F2FB78 table align=center width=340px>"
			(str "<tr>" 
		   "<td>" ">>> Browse your movies here <<<" "</td>"
		   "</tr>"
			"<tr>"
		   "<td>" "<a href=" (str "http://localhost:3000/listmovies") ">" "View complete list of your movies" "</a>" "</td>"
		   "</tr>"
		   "<tr>"
           "<td>" "<a href=" (str "http://localhost:3000/listseries") ">" "View list of your movie series" "</a>" "</td>"
           "</tr>"            
         "</table>")))
		[:br]
		(label "lblnews" ">>> Latest news from FIRSTSHOWING.NET <<<")
		[:br]
	    (take 1(parse-news))
	    [:br]
	    (label "lblauthor" "Written by: ")
	    (take 1(parse-author))[:br]
		(link-to "http://www.firstshowing.net/category/movie-news/" "Check out more news from FIRSTSHOWING.NET here!")
		[:br]]
		))
      
;CHANGE WINDOW
	  
(defn change-window [] 
  (view-layout 
    [:h2 "You have successfully made changes to your Watchlist"] 
	[:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"  }
    (form-to [:post "/"] 
	  [:br]
      [:br]
	  [:br]
	  (str "<table border= 2px solid black bgcolor=#F2FB78 table align=center >"
          (str "<tr>" 
		   "<td>" ">>> From here, you can: <<<" "</td>"
		   "</tr>"
		   "<tr>"
           "<td>" "<a href=" (str "http://localhost:3000/listmovies") ">" "View complete list of your movies" "</a>" "</td>"
           "</tr>"
		   "<tr>"
           "<td>" "<a href=" (str "http://localhost:3000/listseries") ">" "View list of your movie series" "</a>" "</td>"
           "</tr>"
			"<tr>"
		   "<td>" "<a href=" (str "http://localhost:3000/addfilm") ">" "Add a new film to your watchlist" "</a>" "</td>"
		   "</tr>"
		   "<tr>"
           "<td>" "<a href=" (str "http://localhost:3000/addnewmovie-series") ">" "Add new movie-series to your watchlist" "</a>" "</td>"
           "</tr>"  
         "</table>"))
		 )]
		 ))

;OUTPUT PAGE,SEARCH RESULTS
		 
(defn create-flick-url [a]   
  (str "http://www.rottentomatoes.com/search/?search=" a ""))
 

 (defn create-link [a]
	(apply str "http://www.rottentomatoes.com" (take 1(name+search (create-flick-url a)))))
	
	(defn create-link2 [a]
	(apply str (image+search (create-flick-url a))))

		  

(defn view-output [a]    
  
  (view-layout
	(include-js "http://code.jquery.com/jquery-2.1.1.min.js")
	(include-js "http://yourjavascript.com/9314102313/skripta.js")
	[:h2 "Check review of each movie, from rottentomatoes.com, that match your search: "]
	[:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"  }
		[:form {:method "post" :action "/"}          
			(interleave
				(for [movie-read-link (name+search (create-flick-url a))]
					[:a {:href movie-read-link} "Read here"])
				(for [flick-image (image+search (create-flick-url a))]
					[:img {:src flick-image} [:br] [:br][:br]]))
					]]))
		


;VARIOUS PAGES FOR DATABASE

(defn view-movie-series "view all available movie-series" []
  (html5 		
			[:head				
				[:title "movie-series film collection"]]
			[:h2 "All movie-series:"]	
			[:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"}
    (str "<table border=2px bgcolor= #F2FB78 table align=center>"
         ( apply str (seq (for [movie-series (list-series)]
		 
           (str "<tr>" 
           "<td>"(:series_production movie-series) "</td>"
           "<td>"(:series_name movie-series) "</td>"           
          ;"<td>" (link-to (:read-link ) "See it here!") "</td>" ;
           "<td>" "<a href=" (str "http://localhost:3000/listmoviesfran/" (:series_id movie-series)) ">" "See all saved movies from this movie-series" "</a>" "</td>"
           "<td>" "<a href=" (str "http://localhost:3000/editmovie-series/" (:series_id movie-series)) ">" "Edit movie-series" "</a>" "</td>"
           "<td>" "<a href=" (str "http://localhost:3000/deletemovie-series/" (:series_id movie-series)) ">" "Delete movie-series" "</a>" "</td>"
           ;"<td>" "<a href=" (str "http://localhost:3000/addnewmovie-series") ">" "Add new movie-series" "</a>" "</td>"
           "</tr>"            
           )
         )))
         "<tr>"
           "<td>" "<a href=" (str "http://localhost:3000/addnewmovie-series") ">" "Add new movie-series" "</a>" "</td>"
           "</tr>"
         "</table>")]))

(defn save-movie-series [] 
  (view-layout 
    
    [:h2 "Add new movie-series"] 
	[:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"  }
    (form-to [:post "/addnewmovie-series"]
            
      (label "lblseriesName" "Movie-series title: ")
      (text-field  :txtname) [:br] 
      
      (label "lblProducer" "Production name: ")
      (text-field  :txtproname) [:br]
      
      (submit-button "Save"))
	  [:br]
	  [:br]
	  (str "<a href=http://localhost:3000>"  ">> Return to Homepage <<" "</a>")
	  ]))

(defn update-movie-series [sid] 
  (view-layout 
    [:h2 "Update movie-series"] 
	[:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"  }
    (form-to [:put (format "/editmovie-series/%s" sid)];editmovie-series
            
      (label "lblseriesName" "Movie-series name: ")
      (text-field  :txtname (first (map :series_name (list-one-series sid)))) [:br] 
      
      (label "lblProducer" "Production name: ")
      (text-field  :txtproname (first (map :series_production (list-one-series sid)))) [:br]
      
      (submit-button "Update"))
	  [:br]
	  [:br]
	  (str "<a href=http://localhost:3000>"  ">> Return to Homepage <<" "</a>")
	  ]))

(defn delete-movie-series [sid] 
  (view-layout 
    [:h2 "Delete selected movie-series"] 
    (form-to [:delete (format "/deletemovie-series/%s" sid)];deletemovie-series
            [:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"}
      (label "lblseriesName" "Movie-series name: ")
      (text-field  :txtname (first (map :series_name (list-one-series sid)))) [:br] 
      
      (label "lblProducer" "Production name: ")
      (text-field  :txtproname (first (map :series_production (list-one-series sid)))) [:br]
      
      (submit-button "Delete")])))


(defn view-movies "view all available movies" []   
  (html5 		
			[:head				
				[:title "movie-series film collection"]]
			[:h2 "All movies:"]	
			[:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"}
    (str "<table border= 2px solid black bgcolor=#F2FB78 table align=center>"
         ( apply str (seq (for [film (list-movies)]
           (str 
		   "<tr>" 
           "<td>"(:name film) "</td>"
           "<td>"(:duration film) "</td>"
           "<td>"(:Producer film) "</td>"
           "<td>" (str "<a href=http://localhost:3000/addfilm/" (:id film) ">"  "Edit information" "</a>") "</td>"
           "<td>" (str "<a href=http://localhost:3000/deletefilm/" (:id  film) ">"  "Delete movie" "</a>") "</td>"
           "</tr>"))))
         "<tr>" 
         "<td>" (str "<a href=http://localhost:3000/addfilm >"  "Add movie to your watchlist" "</a>") 
         "</td>" 
         "</tr>" 
         "</table>")]))


(defn view-movies-from-one-movie-series "view all available movies from one movie-series" [sid]   
  (html5 		
			[:head				
				[:title "movie-series film collection"]]
				
			[:h2 "All movies from movie-series:"]	
			[:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"}
    (str "<table border= 2px solid black bgcolor=#F2FB78 table align=center>"
         ( apply str (seq (for [film (list-all-movies-from-serie sid)]
           (str "<tr>" 
           "<td>"(:name film) "</td>"
           "<td>"(:duration film) "</td>"
           "<td>"(:Producer film) "</td>"
           ;"<td>" (str "<a href=http://localhost:3000/addfilm >"  "Add new here!" "</a>") "</td>"
           "<td>" (str "<a href=http://localhost:3000/addfilm/" (:id film) ">"  "Update it here!" "</a>") "</td>"
           "<td>" (str "<a href=http://localhost:3000/deletefilm/" (:id  film) ">"  "Delete it here!" "</a>") "</td>"
           "</tr>")
         ))) "<tr>" "<td>" (str "<a href=http://localhost:3000/addnewfilmtofran/" sid ">"  "Add new here!" "</a>") "</td>" "</tr>"
         "</table>")]))

(defn view-film-input-update [cid] 
  (view-layout 
    [:h2 "Update film"] 
    (form-to [:put (format "/addfilm/%s" cid)]
            [:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"}
      (label "movname" "Film title: ")
      (text-field  :txtname (first (map :name (list-one-film cid)))) [:br] ;(first (:name (list-one-film cid)))
      
      (label "proname" "Producer name: ")
      (text-field  :txtproname (first (map :producer (list-one-film cid)))) [:br] 
      
      (label "duration" "Duration(minutes): ")
      (text-field  :txtduration  (first (map :duration (list-one-film cid))))
      [:br] 
      (submit-button "Update")])))

(defn view-film-input-delete [cid] 
  (view-layout 
    [:h2 "Delete film"] 
    (form-to [:delete (format "/deletefilm/%s" cid)]
            [:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"}
      (label "movname" "Film title: ")
      (text-field  :txtname (first (map :name (list-one-film cid)))) [:br] 
      
      (label "proname" "Producer name: ")
      (text-field  :txtproname (first (map :Producer (list-one-film cid)))) [:br]  
      (label "duration" "Duration(minutes): ")
      (text-field  :txtduration  (first (map :duration (list-one-film cid))))
      [:br] 
      (submit-button "Delete")])))

(defn view-film-input [] 
  (view-layout 
  [:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"}
    [:h2 "Add new film"] 
    (form-to [:post "/addfilm" ]
            
      (label "movname" "Film title: ")
      (text-field  :txtname) [:br] 
      
      (label "proname" "Producer name: ")
      (text-field  :txtproname) [:br] 
      
      (label "duration" "Duration(minutes): ")
      (text-field  :txtduration)[:br]
      (submit-button "Save"))
	  [:br]
	  [:br]
	  (str "<a href=http://localhost:3000>"  ">> Return to Homepage <<" "</a>")
	  ]))
	  
(defn view-news [] 
  (view-layout 
  [:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px :text-align: center"}
    [:h2 "All news from cinemablend.com"] 
		
		(interpose [:br] (parse-news-cine))
		
	  [:br]
	  (str "<a href=http://www.cinemablend.com/news.php>"  ">> Visit cinemablend here <<" "</a>")
	  [:br]
	  (str "<a href=http://localhost:3000>"  ">> Return to Homepage <<" "</a>")
	  ]))
	  
	  
(defn view-film-input-in-movie-series [sid] 
  (view-layout 
  [:body {:style "font: 14pt/16pt helvetica; background-color: #F2FB78; padding-top:100px; text-align: center"}
    [:h2 "Add new film"] 
    (form-to [:post (format "/addnewfilmtofran/%s" sid)]
            
      (label "movname" "Film title: ")
      (text-field  :txtname) [:br] 
      
      (label "proname" "Producer name: ")
      (text-field  :txtproname) [:br] 
      
      (label "duration" "Duration(minutes): ")
      (text-field  :txtduration)[:br]
      (submit-button "Save"))]))

;GET/POST/PUT SECTION
	  

(defroutes main-routes            
   (GET "/" [] 
      (view-input))
  
  (POST "/" [a] 
	(do
       (println "A" a )
  (view-output a)))
  
  (GET "/news" []
        (view-news))
  
  (GET "/listmovies" []
        (view-movies))
  
  (GET "/addfilm/:cid" [cid] (view-film-input-update cid))
  (PUT "/addfilm/:cid" [cid txtname txtproname txtduration] (update-flick (read-string cid) {:name txtname :Producer txtproname :duration txtduration}) (change-window))
  
  (GET "/addnewfilmtofran/:sid" [sid] (view-film-input-in-movie-series sid))
  (POST "/addnewfilmtofran/:sid" [sid txtname txtproname txtduration]
        (insert-flick txtname txtproname txtduration (read-string sid))(change-window))
  
  (GET "/addfilm" [] (view-film-input))
  (POST "/addfilm" [txtname txtproname txtduration]
        (insert-flick txtname txtproname txtduration 90) (change-window))
  
  (GET "/deletefilm/:cid" [cid] (view-film-input-delete cid))
  (DELETE "/deletefilm/:cid" [cid] (delete-flick cid)(change-window))
  
  (GET "/listmoviesfran/:sid" [sid]
        (view-movies-from-one-movie-series sid))

  (GET "/listseries" []
        (view-movie-series))
  
  (GET "/addnewmovie-series" [] (save-movie-series))
  (POST "/addnewmovie-series" [txtname txtproname] (insert-serie txtname txtproname)(change-window))
  
  (GET "/editmovie-series/:sid" [sid] (update-movie-series sid));editmovie-series
  (PUT "/editmovie-series/:sid" [sid txtname txtproname] (update-serie (read-string sid) {:series_name txtname :series_production txtproname})(change-window))
  
  (GET "/deletemovie-series/:sid" [sid] (delete-movie-series sid))
  (DELETE "/deletemovie-series/:sid" [sid] (delete-series sid)(change-window))
  
  
  )
   

(def app (handler/site main-routes)) ; wrap the params to allow destructuring to work
