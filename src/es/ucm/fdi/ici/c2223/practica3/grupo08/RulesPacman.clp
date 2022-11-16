;FACTS ASSERTED BY GAME INPUT

;falta obtener si se puede llegar PP, cuanto queda Ghost salga de Lair y poner todos los nombres correctos
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

;2< chasing ghosts cerca

;1 si hay mas de dos fantasmas agresivos cerca y se puede llegar a PP ve a PP
(defrule PacmanGoPPMultipleGhosts
(PACMAN (nearChasingGhosts ?g)) (test (<= 2 ?g))
    (PACMAN (PacmanCanReachPP))    
    =>
    (assert (ACTION (id ChasePowerPill) (info "2< Chasing ghost near Pacman && PP range -> Go PP") (priority 100)))
)

;se podria crear una nueva accion que sea comer en camino no haya fantasmas para ganar algo de tiempo
;2 si hay 3 fantasmas agresivos o mas y no se puede llegar a PP estas encerrado probablemente, come y muere
(defrule PacmanEatAll
 (PACMAN (nearChasingGhosts ?g)) (test (<= 3 ?g))
    (PACMAN (!PacmanCanReachPP))   
    =>
    (assert (ACTION (id ChasePill) (info " 3< Chasing ghost near Pacman && PP non range -> Eat") (priority 100)))
)

;habria que hacer que fuera del que no le persigue en misma direccion
;3 si hay mas de dos fantasmas agresivos cerca y no se puede llegar a PP huye
(defrule PacmanAwayGhosts
 (PACMAN (nearChasingGhosts ?g)) (test (== 2 ?g))
 (PACMAN (PacmanCanReachPP false))   
    =>
    (assert (ACTION (id RunAwayNearestChasingGhostNonFollow) (info "2 Chasing ghost near Pacman && PP non range -> Away") (priority 100)))
)

; a partir de aqui <=1 fantasmas agresivos, para este caso falta meter el casod e que te persiga uno pero se pueda comer alguno

;4 chasing ghost cerca no persigue y se puede llegar a pp
(defrule PacmanGoPPSingleGhost
    (PACMAN (nearChasingGhosts ?g)) (test (== 1 ?g))
    (PACMAN (ghostFollowsPacman false)) 
    (PACMAN (PacmanCanReachPP true))    
    =>
    (assert 
        (ACTION (id ChasePowerPill) (info "1 Chasing ghost near Pacman && non follow && PP range -> Go PP") (priority 90))
    )
)

;5 chasing ghost cerca no persigue y no se puede llegar a pp
(defrule PacmanAwaySingleGhost
    (PACMAN (nearChasingGhosts ?g)) (test (== 1 ?g))
    (PACMAN (ghostFollowsPacman false)) 
    (PACMAN (PacmanCanReachPP false))    
    =>
    (assert 
        (ACTION (id RunAwayNearestChasingGhostNonFollow) (info "1 Chasing ghost near Pacman && non follow && PP non range -> Away") (priority 90))
    )
)

;no se si es misma direccion sigue comiendo
;6 chasing ghost cerca persigue, es interseccion, cercaPP
(defrule PacmanDodgePPChasingGhost
    (PACMAN (nearChasingGhosts ?g)) (test (== 1 ?g))
    (PACMAN (ghostFollowsPacman true)) 
     (PACMAN (isInIntersection true)) 
       (PACMAN (PPNear true)) 
    =>
    (assert 
        (ACTION (id RunAwayPowerPill) (info "1 Chasing ghost near Pacman && follow && intersection && PP -> dodgePP") (priority 90))
    )
)


;7 chasing ghost cerca persigue, es interseccion, no cercaPP
(defrule PacmanGoWayMorePills
    (PACMAN (nearChasingGhosts ?g)) (test (== 1 ?g))
    (PACMAN (ghostFollowsPacman true)) 
    (PACMAN (isInIntersection true)) 
    (PACMAN (PPNear false)) 
    =>
    (assert 
        (ACTION (id ...bestWay) (info "1 Chasing ghost near Pacman && follow && intersection -> bestWay") (priority 90))
    )
)

;8 chasing ghost cerca persigue, no es interseccion
(defrule PacmanEatNextPillChasingGhostNear
    (PACMAN (nearChasingGhosts ?g)) (test (== 1 ?g))
    (PACMAN (ghostFollowsPacman true)) 
     (PACMAN (isInIntersection false)) 
    =>
    (assert 
        (ACTION (id ChasePill) (info "1 Chasing ghost near Pacman && follow && non intersection -> eatPill") (priority 90))
    )
)

;a partir de aqui no hay chasing ghosts cerca, ignoro que haya chasing ghosts cerca porque las que van con ellos tiene mas prioridad y siempre iran por delante

;para esta no estoy teniendo en cuenta cerca de lair o corners
;9 3< edible ghosts cerca
(defrule PacmanGoGroupEdibleGhosts
    (PACMAN (nearEdibleGhosts ?g)) (test (<= 3 ?g))
    =>
    (assert 
        (ACTION (id ChaseEdibleGroup) (info "3< edibleGhost near Pacman -> goGroup") (priority 80))
    )
)

;10 <2 edible ghosts
(defrule PacmanGoEdibleGhost
    (PACMAN (nearEdibleGhosts ?g)) (test (<= 2 ?g))
    =>
    (assert 
        (ACTION (id ChaseEdible) (info "<2 edibleGhost near Pacman -> goNearestGhost") (priority 70))
    )
)

;a partir de aqui no hay ningun ghost cerca

;11 evitar PP cercana
(defrule PacmanDodgePPNoChasingGhost
    (PACMAN (nearEdibleGhosts ?g)) (test (== 0 ?g))
     (PACMAN (distanceToNearestPP ?dp) (NEAR_PP_DISTANCE ?r)) (test (<= ?dp ?r))
    =>
    (assert 
        (ACTION (id RunAwayPowerPill) (info "no Ghost near Pacman && nearPP -> dodgePP") (priority 60))
    )
)

;12 evitar Rincon
(defrule PacmanDodgeCorner
    (PACMAN (nearEdibleGhosts ?g)) (test (== 0 ?g))
     (PACMAN (distanceToNearestCorner ?dp) (NEAR_CORNER_DISTANCE ?r)) (test (<= ?dp ?r))
    =>
    (assert 
        (ACTION (id RunAwayCorner) (info "no Ghost near Pacman && near Corner -> dodgeCorner") (priority 60))
    )
)

;13 evitar celda
(defrule PacmanDodgeLair
    (PACMAN (nearEdibleGhosts ?g)) (test (== 0 ?g))
     (PACMAN (distanceToLair ?dp) (NEAR_LAIR_DISTANCE ?r)) (test (<= ?dp ?r))
    =>
    (assert 
        (ACTION (id RunAwayLair) (info "no Ghost near Pacman && near Lair -> dodgeLair") (priority 60))
    )
)

;14 comer pills cercanas interseccion
(defrule PacmanEatPillsIntersection
     (PACMAN (pillsNear ?dp) (ENOUGH_PILLS_NEAR ?r)) (test (>= ?dp ?r))
        (PACMAN (isInIntersection true)) 
    =>
    (assert 
        (ACTION (id ...bestWay) (info "no Ghost near Pacman && enough pills && intersection -> bestWay") (priority 50))
    )
)


;15 comer pills cercanas no interseccion
(defrule PacmanEatPillsNonIntersection
     (PACMAN (pillsNear ?dp) (ENOUGH_PILLS_NEAR ?r)) (test (>= ?dp ?r))
        (PACMAN (isInIntersection false)) 
    =>
    (assert 
        (ACTION (id ChasePill) (info "no Ghost near Pacman && enough pills && no intersection -> eat pills") (priority 50))
    )
)

;16 buscar chasing ghost
(defrule PacmanChaseGhost
     (PACMAN (pillsNear ?dp) (ENOUGH_PILLS_NEAR ?r)) (test (< ?dp ?r))
    =>
    (assert 
        (ACTION (id ChaseGhost) (info "no Ghost near Pacman && no enough pills -> go chasing ghost") (priority 50))
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