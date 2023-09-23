(ns tic-tac-toe.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]))

(def game-state
  (r/atom {:squares (into [] (repeat 9 nil))
           :turn "X"}))

(defn click-handler [pos]
  (let [squares (:squares @game-state)]
    (when (nil? (nth squares pos))
      (if (= "X" (:turn @game-state))
        (swap! game-state assoc :squares (assoc squares pos "X")
                                :turn "O")
        (swap! game-state assoc :squares (assoc squares pos "O")
                                :turn "X")))))

(defn square [pos value]
  [:button.square {:on-click (fn [] (click-handler pos))}
    value])

(defn board []
  (let [squares (:squares @game-state)]
    [:<>
      [:div.board-row
       [square 0 (nth squares 0)]
       [square 1 (nth squares 1)]
       [square 2 (nth squares 2)]]
      [:div.board-row
       [square 3 (nth squares 3)]
       [square 4 (nth squares 4)]
       [square 5 (nth squares 5)]]
      [:div.board-row
       [square 6 (nth squares 6)]
       [square 7 (nth squares 7)]
       [square 8 (nth squares 8)]]]))

(defn mount-root []
  (d/render [board] (.getElementById js/document "app")))

(defn ^:export init! []
  (mount-root))
