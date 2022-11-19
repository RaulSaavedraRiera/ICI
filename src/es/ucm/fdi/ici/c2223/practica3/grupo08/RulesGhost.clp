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
; (defrule BLINKYrunsAwayToGhost
;     (BLINKY (edible true))
;     (BLINKY (anotherGhostNotEdible true))
;     =>
;     (assert 
;         (ACTION (id BLINKYrunsAwayToGhost) (info "RunAway to ghost") (priority 60))
;     )
; )
; (defrule PINKYrunsAwayToGhost
;     (PINKY (edible true))
;     (PINKY (anotherGhostNotEdible true))
;     =>
;     (assert 
;         (ACTION (id PINKYrunsAwayToGhost) (info "RunAway to ghost") (priority 60))
;     )
; )
; (defrule INKYrunsAwayToGhost
;     (INKY (edible true))
;     (INKY (anotherGhostNotEdible true))
;     =>
;     (assert 
;         (ACTION (id INKYrunsAwayToGhost) (info "RunAway to ghost") (priority 60))
;     )
; )
; (defrule SUErunsAwayToGhost
;     (SUE (edible true))
;     (SUE (anotherGhostNotEdible true))
;     =>
;     (assert 
;         (ACTION (id SUErunsAwayToGhost) (info "RunAway to ghost") (priority 60))
;     )
; )

;3
; (defrule BLINKYsearchsObjective
;     (BLINKY (edible false) (distanceToPacman ?d))
;     (test (<= ?d 50))
;     =>
;     (assert (ACTION (id BLINKYsearchsObjective) (info "Searchs objective") (priority 100)))
; )
; (defrule PINKYsearchsObjective
;     (PINKY (edible false) (distanceToPacman ?d))
;     (test (<= ?d 50))
;     =>
;     (assert (ACTION (id PINKYsearchsObjective) (info "Searchs objective") (priority 100)))
; )

; ;4
; (defrule BLINKYgoesToObjective
;     (BLINKY (edible false))
;     (BLINKY (hasObjective true))
;     =>
;     (assert (ACTION (id BLINKYgoesToObjective) (info "Goes to objective") (priority 100)))
; )
; (defrule PINKYgoesToObjective
;     (PINKY (edible false))
;     (PINKY (hasObjective true))
;     =>
;     (assert (ACTION (id PINKYgoesToObjective) (info "Goes to objective") (priority 100)))
; )

; ;5
; (defrule INKYchasesOutOfRange
;     (INKY (edible false) (distanceToPacman ?d))
;     (test (<= ?d 50))
;     =>
;     (assert (ACTION (id INKYchases) (info "Chases") (priority 100)))
; )
; (defrule SUEchasesOutOfRange
;     (SUE (edible false) (distanceToPacman ?d))
;     (test (<= ?d 50))
;     =>
;     (assert (ACTION (id SUEchases) (info "Chases") (priority 100)))
; )

; ;6
; (defrule BLINKYarrivesFirstToPP
;     (BLINKY (edible false))
;     (BLINKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
;     (BLINKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (<= ?d ?pac))
;     =>
;     (assert (ACTION (id BLINKYgoesToPP) (info "Goes to PP first") (priority 80)))
; )
; (defrule PINKYarrivesFirstToPP
;     (PINKY (edible false))
;     (PINKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
;     (PINKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (<= ?d ?pac))
;     =>
;     (assert (ACTION (id PINKYgoesToPP) (info "Goes to PP first") (priority 80)))
; )
; (defrule INKYarrivesFirstToPP
;     (INKY (edible false))
;     (INKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
;     (INKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (<= ?d ?pac))
;     =>
;     (assert (ACTION (id INKYgoesToPP) (info "Goes to PP first") (priority 80)))
; )
; (defrule SUEarrivesFirstToPP
;     (SUE (edible false))
;     (SUE (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
;     (SUE (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (<= ?d ?pac))
;     =>
;     (assert (ACTION (id SUEgoesToPP) (info "Goes to PP first") (priority 80)))
; )

; ;7
; (defrule BLINKYdoesNotArriveFirstToPP
;     (BLINKY (edible false))
;     (BLINKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
;     (BLINKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (> ?d ?pac))
;     =>
;     (assert (ACTION (id BLINKYrunsAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
; )
; (defrule PINKYdoesNotArriveFirstToPP
;     (PINKY (edible false))
;     (PINKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
;     (PINKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (> ?d ?pac))
;     =>
;     (assert (ACTION (id PINKYrunsAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
; )
; (defrule INKYdoesNotArriveFirstToPP
;     (INKY (edible false))
;     (INKY (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
;     (INKY (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (> ?d ?pac))
;     =>
;     (assert (ACTION (id INKYrunsAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
; )
; (defrule SUEdoesNotArriveFirstToPP
;     (SUE (edible false))
;     (SUE (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) (test (<= ?dist ?maxDist))
;     (SUE (minDistancePPill ?d) (pacmanDistanceToPPill ?pac)) (test (> ?d ?pac))
;     =>
;     (assert (ACTION (id SUErunsAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
; )

; ;8
; (defrule BLINKYrunsToLair
;     (BLINKY (edible true))
;     (BLINKY (anotherGhostInLair true))
;     =>
;     (assert (ACTION (id BLINKYgoesToLair) (info "Goes to lair") (priority 50)))
; )
; (defrule PINKYrunsToLair
;     (PINKY (edible true))
;     (PINKY (anotherGhostInLair true))
;     =>
;     (assert (ACTION (id PINKYgoesToLair) (info "Goes to lair") (priority 50)))
; )
; (defrule INKYrunsToLair
;     (INKY (edible true))
;     (INKY (anotherGhostInLair true))
;     =>
;     (assert (ACTION (id INKYgoesToLair) (info "Goes to lair") (priority 50)))
; )
; (defrule SUErunsToLair
;     (SUE (edible true))
;     (SUE (anotherGhostInLair true))
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
;     (BLINKY (edible false))
;     (BLINKY (anotherGhostEdible))
;     =>
;     (assert (ACTION (id BLINKYrunsTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
; )

; (defrule INKYprotectEdibles
;     (INKY (edible false))
;     (INKY (anotherGhostEdible))
;     =>
;     (assert (ACTION (id INKYrunsTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
; )

; (defrule PINKYprotectEdibles
;     (PINKY (edible false))
;     (PINKY (anotherGhostEdible))
;     =>
;     (assert (ACTION (id PINKYrunsTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
; )

; (defrule SUEprotectEdibles
;     (SUE (edible false))
;     (SUE (anotherGhostEdible))
;     =>
;     (assert (ACTION (id SUErunsTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
; )

; ;11
; (defrule BLINKYgoesToCornerToDie
;     (BLINKY (edible true))
;     (BLINKY (anotherGhostEdible))
;     (BLINKY (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) (test (<= ?dist ?deathDist))
;     =>
;     (assert (ACTION (id BLINKYgoesToPP) (info "Goes to PP") (priority 80)))
; )
; (defrule INKYgoesToCornerToDie
;     (INKY (edible true))
;     (INKY (anotherGhostEdible))
;     (INKY (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) (test (<= ?dist ?deathDist))
;     =>
;     (assert (ACTION (id INKYgoesToPP) (info "Goes to PP") (priority 80)))
; )
; (defrule PINKYgoesToCornerToDie
;     (PINKY (edible true))
;     (PINKY (anotherGhostEdible))
;     (PINKY (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) (test (<= ?dist ?deathDist))
;     =>
;     (assert (ACTION (id PINKYgoesToPP) (info "Goes to PP") (priority 80)))
; )
; (defrule SUEgoesToCornerToDie
;     (SUE (edible true))
;     (SUE (anotherGhostEdible))
;     (SUE (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) (test (<= ?dist ?deathDist))
;     =>
;     (assert (ACTION (id SUEgoesToPP) (info "Goes to PP") (priority 80)))
; )

; ;12
; (defrule BLINKYturnsNonEdibleBeforePacmanReaches
;     (BLINKY (edible true))
;     (BLINKY (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) (test (<= ?dist ?maxDist))
;     =>
;     (assert (ACTION (id BLINKYchases) (info "Chases MSPacman") (priority 90)))
; )
; (defrule INKYturnsNonEdibleBeforePacmanReaches
;     (INKY (edible true))
;     (INKY (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) (test (<= ?dist ?maxDist))
;     =>
;     (assert (ACTION (id INKYchases) (info "Chases MSPacman") (priority 90)))
; )
; (defrule PINKYturnsNonEdibleBeforePacmanReaches
;     (PINKY (edible true))
;     (PINKY (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) (test (<= ?dist ?maxDist))
;     =>
;     (assert (ACTION (id PINKYchases) (info "Chases MSPacman") (priority 90)))
; )
; (defrule SUEturnsNonEdibleBeforePacmanReaches
;     (SUE (edible true))
;     (SUE (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) (test (<= ?dist ?maxDist))
;     =>
;     (assert (ACTION (id SUEchases) (info "Chases MSPacman") (priority 90)))
; )

; ;13
; (defrule BLINKYtrapsPacmanInCorner
;     (BLINKY (edible false))
;     (BLINKY (pacmanInCorner true))
;     =>
;     (assert (ACTION (id BLINKYtrapCorner)))
; )
; (defrule INKYtrapsPacmanInCorner
;     (INKY (edible false))
;     (INKY (pacmanInCorner true))
;     =>
;     (assert (ACTION (id INKYtrapCorner)))
; )
; (defrule PINKYtrapsPacmanInCorner
;     (PINKY (edible false))
;     (PINKY (pacmanInCorner true))
;     =>
;     (assert (ACTION (id PINKYtrapCorner)))
; )
; (defrule SUEtrapsPacmanInCorner
;     (SUE (edible false))
;     (SUE (pacmanInCorner true))
;     =>
;     (assert (ACTION (id SUEtrapCorner)))
; )

; ;14
; (defrule BLINKYinterceptsJunctionBeforePP
;     (BLINKY (edible false))
;     (BLINKY (intercept true))
;     =>
;     (assert (ACTION (id BLINKYchases)))
; )
; (defrule INKYinterceptsJunctionBeforePP
;     (INKY (edible false))
;     (INKY (intercept true))
;     =>
;     (assert (ACTION (id INKYchases)))
; )
; (defrule PINKYinterceptsJunctionBeforePP
;     (PINKY (edible false))
;     (PINKY (intercept true))
;     =>
;     (assert (ACTION (id PINKYchases)))
; )
; (defrule SUEinterceptsJunctionBeforePP
;     (SUE (edible false))
;     (SUE (intercept true))
;     =>
;     (assert (ACTION (id SUEchases)))
; )
