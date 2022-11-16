;FACTS ASSERTED BY GAME INPUT
(deftemplate BLINKY
    (slot edible(type SYMBOL))
    (slot anotherGhostNotEdible(type SYMBOL))
    (slot minDistancePPill (type INTEGER))
    (slot pacmanDistanceToPPill (type INTEGER))
    (slot distanceToPacman (type INTEGER))
    (slot remainingTime (type INTEGER))
    (slot position (type INTEGER))
    (slot hasObjective (type SYMBOL))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
)

(deftemplate INKY
    (slot edible(type SYMBOL))
    (slot minDistancePPill (type INTEGER))
    (slot pacmanDistanceToPPill (type INTEGER))
    (slot distanceToPacman (type INTEGER))
    (slot remainingTime (type INTEGER))
    (slot position (type INTEGER))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
)

(deftemplate PINKY
    (slot edible(type SYMBOL))
    (slot minDistancePPill(type INTEGER))
    (slot pacmanDistanceToPPill (type INTEGER))
    (slot distanceToPacman (type INTEGER))
    (slot remainingTime(type INTEGER))
    (slot position (type INTEGER))
    (slot hasObjective (type SYMBOL))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
)

(deftemplate SUE
    (slot edible(type SYMBOL))
    (slot minDistancePPill(type INTEGER))
    (slot pacmanDistanceToPPill (type INTEGER))
    (slot distanceToPacman(type INTEGER))
    (slot remainingTime(type INTEGER))
    (slot position (type INTEGER))
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
;1
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

;2
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

;3
(defrule BLINKYsearchsObjective
    (BLINKY (distanceToPacman ?dp) (RANGE ?r)) (test (<= ?dp ?r))
    =>
    (assert (ACTION (id BLINKYsearchsObjective) (info "Searchs objective") (priority 100)))
)
(defrule PINKYsearchsObjective
    (BLINKY (distanceToPacman ?dp) (RANGE ?r)) (test (<= ?dp ?r))
    =>
    (assert (ACTION (id PINKYsearchsObjective) (info "Searchs objective") (priority 100)))
)

;4
(defrule BLINKYgoesToObjective
    (BLINKY (hasObjective true))
    =>
    (assert (ACTION (id BLINKYgoesToObjective) (info "Goes to objective") (priority 100)))
)
(defrule PINKYgoesToObjective
    (PINKY (hasObjective true))
    =>
    (assert (ACTION (id PINKYgoesToObjective) (info "Goes to objective") (priority 100)))
)

;5
(defrule INKYchasesOutOfRange
    (INKY (distanceToPacman ?dp) (RANGE ?r)) (test (<= ?dp ?r))
    =>
    (assert (ACTION (id INKYchases) (info "Chases") (priority 100)))
)
(defrule SUEchasesOutOfRange
    (SUE (distanceToPacman ?dp) (RANGE ?r)) (test (<= ?dp ?r))
    =>
    (assert (ACTION (id SUEchases) (info "Chases") (priority 100)))
)

;6
(defrule BLINKYarrivesFirstToPP
    (BLINKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
    (BLINKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (<= ?d ?pac))
    =>
    (assert (ACTION (id BLINKYgoesToPP) (info "Goes to PP first") (priority 80)))
)
(defrule PINKYarrivesFirstToPP
    (PINKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
    (PINKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (<= ?d ?pac))
    =>
    (assert (ACTION (id PINKYgoesToPP) (info "Goes to PP first") (priority 80)))
)
(defrule INKYarrivesFirstToPP
    (INKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
    (INKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (<= ?d ?pac))
    =>
    (assert (ACTION (id INKYgoesToPP) (info "Goes to PP first") (priority 80)))
)
(defrule SUEarrivesFirstToPP
    (SUE (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
    (SUE (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (<= ?d ?pac))
    =>
    (assert (ACTION (id SUEgoesToPP) (info "Goes to PP first") (priority 80)))
)

;7
(defrule BLINKYdoesNotArriveFirstToPP
    (BLINKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
    (BLINKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (> ?d ?pac))
    =>
    (assert (ACTION (id BLINKYrunsAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
)
(defrule PINKYdoesNotArriveFirstToPP
    (PINKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
    (PINKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (> ?d ?pac))
    =>
    (assert (ACTION (id PINKYrunsAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
)
(defrule INKYdoesNotArriveFirstToPP
    (INKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
    (INKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (> ?d ?pac))
    =>
    (assert (ACTION (id INKYrunsAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
)
(defrule SUEdoesNotArriveFirstToPP
    (SUE (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
    (SUE (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (> ?d ?pac))
    =>
    (assert (ACTION (id SUErunsAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
)