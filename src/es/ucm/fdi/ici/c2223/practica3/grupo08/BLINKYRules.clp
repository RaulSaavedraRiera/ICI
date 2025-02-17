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
(defrule BLINKYrunsAway
    (BLINKY (edible true))
    =>
    (assert 
        (ACTION (id BLINKYrunAway) (info "Comestible ----> Huir") (priority 30))
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

; 3
(defrule BLINKYsearchsObjective
    (BLINKY (edible false) (hasObjective false))
    (BLINKY (distanceToPacman ?d) (RANGE ?r)) (test (> ?d ?r))
    =>
    (assert (ACTION (id BLINKYsearchsObjective) (info "Searchs objective") (priority 100)))
)

;4
(defrule BLINKYgoesToObjective
    (BLINKY (edible false) (hasObjective true))
    =>
    (assert (ACTION (id BLINKYgoesToObjective) (info "Goes to objective") (priority 100)))
)

;6
(defrule BLINKYarrivesFirstToPP
    (BLINKY (edible false) (arrivesFirstToPP true) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) 
    (test (<= ?dist ?maxDist))
    =>
    (assert (ACTION (id BLINKYgoesToPP) (info "Goes to PP first") (priority 80)))
)

;7
(defrule BLINKYdoesNotArriveFirstToPP
    (BLINKY (edible false) (arrivesFirstToPP false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) 
    (test (<= ?dist ?maxDist))
    =>
    (assert (ACTION (id BLINKYrunAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
)

;8
(defrule BLINKYrunsToLair
    (BLINKY (edible true) (anotherGhostInLair true))
    =>
    (assert (ACTION (id BLINKYgoesToLair) (info "Goes to lair") (priority 50)))
)

;9
(defrule BLINKYchases
    (BLINKY (edible false))
    =>
    (assert (ACTION (id BLINKYchases) (info "Chases MSPacman") (priority 10)))
)

;10
(defrule BLINKYprotectEdibles
    (BLINKY (edible false) (anotherGhostEdible true))
    =>
    (assert (ACTION (id BLINKYrunTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
)

;11
(defrule BLINKYgoesToCornerToDie
    (BLINKY (edible true) (anotherGhostEdible true) (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) 
    (test (<= ?dist ?deathDist))
    =>
    (assert (ACTION (id BLINKYgoesToPP) (info "Goes to PP") (priority 80)))
)

;12
(defrule BLINKYturnsNonEdibleBeforePacmanReaches
    (BLINKY (edible true) (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) 
    (test (<= ?dist ?maxDist))
    =>
    (assert (ACTION (id BLINKYchases) (info "Chases MSPacman") (priority 90)))
)

;13
(defrule BLINKYtrapsPacmanInCorner
    (BLINKY (edible false) (pacmanInCorner true))
    =>
    (assert (ACTION (id BLINKYtrapCorner) (info "Traps in corner") (priority 90)))
)

;14
(defrule BLINKYinterceptsJunctionBeforePP
    (BLINKY (edible false) (intercept true))
    =>
    (assert (ACTION (id BLINKYchases) (info "Chases MSPacman") (priority 20)))
)

;15
(defrule BLINKYarrivestToObjective
    (BLINKY (edible false) (hasObjective true) (distanceToObjective ?d))
    (test (<= ?d "4"))
    =>
    (assert (ACTION (id BLINKYsearchsObjective) (info "Searchs objective") (priority 110)))
)
