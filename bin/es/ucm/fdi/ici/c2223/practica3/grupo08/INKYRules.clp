;FACTS ASSERTED BY GAME INPUT
(deftemplate BLINKY
    (slot edible(type SYMBOL))
    (slot anotherGhostNotEdible(type SYMBOL))
    (slot anotherGhostInLair(type SYMBOL))
    (slot anotherGhostEdible (type SYMBOL))
    (slot arrivesFirstToPP (type SYMBOL))
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
    (slot arrivesFirstToPP (type SYMBOL))
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
    (slot arrivesFirstToPP (type SYMBOL))
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
    (slot arrivesFirstToPP (type SYMBOL))
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
)

;ACTION FACTS
(deftemplate ACTION (slot id) (slot info (default "")) (slot priority (type NUMBER)) )

;RULES
;1
(defrule INKYrunsAway
    (INKY (edible true))
    =>
    (assert 
        (ACTION (id INKYrunAway) (info "Comestible ----> Huir") (priority 30))
    )
)

;2
(defrule INKYrunsAwayToGhost
    (INKY (edible true) (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id INKYrunAwayToGhost) (info "RunAway to ghost") (priority 60))
    )
)

;5
(defrule INKYchasesOutOfRange
    (INKY (edible false)) 
    (INKY (distanceToPacman ?d) (RANGE ?r)) (test (> ?d ?r))
    =>
    (assert (ACTION (id INKYchases) (info "Chases out of range") (priority 100)))
)

;6
(defrule INKYarrivesFirstToPP
    (INKY (edible false) (arrivesFirstToPP true) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) 
    (test (<= ?dist ?maxDist))
    =>
    (assert (ACTION (id INKYgoesToPP) (info "Goes to PP first") (priority 80)))
)

;7
(defrule INKYdoesNotArriveFirstToPP
    (INKY (edible false) (arrivesFirstToPP false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) 
    (test (<= ?dist ?maxDist))
    =>
    (assert (ACTION (id INKYrunAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
)

;8
(defrule INKYrunsToLair
    (INKY (edible true) (anotherGhostInLair true))
    =>
    (assert (ACTION (id INKYgoesToLair) (info "Goes to lair") (priority 50)))
)

;9
(defrule INKYchases
    (INKY (edible false))
    =>
    (assert (ACTION (id INKYchases) (info "Chases MSPacman") (priority 10)))
)

;10
(defrule INKYprotectEdibles
    (INKY (edible false) (anotherGhostEdible true))
    =>
    (assert (ACTION (id INKYrunTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
)

;11
(defrule INKYgoesToCornerToDie
    (INKY (edible true) (anotherGhostEdible true) (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) 
    (test (<= ?dist ?deathDist))
    =>
    (assert (ACTION (id INKYgoesToPP) (info "Goes to PP") (priority 80)))
)

;12
(defrule INKYturnsNonEdibleBeforePacmanReaches
    (INKY (edible true) (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) 
    (test (<= ?dist ?maxDist))
    =>
    (assert (ACTION (id INKYchases) (info "Chases MSPacman") (priority 90)))
)

;13
(defrule INKYtrapsPacmanInCorner
    (INKY (edible false) (pacmanInCorner true))
    =>
    (assert (ACTION (id INKYtrapCorner) (info "Traps in corner") (priority 90)))
)

;14
(defrule INKYinterceptsJunctionBeforePP
    (INKY (edible false) (intercept true))
    =>
    (assert (ACTION (id INKYchases) (info "Chases MSPacman") (priority 20)))
)
