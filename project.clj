(defproject Watchlist "0.1.0-SNAPSHOT"
  :description "Find and add movies to your Watchlist"  
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [ring "1.2.0"]
                 [hiccup "1.0.3"]
                 [enlive "1.1.1"]
                 [compojure "1.1.5"]
                 [org.clojure/java.jdbc "0.2.3"]
                 [mysql/mysql-connector-java "5.1.6"]]
  ;:dev-dependencies[[org.clojure/java.jdbc "0.2.3"] ]
  ;:java-source-path "src/myjava"
  :java-source-paths ["src/myjava"]
             :aot :all        
  :ring {:handler adder.pages/app}
  :plugins [[lein-ring "0.8.6"]])
  :resource-path "resources/"
