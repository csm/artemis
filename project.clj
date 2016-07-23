(defproject artemis "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [metosin/compojure-api "1.1.4"]
                 [org.immutant/web "2.1.5"]
                 [ring/ring-devel "1.5.0"]
                 [ring/ring-core "1.5.0"]
                 [org.clojure/tools.cli "0.3.5"]
                 [org.danielsz/system "0.3.0"]]
  :plugins [[lein-uberwar "0.2.0"]]
  :uberwar {:handler artemis.core/handler}
  ;; :aws {:beanstalk {:environments [{:name "development"
  ;;                                   :cname-prefix "artemis-dev-cd1ed93e"}
  ;;                                  {:name "production"
  ;;                                   :cname-prefix "artemis-prod-0fc9a620"}]}}
  :main ^:skip-aot artemis.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}
             :dev {:dependencies [[javax.servlet/servlet-api "2.5"]]}})
