;FACTS ASSERTED BY GAME INPUT
(deftemplate BLINKY
    (slot edible(type SYMBOL))
    (slot minBLINKYDistancePPill (type INTEGER))
    (slot distanceToPacman (type INTEGER))
    (slot BLINKYremainingTime (type INTEGER))
    (slot BLINKYPosition (type INTEGER))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
)

(deftemplate INKY
    (slot edible(type SYMBOL))
    (slot minINKYDistancePPill (type INTEGER))
    (slot distanceToPacman (type INTEGER))
    (slot INKYremainingTime (type INTEGER))
    (slot INKYPosition (type INTEGER))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
)

(deftemplate PINKY
    (slot edible(type SYMBOL))
    (slot minPINKYDistancePPill(type INTEGER))
    (slot distanceToPacman (type INTEGER))
    (slot PINKYremainingTime(type INTEGER))
    (slot PINKYPosition (type INTEGER))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
)

(deftemplate SUE
    (slot edible(type SYMBOL))
    (slot minSUEDistancePPill(type INTEGER))
    (slot distanceToPacman(type INTEGER))
    (slot SUEremainingTime(type INTEGER))
    (slot SUEPosition (type INTEGER))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
)

;ACTION FACTS
(deftemplate ACTION
    (slot id) (slot info (default "")) (slot priority (type INTEGER)) 

)

;RULES
(defrule BLINKYrunsAway
    (BLINKY (edible true))
    =>
    (assert 
        (ACTION (id BLINKYrunsAway) (info "Comestible ----> Huir") (priority 30))
    )
)

