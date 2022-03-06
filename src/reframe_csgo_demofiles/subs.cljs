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
 ::current-demo-file
 (fn [db _]
   (:current-demo-file db)))
