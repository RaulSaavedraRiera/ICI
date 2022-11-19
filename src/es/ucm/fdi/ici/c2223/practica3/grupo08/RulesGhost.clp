;FACTS ASSERTED BY GAME INPUT
(deftemplate BLINKY
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
    (slot minGhostTimeUntilFree (type INTEGER))
    (slot pacmanInCorner (type SYMBOL))
    (slot position (type INTEGER))
    (slot hasObjective (type SYMBOL))
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
    (slot minGhostTimeUntilFree (type INTEGER))
    (slot pacmanInCorner (type SYMBOL))
    (slot position (type INTEGER))
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
    (slot minGhostTimeUntilFree (type INTEGER))
    (slot pacmanPosition (type INTEGER))
    (slot position (type INTEGER))
    (slot hasObjective (type SYMBOL))
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
    (slot minGhostTimeUntilFree (type INTEGER))
    (slot position (type INTEGER))
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
(deftemplate ACTION
    (slot id) (slot info (default "")) (slot priority (type NUMBER)) 
)

;RULES
;1
(defrule BLINKYrunsAway
    (BLINKY (edible true))
    =>
    (assert 
        (ACTION (id BLINKYrunAway) (info "Comestible ----> Huir") (priority 30))
    )
)
(defrule PINKYrunsAway
    (PINKY (edible true))
    =>
    (assert 
        (ACTION (id PINKYrunAway) (info "Comestible ----> Huir") (priority 30))
    )
)
(defrule INKYrunsAway
    (INKY (edible true))
    =>
    (assert 
        (ACTION (id INKYrunAway) (info "Comestible ----> Huir") (priority 30))
    )
)
(defrule SUErunsAway
    (SUE (edible true))
    =>
    (assert 
        (ACTION (id SUErunAway) (info "Comestible ----> Huir") (priority 30))
    )
)

;2
(defrule BLINKYrunsAwayToGhost
    (BLINKY (edible true) (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id BLINKYrunAwayToGhost) (info "RunAway to ghost") (priority 60))
    )
)
(defrule PINKYrunsAwayToGhost
    (PINKY (edible true) (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id PINKYrunAwayToGhost) (info "RunAway to ghost") (priority 60))
    )
)
(defrule INKYrunsAwayToGhost
    (INKY (edible true) (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id INKYrunAwayToGhost) (info "RunAway to ghost") (priority 60))
    )
)
(defrule SUErunsAwayToGhost
    (SUE (edible true) (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id SUErunAwayToGhost) (info "RunAway to ghost") (priority 60))
    )
)

; 3
(defrule BLINKYsearchsObjective
    (BLINKY (edible false) (distanceToPacman ?d))
    (test (<= ?d 50))
    =>
    (assert (ACTION (id BLINKYsearchsObjective) (info "Searchs objective") (priority 100)))
)
(defrule PINKYsearchsObjective
    (PINKY (edible false) (distanceToPacman ?d))
    (test (<= ?d 50))
    =>
    (assert (ACTION (id PINKYsearchsObjective) (info "Searchs objective") (priority 100)))
)

;4
(defrule BLINKYgoesToObjective
    (BLINKY (edible false) (hasObjective true))
    =>
    (assert (ACTION (id BLINKYgoesToObjective) (info "Goes to objective") (priority 100)))
)
(defrule PINKYgoesToObjective
    (PINKY (edible false) (hasObjective true))
    =>
    (assert (ACTION (id PINKYgoesToObjective) (info "Goes to objective") (priority 100)))
)

;5
(defrule INKYchasesOutOfRange
    (INKY (edible false) (distanceToPacman ?d)) (test (<= ?d 50))
    =>
    (assert (ACTION (id INKYchases) (info "Chases") (priority 100)))
)
(defrule SUEchasesOutOfRange
    (SUE (edible false) (distanceToPacman ?d)) (test (<= ?d 50))
    =>
    (assert (ACTION (id SUEchases) (info "Chases") (priority 100)))
)

; ;6
; (defrule BLINKYarrivesFirstToPP
;     (BLINKY (edible false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist) (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) 
;     (test (<= ?dist ?maxDist))
;     (test (<= ?d ?pac))
;     =>
;     (assert (ACTION (id BLINKYgoesToPP) (info "Goes to PP first") (priority 80)))
; )
; (defrule PINKYarrivesFirstToPP
;     (PINKY (edible false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist) (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) 
;     (test (<= ?dist ?maxDist))
;     (test (<= ?d ?pac))
;     =>
;     (assert (ACTION (id PINKYgoesToPP) (info "Goes to PP first") (priority 80)))
; )
; (defrule INKYarrivesFirstToPP
;     (INKY (edible false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist) (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) 
;     (test (<= ?dist ?maxDist))
;     (test (<= ?d ?pac))
;     =>
;     (assert (ACTION (id INKYgoesToPP) (info "Goes to PP first") (priority 80)))
; )
; (defrule SUEarrivesFirstToPP
;     (SUE (edible false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist) (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) 
;     (test (<= ?dist ?maxDist))
;     (test (<= ?d ?pac))
;     =>
;     (assert (ACTION (id SUEgoesToPP) (info "Goes to PP first") (priority 80)))
; )

; ;7
; (defrule BLINKYdoesNotArriveFirstToPP
;     (BLINKY (edible false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist) (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) 
;     (test (<= ?dist ?maxDist))
;     (test (> ?d ?pac))
;     =>
;     (assert (ACTION (id BLINKYrunAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
; )
; (defrule PINKYdoesNotArriveFirstToPP
;     (PINKY (edible false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist) (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) 
;     (test (<= ?dist ?maxDist))
;     (test (> ?d ?pac))
;     =>
;     (assert (ACTION (id PINKYrunAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
; )
; (defrule INKYdoesNotArriveFirstToPP
;     (INKY (edible false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist) (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) 
;     (test (<= ?dist ?maxDist))
;     (test (> ?d ?pac))
;     =>
;     (assert (ACTION (id INKYrunAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
; )
; (defrule SUEdoesNotArriveFirstToPP
;     (SUE (edible false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist) (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) 
;     (test (<= ?dist ?maxDist))
;     (test (> ?d ?pac))
;     =>
;     (assert (ACTION (id SUErunAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
; )

; ;8
; (defrule BLINKYrunsToLair
;     (BLINKY (edible true) (anotherGhostInLair true))
;     =>
;     (assert (ACTION (id BLINKYgoesToLair) (info "Goes to lair") (priority 50)))
; )
; (defrule PINKYrunsToLair
;     (PINKY (edible true) (anotherGhostInLair true))
;     =>
;     (assert (ACTION (id PINKYgoesToLair) (info "Goes to lair") (priority 50)))
; )
; (defrule INKYrunsToLair
;     (INKY (edible true) (anotherGhostInLair true))
;     =>
;     (assert (ACTION (id INKYgoesToLair) (info "Goes to lair") (priority 50)))
; )
; (defrule SUErunsToLair
;     (SUE (edible true) (anotherGhostInLair true))
;     =>
;     (assert (ACTION (id SUEgoesToLair) (info "Goes to lair") (priority 50)))
; )

;9
(defrule BLINKYchases
    (BLINKY (edible false))
    =>
    (assert (ACTION (id BLINKYchases) (info "Chases MSPacman") (priority 10)))
)
(defrule PINKYchases
    (PINKY (edible false))
    =>
    (assert (ACTION (id PINKYchases) (info "Chases MSPacman") (priority 10)))
)
(defrule INKYchases
    (INKY (edible false))
    =>
    (assert (ACTION (id INKYchases) (info "Chases MSPacman") (priority 10)))
)
(defrule SUEchases
    (SUE (edible false))
    =>
    (assert (ACTION (id SUEchases) (info "Chases MSPacman") (priority 10)))
)

; ;10
; (defrule BLINKYprotectEdibles
;     (BLINKY (edible false) (anotherGhostEdible))
;     =>
;     (assert (ACTION (id BLINKYrunTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
; )

; (defrule INKYprotectEdibles
;     (INKY (edible false) (anotherGhostEdible))
;     =>
;     (assert (ACTION (id INKYrunTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
; )

; (defrule PINKYprotectEdibles
;     (PINKY (edible false) (anotherGhostEdible))
;     =>
;     (assert (ACTION (id PINKYrunTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
; )

; (defrule SUEprotectEdibles
;     (SUE (edible false) (anotherGhostEdible))
;     =>
;     (assert (ACTION (id SUErunTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
; )

; ;11
; (defrule BLINKYgoesToCornerToDie
;     (BLINKY (edible true) (anotherGhostEdible) (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) 
;     (test (<= ?dist ?deathDist))
;     =>
;     (assert (ACTION (id BLINKYgoesToPP) (info "Goes to PP") (priority 80)))
; )
; (defrule INKYgoesToCornerToDie
;     (INKY (edible true) (anotherGhostEdible) (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) 
;     (test (<= ?dist ?deathDist))
;     =>
;     (assert (ACTION (id INKYgoesToPP) (info "Goes to PP") (priority 80)))
; )
; (defrule PINKYgoesToCornerToDie
;     (PINKY (edible true) (anotherGhostEdible) (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) 
;     (test (<= ?dist ?deathDist))
;     =>
;     (assert (ACTION (id PINKYgoesToPP) (info "Goes to PP") (priority 80)))
; )
; (defrule SUEgoesToCornerToDie
;     (SUE (edible true) (anotherGhostEdible) (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) 
;     (test (<= ?dist ?deathDist))
;     =>
;     (assert (ACTION (id SUEgoesToPP) (info "Goes to PP") (priority 80)))
; )

; ;12
; (defrule BLINKYturnsNonEdibleBeforePacmanReaches
;     (BLINKY (edible true) (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) 
;     (test (<= ?dist ?maxDist))
;     =>
;     (assert (ACTION (id BLINKYchases) (info "Chases MSPacman") (priority 90)))
; )
; (defrule INKYturnsNonEdibleBeforePacmanReaches
;     (INKY (edible true) (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) 
;     (test (<= ?dist ?maxDist))
;     =>
;     (assert (ACTION (id INKYchases) (info "Chases MSPacman") (priority 90)))
; )
; (defrule PINKYturnsNonEdibleBeforePacmanReaches
;     (PINKY (edible true) (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) 
;     (test (<= ?dist ?maxDist))
;     =>
;     (assert (ACTION (id PINKYchases) (info "Chases MSPacman") (priority 90)))
; )
; (defrule SUEturnsNonEdibleBeforePacmanReaches
;     (SUE (edible true) (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) 
;     (test (<= ?dist ?maxDist))
;     =>
;     (assert (ACTION (id SUEchases) (info "Chases MSPacman") (priority 90)))
; )

; ;13
; (defrule BLINKYtrapsPacmanInCorner
;     (BLINKY (edible false) (pacmanInCorner true))
;     =>
;     (assert (ACTION (id BLINKYtrapCorner)))
; )
; (defrule INKYtrapsPacmanInCorner
;     (INKY (edible false) (pacmanInCorner true))
;     =>
;     (assert (ACTION (id INKYtrapCorner)))
; )
; (defrule PINKYtrapsPacmanInCorner
;     (PINKY (edible false) (pacmanInCorner true))
;     =>
;     (assert (ACTION (id PINKYtrapCorner)))
; )
; (defrule SUEtrapsPacmanInCorner
;     (SUE (edible false) (pacmanInCorner true))
;     =>
;     (assert (ACTION (id SUEtrapCorner)))
; )

; ;14
; (defrule BLINKYinterceptsJunctionBeforePP
;     (BLINKY (edible false) (intercept true))
;     =>
;     (assert (ACTION (id BLINKYchases)))
; )
; (defrule INKYinterceptsJunctionBeforePP
;     (INKY (edible false) (intercept true))
;     =>
;     (assert (ACTION (id INKYchases)))
; )
; (defrule PINKYinterceptsJunctionBeforePP
;     (PINKY (edible false) (intercept true))
;     =>
;     (assert (ACTION (id PINKYchases)))
; )
; (defrule SUEinterceptsJunctionBeforePP
;     (SUE (edible false) (intercept true))
;     =>
;     (assert (ACTION (id SUEchases)))
; )

; ;15
; (defrule BLINKYarrivestToObjective
;     (BLINKY (edible false) (distanceToObjective ?d))
;     (test (<= ?d 4))
;     =>
;     (assert BLINKYsearchsObjective)
; )
; (defrule PINKYarrivestToObjective
;     (PINKY (edible false) (distanceToObjective ?d))
;     (test (<= ?d 4))
;     =>
;     (assert PINKYsearchsObjective)
; )
