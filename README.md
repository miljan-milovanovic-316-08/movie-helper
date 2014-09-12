movie-helper
============

# Watchlist

Project designed to help out users with movie news, movie search and to form his very own WATCHLIST, mildly inspired from imdb's watchlist.
Upon running .bat file "pokreniMe.bat" user activates :lein-ring-server which furthermore opens homepage of the project.

From there, user can see latest news parsed from cinemablend.com into title which is also a link to that same news.
Secondly, at the bottom of the page another piece of latest news is parsed from firstshowing.com along with the author name of that news.
A part from that, user can access database through various pages for adding new movie, new movie series, as well as updating and deleting them.
Another feature of this project is search box where entered string is parsed into search result from rottentomatoes.com and displays new page with fetched images and links to movies, matching the search query.

Project uses http://localhost:3000/ as port
For phpmyadmin :user "root"
               :password "" 


Useful guides and tutorials can be found on stackoverflow.com, google groups for compojure and enlive, clojuredocs and github

