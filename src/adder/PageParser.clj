;Here are the most useful functions for parsing content from websites




;Useful links and topics from various sites

;http://stackoverflow.com/questions/9320374/clojure-using-enlive-to-extract-raw-html-from-a-selector?rq=1
;http://stackoverflow.com/questions/25608674/parsing-search-results-from-website-compojure-clojure/25609639?noredirect=1#comment40316649_25609639

;http://rob-rowe.blogspot.com/2011/05/parsing-web-pages-with-clojure-and.html
;https://groups.google.com/group/enlive-clj/browse_thread/thread/41288d06fcaf83f0

;http://stackoverflow.com/questions/10512609/displaying-data-from-two-parallel-sequences-on-web-page-in-compojure?rq=1
;http://clojure-doc.org/articles/tutorials/basic_web_development.html

(ns adder.PageParser
  (:use [adder.image-reader])
  (:require [net.cgrand.enlive-html :as html])
  (:require [clojure.string :as string]))


  (def url1 "http://www.rottentomatoes.com/search/?search=ada")
  (def url2 "http://www.firstshowing.net/category/movie-news/")
  (def url3 "http://www.cinemablend.com/news.php")
  (def url4 "http://www.cinemablend.com")
  (def url5 "http://www.rottentomatoes.com/m/")
  
  
  
  (defn get-page
  "Gets the html page from passed url"
  [url]
  (html/html-resource (java.net.URL. url)))
  
; PARSE-LINKS-NEWS-CONTENT
  (defn parse-news "Parses news title from firstshowing.com"[]
  (html/select 
   (get-page url2) 
   [:div.article :h2 :a html/text]))
   
   (defn parse-author "Parses author name of the last news on firstshowing.com" []
  (html/select 
   (get-page url2) 
   [:div.date2a :span :a html/text]))
   
 (defn parse-news-cine "Parses last news from cinemablend" []
  (html/select 
   (get-page url3) 
   [:div.nniListing :div.nnicontent :a html/text]))

  (defn select+ [coll selector+]
   (map
     (peek selector+)
     (html/select 
       (get-page url3) 
       (pop selector+))))

(def href
  (fn [node] (:href (:attrs node))))

(defn parse-test []
  (select+ 
   (get-page url3) 
   [:div.nniListing :div.nnicontent :a href])) 
   
   (defn create-url []   
    (apply str url4 (take 1(parse-test))))
	
	;(defn create-flick-url [a]   
   ; (apply str "http://www.rottentomatoes.com" (take 1(parse-links))))
	
	;(defn parse-links "Parses links"[]
	;(selection 
		;(get-page url1) 
		;[:ul#movie_results_ul.results_ul :div.media_block_content :h3.nomargin :a href]))
		
	(defn create-flick-url2 [a]   
	   (str "http://www.rottentomatoes.com/search/?search="a""))	
		
	(defn parse-search "Parses last news from cinemablend" []
				(html/select 
				 (get-page url1)
				[:ul#movie_results_ul.results_ul :div.media_block_content :h3.nomargin :a html/text]))
		
	(defn selection [coll selector+]
		(map
			(peek selector+)
			(html/select 
					(get-page url1) 
					(pop selector+))))	
		;(defn print-flick-name
		;	[url]
			;(vec (flatten (map :content (parse-links url)))))
   
 ;MAPS HERE
 
(defn name+search
  "This function returns a sequence of h3 tags,where h3.nomargin is parsed from rotten"
  [url]
  (vec (flatten (map #(get-in % [:attrs :href]) (html/select (get-page url) [:h3.nomargin :a])))))  

 
(defn image+search
  "Function that returns a sequence of tags, where span.movieposter is image parsed from rotten"
  [url]
    (vec (flatten (map #(get-in % [:attrs :src]) (html/select (get-page url) [:span.movieposter :img])))))    
   
; TEST

;(defn print-flick-name-content
  ;[url]
  ;(vec (flatten (map :content (name+search url)))))

;(defn get-image-content
  ;[url]
  ;(vec (flatten (map #(re-find #"http.*jpg" %) (map :style (map :attrs (image+search url)))))))


;(defn get-all-reading-links "Lists links to all series of chosen flick "
  ;[url]
  ;(vec (for [flick-url 
        ; (vec (map :href (map :attrs (name+search url))))]
			;(str "http://www.rottentomatoes.com" flick-url))))


