(ns chaos.core
  (:require [quil.core :as q]))

(def screen [1200 800])
(def unit [10 10 ])
(def n-points 300)
(def max-rand-n 50)

(def sigma 10)
(def rho 28)
(def beta (/ 8 3))
(def dt 0.005)

(def points (atom (mapv #(into (vector) %)
                        (for [i (range n-points)
                              :let [x (- (rand-int (* 2 max-rand-n)) max-rand-n)
                                    y (- (rand-int (* 2 max-rand-n)) max-rand-n)
                                    z (- (rand-int (* 2 max-rand-n)) max-rand-n)]]
                          [x y z]))))

(defn lorenz-attractor [[x y z]]
  [(+ x (* dt (* sigma (- y x))))
   (+ y (* dt (- (* x (- rho z)) y)))
   (+ z (* dt (- (* x y) (* beta z))))])

(defn setup []
  (q/frame-rate 60)
  (q/background 0))

(defn draw []
  (q/background 0)
  (q/stroke-weight 10)
  (q/stroke 0 255 0)
  (reset! points (mapv lorenz-attractor @points))
  (mapv #(q/point (+ (/ (get screen 0) 2) (* (get % 0) (get unit 0)))
                  (+ (/ (get screen 1) 2) (* (get % 1) (get unit 1)))) @points))

(q/defsketch chaos
  :title "Chaos Attractor"
  :setup setup
  :draw draw
  :size [(get screen 0) (get screen 1)])
