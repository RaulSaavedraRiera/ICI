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
(defrule PINKYrunsAway
    (PINKY (edible true))
    =>
    (assert 
        (ACTION (id PINKYrunAway) (info "Comestible ----> Huir") (priority 30))
    )
)

;2
(defrule PINKYrunsAwayToGhost
    (PINKY (edible true) (anotherGhostNotEdible true))
    =>
    (assert 
        (ACTION (id PINKYrunAwayToGhost) (info "RunAway to ghost") (priority 60))
    )
)

; 3
(defrule PINKYsearchsObjective
    (PINKY (edible false) (hasObjective false))
    (PINKY (distanceToPacman ?d) (RANGE ?r)) (test (> ?d ?r))
    =>
    (assert (ACTION (id PINKYsearchsObjective) (info "Searchs objective") (priority 100)))
)

;4
(defrule PINKYgoesToObjective
    (PINKY (edible false) (hasObjective true))
    =>
    (assert (ACTION (id PINKYgoesToObjective) (info "Goes to objective") (priority 100)))
)

;6
(defrule PINKYarrivesFirstToPP
    (PINKY (edible false) (arrivesFirstToPP true) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) 
    (test (<= ?dist ?maxDist))
    =>
    (assert (ACTION (id PINKYgoesToPP) (info "Goes to PP first") (priority 80)))
)

;7
(defrule PINKYdoesNotArriveFirstToPP
    (PINKY (edible false) (arrivesFirstToPP false) (pacmanDistanceToPPill ?dist) (PACMAN_MAX_DIST_TO_PP ?maxDist)) 
    (test (<= ?dist ?maxDist))
    =>
    (assert (ACTION (id PINKYrunAway) (info "Runs away from PP") (priority 90))) ;prioridad menor que la de estar fuera de rango (100)
)

;8
(defrule PINKYrunsToLair
    (PINKY (edible true) (anotherGhostInLair true))
    =>
    (assert (ACTION (id PINKYgoesToLair) (info "Goes to lair") (priority 50)))
)

;9
(defrule PINKYchases
    (PINKY (edible false))
    =>
    (assert (ACTION (id PINKYchases) (info "Chases MSPacman") (priority 10)))
)

;10
(defrule PINKYprotectEdibles
    (PINKY (edible false) (anotherGhostEdible true))
    =>
    (assert (ACTION (id PINKYrunTowardsEdibleGhost) (info "Run to edible ghost") (priority 80)))
)

;11
(defrule PINKYgoesToCornerToDie
    (PINKY (edible true) (anotherGhostEdible true) (distanceToPacman ?dist) (SURE_DEATH_DISTANCE ?deathDist)) 
    (test (<= ?dist ?deathDist))
    =>
    (assert (ACTION (id PINKYgoesToPP) (info "Goes to PP") (priority 80)))
)

;12
(defrule PINKYturnsNonEdibleBeforePacmanReaches
    (PINKY (edible true) (distanceToPacmanWithSpeed ?dist) (remainingTime ?maxDist)) 
    (test (<= ?dist ?maxDist))
    =>
    (assert (ACTION (id PINKYchases) (info "Chases MSPacman") (priority 90)))
)

;13
(defrule PINKYtrapsPacmanInCorner
    (PINKY (edible false) (pacmanInCorner true))
    =>
    (assert (ACTION (id PINKYtrapCorner) (info "Traps in corner") (priority 90)))
)

;14
(defrule PINKYinterceptsJunctionBeforePP
    (PINKY (edible false) (intercept true))
    =>
    (assert (ACTION (id PINKYchases) (info "Chases MSPacman") (priority 20)))
)

;15
(defrule PINKYarrivestToObjective
    (PINKY (edible false) (hasObjective true) (distanceToObjective ?d))
    (test (<= ?d "4"))
    =>
    (assert (ACTION (id PINKYsearchsObjective) (info "Searchs objective") (priority 110)))
)
