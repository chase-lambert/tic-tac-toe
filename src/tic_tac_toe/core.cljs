(ns tic-tac-toe.core
    (:require
      [reagent.core :as r]
      [reagent.dom :as d]))

(def game-state
  (r/atom {:squares (into [] (repeat 9 nil))
           :turn "X"
           :winner nil}))

(def winning-lines
  [[0 1 2]
   [3 4 5]
   [6 7 8]
   [0 3 6]
   [1 4 7]
   [2 5 8]
   [0 4 8]
   [2 4 6]])

(defn click-handler [pos]
  (let [current-square (nth (:squares @game-state) pos)]
    (when (nil? current-square)
      (let [new-move    (if (= "X" (:turn @game-state)) "X" "O")
            new-squares (assoc (:squares @game-state) pos new-move)
            winner      (first
                          (for [[a b c] winning-lines
                                :let [[d e f] [(nth new-squares a) 
                                               (nth new-squares b) 
                                               (nth new-squares c)]]
                                :when (and d e f (= d e f))]
                            d))]
        (swap! game-state assoc :squares new-squares
                                :turn (if (= "X" (:turn @game-state)) "O" "X")
                                :winner winner)))))


(defn square [pos value]
  [:button.square {:on-click (fn [] (click-handler pos))}
    value])

(defn board []
  (let [squares (:squares @game-state)
        winner  (:winner @game-state)]
    [:<>
      (when winner
        [:div winner " wins!"])
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
