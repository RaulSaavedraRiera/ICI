;FACTS ASSERTED BY GAME INPUT
(deftemplate BLINKY
    (slot edible(type SYMBOL))
    (slot anotherGhostNotEdible(type SYMBOL))
    (slot anotherGhostInLair(type SYMBOL))
    (slot anotherGhostEdible (type SYMBOL))
    (slot minDistancePPill(type INTEGER))
    (slot pacmanDistanceToPPill (type INTEGER))
    (slot distanceToPacman (type INTEGER))
    (slot distanceToPacmanWithSpeed (type INTEGER))
    (slot distanceToLair (type INTEGER))
    (slot remainingTime(type INTEGER))
    (slot hasObjective (type SYMBOL))
    (slot distanceToObjective (type INTEGER))
    (slot pacmanInCorner (type SYMBOL))
    (slot intercept (type SYMBOL))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
    (slot SURE_DEATH_DISTANCE(type INTEGER))
)

(deftemplate PINKY
    (slot edible(type SYMBOL))
    (slot anotherGhostNotEdible(type SYMBOL))
    (slot anotherGhostInLair(type SYMBOL))
    (slot anotherGhostEdible (type SYMBOL))
    (slot minDistancePPill(type INTEGER))
    (slot pacmanDistanceToPPill (type INTEGER))
    (slot distanceToPacman (type INTEGER))
    (slot distanceToPacmanWithSpeed (type INTEGER))
    (slot distanceToLair (type INTEGER))
    (slot remainingTime(type INTEGER))
    (slot hasObjective (type SYMBOL))
    (slot distanceToObjective (type INTEGER))
    (slot pacmanInCorner (type SYMBOL))
    (slot intercept (type SYMBOL))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
    (slot SURE_DEATH_DISTANCE(type INTEGER))
)

(deftemplate INKY
    (slot edible(type SYMBOL))
    (slot anotherGhostNotEdible(type SYMBOL))
    (slot anotherGhostInLair(type SYMBOL))
    (slot anotherGhostEdible (type SYMBOL))
    (slot minDistancePPill (type INTEGER))
    (slot pacmanDistanceToPPill (type INTEGER))
    (slot distanceToPacman (type INTEGER))
    (slot distanceToPacmanWithSpeed (type INTEGER))
    (slot distanceToLair (type INTEGER))
    (slot remainingTime (type INTEGER))
    (slot pacmanInCorner (type SYMBOL))
    (slot intercept (type SYMBOL))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
    (slot SURE_DEATH_DISTANCE(type INTEGER))
)

(deftemplate SUE
    (slot edible(type SYMBOL))
    (slot anotherGhostNotEdible(type SYMBOL))
    (slot anotherGhostInLair(type SYMBOL))
    (slot anotherGhostEdible (type SYMBOL))
    (slot minDistancePPill(type INTEGER))
    (slot pacmanDistanceToPPill (type INTEGER))
    (slot distanceToPacman(type INTEGER))
    (slot distanceToPacmanWithSpeed (type INTEGER))
    (slot distanceToLair (type INTEGER))
    (slot remainingTime(type INTEGER))
    (slot chasingTime (type INTEGER))
    (slot pacmanInCorner (type SYMBOL))
    (slot intercept (type SYMBOL))
    ;CONSTANTS
    (slot RANGE (type INTEGER))
    (slot PACMAN_MAX_DIST_TO_PP (type INTEGER))
    (slot SAFETY_DISTANCE_WHEN_EDIBLE (type INTEGER))
    (slot SURE_DEATH_DISTANCE(type INTEGER))
    (slot ORBITING_DISTANCE (type INTEGER))
    (slot CHASING_TIME_LIMIT (type INTEGER))
)

;ACTION FACTS
(deftemplate ACTION (slot id) (slot info (default "")) (slot priority (type NUMBER)) )

;RULES
;1
(defrule SUErunsAway
    (SUE (edible true))
    =>
    (assert 
        (ACTION (id SUErunAway) (info "Comestible ----> Huir") (priority 30))
    )
)

;2
(defrule SUErunsAwayToGhost
    (SUE (edible true) (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id SUErunAwayToGhost) (info "RunAway to ghost") (priority 60))
    )
)

;5
(defrule SUEchasesOutOfRange
    (SUE (edible false))
    (SUE (distanceToPacman ?d) (RANGE ?r)) (test (> ?d ?r))
    =>
    (assert (ACTION (id SUEchases) (info "Chases out of range") (priority 100)))
)

; ;6
; (defrule SUEarrivesFirstToPP
;     (SUE (edible false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist) (minDistancePPill ?d)) 
;     (test (<= ?dist ?maxDist))
;     (test (<= ?d ?dist))
;     =>
;     (assert (ACTION (id SUEgoesToPP) (info "Goes to PP first") (priority 80)))
; )

; ;7
; (defrule SUEdoesNotArriveFirstToPP
;     (SUE (edible false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist) (minDistancePPill ?d)) 
;     (test (<= ?dist ?maxDist))
;     (test (> ?d ?dist))
;     =>
;     (assert (ACTION (id SUErunAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
; )

;8
(defrule SUErunsToLair
    (SUE (edible true) (anotherGhostInLair true))
    =>
    (assert (ACTION (id SUEgoesToLair) (info "Goes to lair") (priority 50)))
)

;9
(defrule SUEchases
    (SUE (edible false))
    =>
    (assert (ACTION (id SUEchases) (info "Chases MSPacman") (priority 10)))
)

;10
(defrule SUEprotectEdibles
    (SUE (edible false) (anotherGhostEdible true))
    =>
    (assert (ACTION (id SUErunTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
)

;11
(defrule SUEgoesToCornerToDie
    (SUE (edible true) (anotherGhostEdible true) (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) 
    (test (<= ?dist ?deathDist))
    =>
    (assert (ACTION (id SUEgoesToPP) (info "Goes to PP") (priority 80)))
)

;12
(defrule SUEturnsNonEdibleBeforePacmanReaches
    (SUE (edible true) (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) 
    (test (<= ?dist ?maxDist))
    =>
    (assert (ACTION (id SUEchases) (info "Chases MSPacman") (priority 90)))
)

;13
(defrule SUEtrapsPacmanInCorner
    (SUE (edible false) (pacmanInCorner true))
    =>
    (assert (ACTION (id SUEtrapCorner)))
)

;14
(defrule SUEinterceptsJunctionBeforePP
    (SUE (edible false) (intercept true))
    =>
    (assert (ACTION (id SUEchases)))
)
