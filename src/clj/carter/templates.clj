;; Copyright 2017 7bridges s.r.l.
;;
;; Licensed under the Apache License, Version 2.0 (the "License");
;; you may not use this file except in compliance with the License.
;; You may obtain a copy of the License at
;;
;; http://www.apache.org/licenses/LICENSE-2.0
;;
;; Unless required by applicable law or agreed to in writing, software
;; distributed under the License is distributed on an "AS IS" BASIS,
;; WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
;; See the License for the specific language governing permissions and
;; limitations under the License.

(ns carter.templates
  (:require [hiccup.page :as hiccup]))

(defn index-html
  "Main index."
  [title]
  (hiccup/html5
   {:lang "it"}
   [:head
    [:meta {:charset "utf-8"}]
    [:meta {:http-equiv "X-UA-Compatible" :content "IE=edge,chrome=1"}]
    [:meta
     {:name "viewport"
      :content "width=device-width, initial-scale=1.0, maximum-scale=1.0"}]
    [:title "carter"]
    (hiccup/include-css "css/semantic.min.css")]
   [:body
    [:div#app]
    (hiccup/include-js
     "//d3js.org/d3.v4.min.js"
     "//cdnjs.cloudflare.com/ajax/libs/d3-legend/2.24.0/d3-legend.js"
     "js/main.js")
    [:script "carter.core.init();"]]))
