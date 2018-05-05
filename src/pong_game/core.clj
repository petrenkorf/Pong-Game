(ns pong-game.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def left-racket  (atom {:x 10  :y 20  :w 15 :h 80}))
(def right-racket (atom {:x 575 :y 20  :w 15 :h 80}))
(def ball         (atom {:x 290 :y 205 :w 20 :h 20}))

(defn setup []
  (q/frame-rate 60))

(defn update-state [state])

(defn draw-item [r]
  (q/rect (:x r) (:y r) (:w r) (:h r)))

(defn draw-state [state]
  (q/background 0x20)
  (q/fill 0xff)
  (draw-item @left-racket)
  (draw-item @right-racket) 
  (draw-item @ball))

; Input

(defn keypress [p1 key-data]
  (cond
    
    ; left
    (= (:key-code key-data) 87) 
      (swap! left-racket update-in [:y] (fn [v] (- v 10))) 
    (= (:key-code key-data) 83) 
      (swap! left-racket update-in [:y] (fn [v] (+ v 10))) 
    
    ; right
    (= (:key-code key-data) 38) 
      (swap! right-racket update-in [:y] (fn [v] (- v 10))) 
    (= (:key-code key-data) 40) 
      (swap! right-racket update-in [:y] (fn [v] (+ v 10)))))

(defn -main [& args]
  (q/defsketch pong-game
    :title "Pong Game"
    :size [600 450]
    :setup setup
    :update update-state
    :key-pressed keypress
    :draw draw-state
    :features [:keep-on-top]
    :middleware [m/fun-mode]))
