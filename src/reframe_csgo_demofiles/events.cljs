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


(defn parseDemoFile [^js demofile-instance buffer]

  (let [parsed (.parse demofile-instance buffer)]
    (js/console.log parsed)
    (re-frame/dispatch [::demo-file-parsed parsed])))

(re-frame/reg-event-db
 ::demo-file-selected
 (fn-traced [db [_ file]]
            (let [demofile-instance (new (.. js/window -demofile -DemoFile))]
              (-> (. file arrayBuffer)
                  (.then #(parseDemoFile demofile-instance %))))
            db))

(re-frame/reg-event-db
 ::demo-file-parsed
 (fn-traced [db [a parsed-file]]
            (js/console.log parsed-file)
            (assoc db :current-demo-file parsed-file)))
