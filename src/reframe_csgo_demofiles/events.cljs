(ns reframe-csgo-demofiles.events
  (:require
   [re-frame.core :as re-frame]
   [reframe-csgo-demofiles.db :as db]
   [day8.re-frame.tracing :refer-macros [fn-traced]]))

(re-frame/reg-event-db
 ::initialize-db
 (fn-traced [_ _]
            db/default-db))

(re-frame/reg-event-fx
 ::navigate
 (fn-traced [_ [_ handler]]
            {:navigate handler}))

(re-frame/reg-event-fx
 ::set-active-panel
 (fn-traced [{:keys [db]} [_ active-panel]]
            {:db (assoc db :active-panel active-panel)}))


(re-frame/reg-event-db
 ::tickend
 (fn-traced [db [_ tick tick-data]]
            (update-in db [:ticks tick] merge tick-data)))

; TODO move to other place?
(defn extract-player
  [player]
  {:id (. player -userId)
   :name (.. player -userInfo -name)
   :pos-x (.. player -position -x)
   :pos-y (.. player -position -y)
   :pos-z (.. player -position -z)})

; TODO move to other place?
(defn dispatch-tickend
  [tick demofile]
  (when (and (< tick 100)
             (>= tick 0))
    (re-frame/dispatch [::tickend tick {:players (map extract-player (. demofile -players))}])))

(re-frame/reg-event-fx
 ::demo-file-selected
 (fn-traced [_ [_ file]]
            (let [demofile-instance (new (.. js/window -demofile -DemoFile))
                  ;; game-events (. demofile-instance -gameEvents)
                  ]
              (.on demofile-instance "tickend" #(dispatch-tickend % demofile-instance))
              (.on demofile-instance "end" #(re-frame/dispatch [::demo-file-parsed]))
              (-> (. file arrayBuffer)
                  (.then #(.parse demofile-instance %))))
            {}))

(re-frame/reg-event-db
 ::demo-file-parsed
 (fn-traced [db event]
            (assoc db :demo-file-parsed true)))