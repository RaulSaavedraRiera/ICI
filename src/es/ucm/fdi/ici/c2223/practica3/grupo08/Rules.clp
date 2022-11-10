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

(deftemplate PACMAN
    (slot distToLair(type INTEGER))
    (slot distToNearestCorner(type INTEGER))
    (slot distToNearestChasingNotBehind(type INTEGER))
    (slot distToNearestChasingAnyDir(type INTEGER))
    (slot distToNearestEdible(type INTEGER))
    (slot distToNearestPP(type INTEGER))
    (slot distToNearestPill(type INTEGER))
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

(defrule PACMANnearLair
    (MSPACMAN (distToLair < 50))
    =>
    (assert 
        (ACTION (id ...HuirCelda...) (info "Celda Cerca ---> Huir Celda") (priority 30))
    )
)

(defrule PACMANnearCorner
    (MSPACMAN (distToNearestCorner < 50))
    =>
    (assert 
        (ACTION (id ...HuirRincon...) (info "Rincon Cerca ---> Huir Rincon") (priority 30))
    )
)

(defrule PACMANnearChasingNotBehind
    (MSPACMAN (distToNearestChasingNotBehind < 50))
    =>
    (assert 
        (ACTION (id ...HuirNearestChasingNotBehind...) (info "Chasing Cerca y no Misma Dir ---> Huir Chasing") (priority 30))
    )
)

(defrule PACMANnearChasingAnyDir
    (MSPACMAN (distToNearestChasingAnyDir < 50))
    =>
    (assert 
        (ACTION (id ...HuirNearestChasingAnyDir...) (info "Chasing Cerca Misma Dir ---> Huir Chasing") (priority 30))
    )
)

(defrule PACMANnearEdible
    (MSPACMAN (distToNearestEdible < 50))
    =>
    (assert 
        (ACTION (id ...ChaseEdible...) (info "Edible Cerca ---> ChaseEdible") (priority 30))
    )
)

(defrule PACMANnearPP
    (MSPACMAN (distToNearestPP < 50))
    =>
    (assert 
        (...)
    )
)

(defrule PACMANnearPill
    (MSPACMAN (distToNearestPill < 50))
    =>
    (assert 
        (...)
    )
)