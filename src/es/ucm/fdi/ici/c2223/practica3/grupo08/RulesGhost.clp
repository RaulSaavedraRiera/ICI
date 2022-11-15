;FACTS ASSERTED BY GAME INPUT
(deftemplate BLINKY
    (slot edible(type SYMBOL))
    (slot anotherGhostNotEdible(type SYMBOL))
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
(defrule PINKYrunsAway
    (PINKY (edible true))
    =>
    (assert 
        (ACTION (id PINKYrunsAway) (info "Comestible ----> Huir") (priority 30))
    )
)
(defrule INKYrunsAway
    (INKY (edible true))
    =>
    (assert 
        (ACTION (id INKYrunsAway) (info "Comestible ----> Huir") (priority 30))
    )
)
(defrule SUErunsAway
    (SUE (edible true))
    =>
    (assert 
        (ACTION (id SUErunsAway) (info "Comestible ----> Huir") (priority 30))
    )
)

(defrule BLINKYrunsAwayToGhost
    (BLINKY (edible true))
    (BLINKY (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id BLINKYrunsAwayToGhost) (info "RunAway to ghost") (priority 50))
    )
)
(defrule PINKYrunsAwayToGhost
    (PINKY (edible true))
    (PINKY (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id PINKYrunsAwayToGhost) (info "RunAway to ghost") (priority 50))
    )
)
(defrule INKYrunsAwayToGhost
    (INKY (edible true))
    (INKY (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id INKYrunsAwayToGhost) (info "RunAway to ghost") (priority 50))
    )
)
(defrule SUErunsAwayToGhost
    (SUE (edible true))
    (SUE (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id SUErunsAwayToGhost) (info "RunAway to ghost") (priority 50))
    )
)

(defrule BLINKYsearchsObjective
    (BLINKY (distanceToPacman ?dp) (RANGE ?r)) (test (< ?dp r))
    =>
    (assert (ACTION (id BLINKYsearchsObjective) (info "Searchs objective") (priority 100)))
)
(defrule PINKYsearchsObjective
    (BLINKY (distanceToPacman ?dp) (RANGE ?r)) (test (< ?dp r))
    =>
    (assert (ACTION (id PINKYsearchsObjective) (info "Searchs objective") (priority 100)))
)
(defrule INKYchases
    (INKY (distanceToPacman ?dp) (RANGE ?r)) (test (< ?dp r))
    =>
    (assert (ACTION (id INKYchases) (info "Chases") (priority 100)))
)
(defrule SUEchases
    (SUE (distanceToPacman ?dp) (RANGE ?r)) (test (< ?dp r))
    =>
    (assert (ACTION (id SUEchases) (info "Chases") (priority 100)))
)
; ---

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