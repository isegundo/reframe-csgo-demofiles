(ns reframe-csgo-demofiles.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 ::app-name
 (fn [db]
   (:app-name db)))

(re-frame/reg-sub
 ::active-panel
 (fn [db _]
   (:active-panel db)))

(re-frame/reg-sub
 ::demo-file-parsed
 (fn [db _]
   (:demo-file-parsed db)))

(re-frame/reg-sub
 ::current-tick
 (fn [db _]
   (:current-tick db)))

;; (re-frame/reg-sub
;;  ::player-names
;;  (fn [db _]
;;    (let [demo-file (-> db
;;                        :current-demo-file)]
;;      (js/console.log (.. demo-file -entities))
;;      "Blu"
;;      )))