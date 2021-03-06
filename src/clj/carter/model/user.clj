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

(ns carter.model.user
  (:require [carter.services.orientdb :as db]))

(def select-queries
  {:find-all "select from User"
   :find-by-rid "select from User where @rid = :rid"
   :find-by-id "select from User where id = :id"
   :find-by-username "select from User where username = :username"
   :find-by-hashtag "select user.@rid as rid, user.id as id,
                            user.username as username,
                            user.screen_name as screen_name
                     from (
                       MATCH {class: Hashtag, as: hashtag,
                              where: (@rid = :rid)}
                       .in('Has'){as: has}
                       .inE('Tweeted'){as: tweeted}
                       .outV(){as: user,
                                where: ($matched.hashtag != $currentMatch)}
                       RETURN user)"})

(def update-queries
  {:update-by-rid "update :rid set id = :id, username = :username,
                   screen_name = :screen_name"})

(defn find-all
  "Retrieve all the User vertexes."
  []
  (let [query (:find-all select-queries)]
    (db/query! query)))

(defn- find-by
  [find-key params]
  (let [query (get select-queries find-key)]
    (db/query! query params)))

(defn find-by-rid
  "Find the User vertex by `rid`."
  [rid]
  (first (find-by :find-by-rid {:rid rid})))

(defn find-by-id
  "Find the User vertex by `id`."
  [id]
  (first (find-by :find-by-id {:id id})))

(defn find-by-username
  "Find the User vertex by `username`."
  [username]
  (first (find-by :find-by-username {:username username})))

(defn find-by-hashtag
  "Find Tweet vertexes by `hashtag`. `hashtag` is a rid."
  [hashtag]
  (find-by :find-by-hashtag {:rid hashtag}))

(defn create
  "Create a User vertex.
  `params` is a map with :id, :username and :screen_name keys.
  All values are strings."
  [params]
  (db/insert! "User" params))

(defn update-by-rid
  "Update a User vertex.
  `params` is a map with :rid, :id, :username and :screen_name keys."
  [params]
  (let [query (:update-by-rid update-queries)]
    (db/execute! query params)))

(defn delete
  "Delete a User by `rid`."
  [rid]
  (db/delete! rid))
