(ns reframe-csgo-demofiles.views
  (:require
   [re-frame.core :as re-frame]
   [re-com.core :as re-com :refer [at]]
   [reframe-csgo-demofiles.events :as events]
   [reframe-csgo-demofiles.routes :as routes]
   [reframe-csgo-demofiles.subs :as subs]))


; cs:go view

;; TODO Review the nested components

(defn demo-file-details []
  [re-com/v-box
   :children [[re-com/h-box
               :children [[re-com/label :label "Date/Time:"]
                          [re-com/label :label "2022-01-25T14:55:00.000Z"]]]
              [re-com/h-box
               :children [[re-com/label :label (str "Players:"
                                                    ;; @(re-frame/subscribe [::subs/player-names])
                                                    )]
                          ]]
              [re-com/h-box
               :children [[re-com/label :label (str "Current tick:" 
                                                    @(re-frame/subscribe [::subs/current-tick])
                                                    )]
                          ]]]])

(defn csgo-demo-file-panel []
  (let [demo-file-parsed (re-frame/subscribe [::subs/demo-file-parsed])]
    (fn []
      [re-com/v-box
       :children [[:input {:type "file"
                           :on-change #(re-frame/dispatch [::events/demo-file-selected (-> % .-target .-files (aget 0))])}]
                  (when @demo-file-parsed
                    [demo-file-details])]])))

;; home

(defn home-title []
  (let [name (re-frame/subscribe [::subs/app-name])]
    [re-com/v-box
     :children [[re-com/title
                 :src   (at)
                 :label (str @name)
                 :level :level1]
                [csgo-demo-file-panel]]]))

(defn link-to-about-page []
  [re-com/hyperlink
   :src      (at)
   :label    "go to About Page"
   :on-click #(re-frame/dispatch [::events/navigate :about])])

(defn home-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[home-title]
              [link-to-about-page]]])


(defmethod routes/panels :home-panel [] [home-panel])

;; about

(defn about-title []
  [re-com/title
   :src   (at)
   :label "About"
   :level :level1])

(defn link-to-home-page []
  [re-com/hyperlink
   :src      (at)
   :label    "go to Home Page"
   :on-click #(re-frame/dispatch [::events/navigate :home])])

(defn about-panel []
  [re-com/v-box
   :src      (at)
   :gap      "1em"
   :children [[about-title]
              [link-to-home-page]]])

(defmethod routes/panels :about-panel [] [about-panel])

;; main

(defn main-panel []
  (let [active-panel (re-frame/subscribe [::subs/active-panel])]
    [re-com/v-box
     :src      (at)
     :height   "100%"
     :children [(routes/panels @active-panel)]]))
