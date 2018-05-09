(ns pong-game.core
  (:require [quil.core :as q]
            [quil.middleware :as m]))

(def left-racket  (atom {:x 10  :y 20  :w 15 :h 80}))
(def right-racket (atom {:x 575 :y 20  :w 15 :h 80}))
(def ball         (atom {:x 290 :y 205 :w 20 :h 20}))
(def ball-dir     (atom [-5 5]))

(defn setup []
  (q/frame-rate 60))

; Update

(defn next-ball [ball [spdx spdy]]
  (assoc ball :x (+ (:x ball) spdx)
              :y (+ (:y ball) spdy)))

(defn change-direction [dir]
  [(first dir) (* -1 (second dir))])

(defn reset-ball [ball]
  (assoc ball :x 290
              :y 205))

(defn ball-collision [b]
  (cond
    (>= (:y b) 430) (swap! ball-dir change-direction)
    (<= (:y b) 0) (swap! ball-dir change-direction)
    (<  (:x b) 0) (swap! ball reset-ball)
    (>  (:x b) 600) (swap! ball reset-ball)
    
    ))

(defn update-state [state]
  (swap! ball next-ball @ball-dir)
  (ball-collision @ball))

; Draw

(defn draw-item [item]
  (q/rect (:x item) (:y item) (:w item) (:h item)))

(defn draw-state [state]
  (println ball)
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
